package org.magneto.mutantDetector.services.interfaces;

import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;

public interface MutantService {

	public boolean analizeDna(Dna dna) throws InvalidDnaException, DBException;


}