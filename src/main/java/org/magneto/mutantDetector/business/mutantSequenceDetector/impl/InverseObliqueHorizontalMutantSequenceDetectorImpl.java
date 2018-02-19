package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class InverseObliqueHorizontalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	public InverseObliqueHorizontalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int getColumn(int r, int s) {
		// corro en uno la columna para no repetir la diagonal principal
		return r + s + 1;
	}

	@Override
	public int getRow(int r, int s) {
		return size - 1 - s;
	}

}
