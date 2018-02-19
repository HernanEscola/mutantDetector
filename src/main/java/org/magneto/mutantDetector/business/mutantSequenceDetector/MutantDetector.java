package org.magneto.mutantDetector.business.mutantSequenceDetector;

import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.HorizontalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.InverseObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.MultiSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.ObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.VerticalMutantSequenceDetectorImpl;

/**
 * Clase que, compuesta de las otros detectores, ejecuta el algoritmo para
 * hallar una secuencia válida
 * 
 * @author Hernan A. Escola
 *
 */
public class MutantDetector extends MultiSequenceDetectorImpl {
	final public static int N_SEQUENCES_TO_FIND = 2;
	final public static int MUTANT_SEQUENCE_LENGTH = 4;

	private MultiSequenceDetectorImpl multiSequenceDetectorImpl;

	public MutantDetector() {
		super();
		multiSequenceDetectorImpl = new MultiSequenceDetectorImpl();
		multiSequenceDetectorImpl.addDetector(new HorizontalMutantSequenceDetectorImpl(MUTANT_SEQUENCE_LENGTH));
		multiSequenceDetectorImpl.addDetector(new VerticalMutantSequenceDetectorImpl(MUTANT_SEQUENCE_LENGTH));
		multiSequenceDetectorImpl.addDetector(new ObliqueMutantSequenceDetectorImpl(MUTANT_SEQUENCE_LENGTH));
		multiSequenceDetectorImpl.addDetector(new InverseObliqueMutantSequenceDetectorImpl(MUTANT_SEQUENCE_LENGTH));
	}

	public boolean isMutant(String[] dna) {

		return multiSequenceDetectorImpl.detect(dna, N_SEQUENCES_TO_FIND) == N_SEQUENCES_TO_FIND;
	}

}
