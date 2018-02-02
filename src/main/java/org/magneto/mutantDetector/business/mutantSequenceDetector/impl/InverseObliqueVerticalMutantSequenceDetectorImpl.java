package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class InverseObliqueVerticalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	public InverseObliqueVerticalMutantSequenceDetectorImpl() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getColumn(int r, int s) {
		return s;
	}

	@Override
	public int getRow(int r, int s) {
		return r - s;
	}

}
