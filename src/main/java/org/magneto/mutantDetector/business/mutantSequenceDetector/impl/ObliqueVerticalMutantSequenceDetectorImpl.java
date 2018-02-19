package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.Coordinates;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class ObliqueVerticalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	public ObliqueVerticalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int transformToColumn(int r, int s) {
		return s;
	}

	@Override
	public int transformToRow(int r, int s) {
		return r+s;
	}

	@Override
	public Coordinates transformToCoordinate(int r, int s) {
		return new Coordinates(s, r+s);
	}
}
