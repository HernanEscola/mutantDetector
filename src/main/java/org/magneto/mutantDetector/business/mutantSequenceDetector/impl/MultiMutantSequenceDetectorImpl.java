
package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;

/**
 * Esta clase envuelve a los otros detectores y los combina para realizar el
 * escaneo necesario
 * 
 * @author hescola
 *
 */
public abstract class MultiMutantSequenceDetectorImpl implements IMutantSequenceDetector {

	private int numberOfSequencesToFind;
	private int sequenceLength;
	protected List<IMutantSequenceDetector> detectors = new ArrayList<IMutantSequenceDetector>();


	public MultiMutantSequenceDetectorImpl() {
	}

	/**
	 * Implementar agregando los detectores que se desee
	 * 
	 * @return
	 */
	public void addDetector(IMutantSequenceDetector detector) {
		detectors.add(detector);
	}

	@Override
	public int detect(String[] dna) {
		int found = 0;
		for (IMutantSequenceDetector msd : this.detectors) {

			msd.init(sequenceLength, numberOfSequencesToFind - found);
			found += msd.detect(dna);
			if (found >= numberOfSequencesToFind) {
				break;
			}
		}
		return found;
	}

	@Override
	public IMutantSequenceDetector init(int sequenceLength, int numberOfSequencesToFind) {
		this.numberOfSequencesToFind = numberOfSequencesToFind;
		this.sequenceLength = sequenceLength;
		return this;
	}


}
