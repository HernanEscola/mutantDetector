package org.magneto.mutantDetector.utils;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.magneto.mutantDetector.utils.ServersManager;

import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

@TestInstance(Lifecycle.PER_CLASS)
public class TestWithNewRedisServerInstance {

	private static final int redisPort = 6379;
	private static final String redisHost = "localhost";

	protected RedisServer redisServer;
	
	/**
	 * Setup para llamarlo en @BeforeEach o @BeforeAll
	 * @throws IOException 
	 */
	public void setup()  {
		try {
			redisServer = ServersManager.startCleanRedisInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Assertions.fail(e.getMessage());
		}
	}
	
	

	/**
	 * Finish para llamarlo en @BeforeEach o @BeforeAll
	 */
	public void finish() {
		try {
			this.redisServer.stop();
		} catch (Exception e) {
			Jedis jedi = new Jedis(redisHost, redisPort);
			jedi.shutdown();
			jedi.close();
		}
	}
	
	

}