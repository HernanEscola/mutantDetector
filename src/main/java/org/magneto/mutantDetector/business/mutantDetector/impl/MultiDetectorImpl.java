
package org.magneto.mutantDetector.business.mutantDetector.impl;

import java.util.ArrayList;
import java.util.List;

import org.magneto.mutantDetector.business.mutantDetector.SequenceDetector;

/**
 * Esta clase envuelve a los otros detectores y los combina para realizar el
 * escaneo necesario
 * 
 * @author hescola
 *
 */
public class MultiDetectorImpl implements SequenceDetector {

	private List<SequenceDetector> detectors = new ArrayList<SequenceDetector>();

	

	/**
	 * Implementar agregando los detectores que se desee
	 * 
	 * @return
	 */
	public void addDetector(SequenceDetector detector) {
		detectors.add(detector);
	}

	@Override
	public int detect(String[] dna, int maxNumberOfSequencesTryingToDetect) {
		int found = 0;
		for (SequenceDetector msd : this.detectors) {
			found += msd.detect(dna, maxNumberOfSequencesTryingToDetect - found);
			if (found >= maxNumberOfSequencesTryingToDetect) {
				break;
			}
		}
		return found;
	}

}
