package org.magneto.mutantDetector.database.jedis;

import java.time.Duration;

import org.magneto.mutantDetector.exceptions.DBException;

import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Singleton que inicializa y provee el Pool conexiones de jedis
 * 
 * @author hescola
 *
 */
@Log4j
public class JedisProducer {

	public final static String REDIS_PORT = "6379";

	private JedisPool jedisPool;
	private static JedisProducer instance;

	protected JedisProducer() {
		initJedisPool();
	}

	/**
	 * Inicializa el cliente Jedis
	 * 
	 * @param jedis
	 */
	protected void initJedisPool() {
		final JedisPoolConfig poolConfig;

		JedisPoolConfig config = getJedisPoolConfig();

		/**
		 * TODO: implementar clusters y variables de entorno porque este mecanísmo para
		 * salir del paso es horrible
		 */
		String[] redisHosts = { "localhost" };

		String redisHost = null, redisPort = null;
		Jedis jResource = null;

		for (String key : System.getenv().keySet()) {
			log.info(key + "=" + System.getenv().get(key));
		}

		log.info("Redis=" + System.getenv().get("REDIS_HOST"));
		// redisHost = System.getenv().getOrDefault("REDIS_HOST", host);

		for (String host : redisHosts) { // esto
			try {

				redisHost = System.getenv().getOrDefault("REDIS_HOST", host);
				redisPort = System.getenv().getOrDefault("REDIS_PORT", REDIS_PORT);

				jedisPool = new JedisPool(config, redisHost, Integer.valueOf(redisPort), 2000);

				jResource = jedisPool.getResource(); // intento obtener una
														// conexión. Si arroja
														// exceción es porque no
														// pude establecer la
														// conexión
				// corto cuando logro obtener una conexion establecida
				if (jResource != null) {
					backToPool(jResource);
				}
				break;
			} catch (Exception e) {
				log.debug("Error Inicializando pool de Jedis (" + redisHost + ":" + redisPort + ")", e);
				try {
					jedisPool.close();// por las dudas cierro el pool
				} catch (Exception e2) {
					// TODO: verificar si es necesario
				}
				jedisPool = null;
			} finally {

			}
		}
		if (jedisPool == null) {
			log.error("No se pudo inicializar el pool. No se encontró una instancia del server");
		} else {
			log.info("Jedis Pool Inicializado");
		}
	}

	/**
	 * Retorna la configuración del pool de Jedis
	 * 
	 * @return
	 */
	private JedisPoolConfig getJedisPoolConfig() {
		final JedisPoolConfig poolConfig;
		poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(1024);
		poolConfig.setMaxIdle(128);
		poolConfig.setMinIdle(16);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
		poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
		poolConfig.setNumTestsPerEvictionRun(3);
		poolConfig.setBlockWhenExhausted(true);
		JedisPoolConfig config = new JedisPoolConfig();
		config.setTestOnBorrow(true);
		config.setMaxWaitMillis(2000); // si en dos segundos no consigue una
										// conexión algo anda mal
		return config;
	}

	public static JedisProducer getInstance() {
		if (instance == null) {
			instance = new JedisProducer();
		}
		return instance;
	}

	public Jedis get() throws DBException {
		try {
			Jedis jedis;
			jedis = jedisPool.getResource();
			return jedis;

		} catch (Exception e) {
			throw new DBException("Error while trying to connect to the server", e);
		}

	}

	@Override
	protected void finalize() throws Throwable {
		try {
			jedisPool.close();
		} catch (Exception e) {
			throw new DBException("Error while trying to save the DNA", e);
		}
	}

	/**
	 * Retorna el recurso al pool
	 * 
	 * @param jedis
	 */
	public void backToPool(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
		// No se devuelve mas al pool según la documentación

	}

}
