package org.magneto.mutantDetector.database.jedis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
/**
 * Clase Base para los DAOs
 * 
 * TODO: desacoplar la implementación para que sea una Interface
 * @author Hernan Adriel Escola
 *
 */
public abstract class JedisDao {

	protected String getKey(String key) {
		// TODO Auto-generated method stub
		return getDaoKey() + "/" + key;
	}

//	
	protected void returnToPool(Jedis jedis) {
			JedisProducer.getInstance().backToPool((Jedis) jedis);
	}

	public abstract String getDaoKey();

	/**
	 * Serializa para guardar el objeto
	 * @param toSerialiaze
	 * @return
	 * @throws JsonProcessingException
	 */
	protected String serialize(Object toSerialiaze) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String serialized = mapper.writeValueAsString(toSerialiaze);
		return serialized;
	}
}
