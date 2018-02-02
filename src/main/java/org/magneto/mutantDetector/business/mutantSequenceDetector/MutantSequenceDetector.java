package org.magneto.mutantDetector.business.mutantSequenceDetector;

import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.HorizontalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.InverseObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.MultiMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.ObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.VerticalMutantSequenceDetectorImpl;

/**
 * Clase que, compuesta de las otros detectores, ejecuta el algoritmo para hallar una secuencia v'alida
 * Asegurarse de Inicializar la instancia antes de llamar a detect
 * @author Hernan A. Escola
 *
 */
public class MutantSequenceDetector extends MultiMutantSequenceDetectorImpl{
	public MutantSequenceDetector(int sequenceLength, int numberOfSequencesToFind) {
		this();
		init(sequenceLength, numberOfSequencesToFind);
	}

	public MutantSequenceDetector() {
		super();
		addDetector(new HorizontalMutantSequenceDetectorImpl());
		addDetector(new VerticalMutantSequenceDetectorImpl());
		addDetector(new ObliqueMutantSequenceDetectorImpl());
		addDetector(new InverseObliqueMutantSequenceDetectorImpl());
	}
	

}
