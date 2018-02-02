package org.magneto.mutantDetector.database.jedis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.database.jedis.JedisDao;

public class JedisDaoTest {
	@Test
	public void getKeyTest() {
		// TODO Auto-generated method stub
		String key = "test";
		JedisDao jedisDao = getJedisDaoTestImpl(key);
		Assertions.assertEquals(jedisDao.getKey(key), key + "/" + key);
	}

	@Test
	public void getDaoKeyTest() {
		// TODO Auto-generated method stub
		final String key = "test";
		JedisDao jedisDao = getJedisDaoTestImpl(key);
		Assertions.assertEquals(jedisDao.getDaoKey(), key);
	}

	private JedisDao getJedisDaoTestImpl(String key) {
		return new JedisDao() {
			@Override
			public String getDaoKey() {
				// TODO Auto-generated method stub
				return key;
			}
		};
	}
}
