package org.magneto.mutantDetector.services.interfaces;

import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;

public interface MutantService {

	/**
	 * Analiza la cadena de de ADN en búsqueda del GEN Mutante
	 * 
	 * Asumo que recibo una cadena de ADN válida
	 * 
	 * @param dna
	 * @return true si la cadena es una cadena de ADN Mutante, sino false
	 * @throws DBException
	 *             Arrojada si ocurre alg'un error al querer registrar la cadena
	 * @throws InvalidDnaException
	 */
	public EDnaType analizeDna(Dna dna) throws DBException, InvalidDnaException;

}