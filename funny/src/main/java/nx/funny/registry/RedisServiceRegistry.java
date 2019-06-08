package nx.funny.registry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import nx.funny.transporter.message.HeartBeatRequest;
import nx.funny.transporter.message.HeartBeatResponse;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 基于redis实现的一个ServiceRegistry
 */
public class RedisServiceRegistry extends AbstractRegistry {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Getter
	private String redisHost;
	@Getter
	private int redisPort;
	@Getter
	private String user;
	@Getter
	private String password;

	@Setter
	private StringSerializer serializer = new DefaultStringSerializer();
	@Setter
	private StringDeSerializer deSerializer = new DefaultDeStringSerializer();

	public RedisServiceRegistry(String ip, int port, String redisHost, int redisPort, String user, String password) {
		super(ip, port);
		this.redisHost = redisHost;
		this.redisPort = redisPort;
		this.user = user;
		this.password = password;
	}

	private Jedis getJedisConnection() {
		// TODO:要加入用户名和密码,连接池
		return new Jedis(redisHost, redisPort);
	}

	public interface StringSerializer {
		String serialize(Object object);
	}

	public interface StringDeSerializer {
		<T> T serialize(String src, Type type);
	}

	public static class DefaultStringSerializer implements StringSerializer {
		private Gson gson = new Gson();

		@Override
		public String serialize(Object object) {
			return gson.toJson(object);
		}
	}

	public static class DefaultDeStringSerializer implements StringDeSerializer {
		private Gson gson = new Gson();

		@Override
		public <T> T serialize(String src, Type type) {
			return gson.fromJson(src, type);
		}
	}

	private static final String REGISTRY_HASH_KEY = "REGISTRY_HASH_KEY";
	private static final String OPTIMISTIC_LOCK_SUFFIX = "_LOCK";

	/**
	 * 修改某个ServiceInfo对应的Position集合
	 */
	private boolean changeServicePositions(ServiceType type, Consumer<Set<ServicePosition>> consumer) {
		try (Jedis jedis = getJedisConnection()) {
			String typeValue = serializer.serialize(type);
			String typeValueLockKey = typeValue + OPTIMISTIC_LOCK_SUFFIX;
			jedis.watch(typeValueLockKey);

			String positionsValue = jedis.hget(REGISTRY_HASH_KEY, typeValue);
			Set<ServicePosition> positions = null;
			if (positionsValue == null || positionsValue.length() == 0) {
				positions = new HashSet<>(1);
			} else {
				positions = deSerializer.serialize(positionsValue, new TypeToken<Set<ServicePosition>>() {
				}.getType());
			}

			consumer.accept(positions);

			Transaction multi = jedis.multi();
			positionsValue = serializer.serialize(positions);
			multi.hset(REGISTRY_HASH_KEY, typeValue, positionsValue);
			multi.incr(typeValueLockKey);
			List<Object> result = multi.exec();
			return result.size() == 2;
		}
	}

	@Override
	public void register(ServiceInfo info) {

		ServicePosition position = info.getPosition();

		boolean success = changeServicePositions(info.getType(), positions -> {
			positions.add(position);
		});
		if (!success)
			register(info);
		else
			logger.log(Level.INFO, "serviceRegister:" + info.toString());
	}

	@Override
	public void register(List<ServiceInfo> services) {
		if (services == null)
			return;
		services.forEach(this::register);
	}

	@Override
	public void remove(ServiceInfo info) {
		ServicePosition position = info.getPosition();

		boolean success = changeServicePositions(info.getType(), positions -> {
			positions.remove(position);
		});
		if (!success)
			remove(info);
		else
			logger.log(Level.INFO, "remove:" + info.toString());
	}

	@Override
	public void removeAll(ServiceType type) {
		boolean success = changeServicePositions(type, Set::clear);
		if (!success)
			removeAll(type);
		else
			logger.log(Level.INFO, "removeAll:" + type.toString());
	}

	@Override
	public Set<ServiceInfo> retrieve(String name) {
		Set<ServiceInfo> result = new HashSet<>();
		Type serviceTypeType = new TypeToken<ServiceType>() {
		}.getType();
		Type servicePositionType = new TypeToken<Set<ServicePosition>>() {
		}.getType();
		try (Jedis jedis = getJedisConnection()) {
			Map<String, String> keyValue = jedis.hgetAll(REGISTRY_HASH_KEY);
			keyValue.forEach((key, value) -> {
				ServiceType type = deSerializer.serialize(key, serviceTypeType);
				if (type.getName().equals(name)) {
					Set<ServicePosition> positions = deSerializer.serialize(value, servicePositionType);
					positions.forEach(position -> {
						result.add(new ServiceInfo(type, position));
					});
				}

			});
		}
		logger.log(Level.INFO, "retrieve:" + name);
		return result;
	}

	@Override
	public HeartBeatResponse receiveHeartbeat(Set<HeartBeatRequest> services) {
		// TODO: 2018/12/10 待实现redis方式的接收心跳
		System.out.println(String.format("receiveHeartbeat: %s", services));
		return null;
	}

}
