package org.magneto.mutantDetector.services.interfaces;

import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.exceptions.DBException;

public interface MutantService {

	public EDnaType analizeDna(Dna dna) throws DBException;

	/**
	 * Ejecuta el código que analiza si es mutante o no
	 * 
	 * @param dnaData
	 * @return
	 */
	public boolean isMutant(String[] dna);
}