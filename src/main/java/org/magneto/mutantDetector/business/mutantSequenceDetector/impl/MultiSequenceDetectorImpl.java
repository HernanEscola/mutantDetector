
package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import java.util.ArrayList;
import java.util.List;

import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;

/**
 * Esta clase envuelve a los otros detectores y los combina para realizar el
 * escaneo necesario
 * 
 * @author hescola
 *
 */
public class MultiSequenceDetectorImpl implements IMutantSequenceDetector {

	private List<IMutantSequenceDetector> detectors = new ArrayList<IMutantSequenceDetector>();

	

	/**
	 * Implementar agregando los detectores que se desee
	 * 
	 * @return
	 */
	public void addDetector(IMutantSequenceDetector detector) {
		detectors.add(detector);
	}

	@Override
	public int detect(String[] dna, int maxNumberOfSequencesTryingToDetect) {
		int found = 0;
		for (IMutantSequenceDetector msd : this.detectors) {
			found += msd.detect(dna, maxNumberOfSequencesTryingToDetect - found);
			if (found >= maxNumberOfSequencesTryingToDetect) {
				break;
			}
		}
		return found;
	}

}
