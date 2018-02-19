package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class ObliqueVerticalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	public ObliqueVerticalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int getColumn(int r, int s) {
		return s;
	}

	@Override
	public int getRow(int r, int s) {
		return r+s;
	}


}
