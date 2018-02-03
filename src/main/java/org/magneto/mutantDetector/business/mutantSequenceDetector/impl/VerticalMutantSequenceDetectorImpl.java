package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class VerticalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	public VerticalMutantSequenceDetectorImpl() {
	}

	@Override
	public int getRow(int r, int s) {
		return s;
	}

	@Override
	public int getColumn(int r, int s) {
		return r;
	}

	public boolean isInsideMatrix(int row, int column) {
		return row < size && column < size;
	}


}
