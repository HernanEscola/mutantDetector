package org.magneto.mutantDetector.database.jedis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.database.jedis.JedisDao;

public class JedisDaoTest {
	@Test
	public void getKeyTest() {
		String key = "test";
		JedisDao jedisDao = getJedisDaoTestImpl(key);
		Assertions.assertEquals(jedisDao.getKey(key), key + "/" + key);
	}

	@Test
	public void getDaoKeyTest() {
		final String key = "test";
		JedisDao jedisDao = getJedisDaoTestImpl(key);
		Assertions.assertEquals(jedisDao.getDaoKey(), key);
	}

	private JedisDao getJedisDaoTestImpl(String key) {
		return new JedisDao() {
			@Override
			public String getDaoKey() {
				return key;
			}
		};
	}
}
