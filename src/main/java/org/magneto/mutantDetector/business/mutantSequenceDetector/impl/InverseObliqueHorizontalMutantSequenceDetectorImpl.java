package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.Coordinates;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class InverseObliqueHorizontalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	public InverseObliqueHorizontalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int transformToColumn(int r, int s) {
		// corro en uno la columna para no repetir la diagonal principal
		return r + s + 1;
	}

	@Override
	public int transformToRow(int r, int s) {
		return matrixSize - 1 - s;
	}
	
	@Override
	public Coordinates transformToCoordinate(int r, int offset) {
		return new Coordinates(r + offset + 1, matrixSize - 1 - offset);
	}

}
