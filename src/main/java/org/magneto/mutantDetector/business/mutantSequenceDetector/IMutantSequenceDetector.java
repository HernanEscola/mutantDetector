package org.magneto.mutantDetector.business.mutantSequenceDetector;

/**
 * API del Detector de secuencias mutante
 * @author aion
 *
 */
public interface IMutantSequenceDetector {

	/**
	 * Método que se ejecuta para encontrar las secuencias
	 * 
	 * @param dna
	 * @return
	 */
	public int detect(String[] dna);
	
	/**
	 * Inicializa el detector para poder ajustarse a los parámetros
	 * @param SEQUENCE_LENGTH
	 * @param numberOfSequencesToFind
	 */
	public IMutantSequenceDetector init(int sequenceLength, int numberOfSequencesToFind);

}
