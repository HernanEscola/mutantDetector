package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.Coordinates;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class VerticalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	public VerticalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int transformToColumn(int r, int s) {
		return r;
	}

	@Override
	public int transformToRow(int r, int s) {
		return s;
	}

	@Override
	public Coordinates transformToCoordinate(int r, int s) {
		return new Coordinates(r, s);
	}

	public boolean isInsideMatrix(int row, int column) {
		return row < matrixSize && column < matrixSize;
	}

}
