package org.magneto.mutantDetector.business.mutantSequenceDetector;

/**
 * API del Detector de secuencias mutante
 * @author Hernan A. Escola 
 *
 */
public interface IMutantSequenceDetector {

	/**
	 * Método que se ejecuta para buscar y contar secuencias hasta maxNumberOfSequencesTryingToDetect
	 * 
	 * @param dna
	 * @param maxNumberOfSequencesToTryDetect
	 * @return
	 */
	public int detect(String[] dna, int maxNumberOfSequencesTryingToDetect);
	
}
