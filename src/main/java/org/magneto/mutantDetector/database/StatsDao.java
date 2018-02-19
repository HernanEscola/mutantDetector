package org.magneto.mutantDetector.database;

import org.jvnet.hk2.annotations.Service;
import org.magneto.mutantDetector.DTO.Stats;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.database.jedis.JedisDao;
import org.magneto.mutantDetector.database.jedis.JedisProducer;
import org.magneto.mutantDetector.exceptions.DBException;

import com.google.common.base.Optional;

import redis.clients.jedis.Jedis;

/**
 * DAO de Estadísticas
 * 
 * @author hescola
 *
 */
@Service
public class StatsDao extends JedisDao {

	public final String DAO_KEY = "stats";

	@Override
	public String getDaoKey() {
		return DAO_KEY;
	}

	public StatsDao() {
	}

	private Long incrementDnaStat(EDnaType type) throws DBException {
		Jedis jedis = null;
		try {
			jedis = JedisProducer.getInstance().get();
			return jedis.incr(getKey(type.name()));
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * Retorna los stats de los analisis realzados
	 * 
	 * @return Stats Instancia con los valores de los stats registrados
	 * @throws DBException
	 */
	public Stats getStats() throws DBException {
		Jedis jedis = null;
		try {
			jedis = JedisProducer.getInstance().get();
			Integer mutants = new Integer(Optional.fromNullable(jedis.get(getKey(EDnaType.MUTANT.name()))).or("0"));
			Integer humans = new Integer(Optional.fromNullable(jedis.get(getKey(EDnaType.HUMAN.name()))).or("0"));
			
			return new Stats(mutants, humans, (float) mutants / ((float) humans == 0 ? 1 : humans));
		} finally {
			returnToPool(jedis);
		}
	}

	/**
	 * Incrementa en uno el valor de mutantes
	 * 
	 * @return Long cantidad de ADNs Mutantes escaneados
	 * @throws DBException
	 */
	public Long addMutant() throws DBException {
		return incrementDnaStat(EDnaType.MUTANT);

	}

	/**
	 * * Incrementa en uno el valor de Humanos
	 * 
	 * @return Long cantidad de ADNs Humanos escaneados
	 * @throws DBException
	 */
	public Long addHuman() throws DBException {
		return incrementDnaStat(EDnaType.HUMAN);
	}

}
