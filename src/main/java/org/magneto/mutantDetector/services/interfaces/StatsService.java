package org.magneto.mutantDetector.services.interfaces;

import org.magneto.mutantDetector.DTO.Stats;
import org.magneto.mutantDetector.exceptions.DBException;

/**
 * Servicio de Estadísticas de los Análisis realizados
 * 
 * @author hescola
 *
 */
public interface StatsService {

	/**
	 * Devuelve las estadísticas de los análisis de ADN
	 * @return
	 * @throws DBException 
	 */
	public Stats getStats() throws DBException;
	/**
	 * Aumenta en 1 el contador de ADN mutante analizados
	 * @throws DBException 
	 */
	public void incrementMutant() throws DBException;
	
	/**
	 * Aumenta en 1 el contador de ADN Humanos analizados
	 * @throws DBException 
	 */
	public void incrementHuman() throws DBException;

}