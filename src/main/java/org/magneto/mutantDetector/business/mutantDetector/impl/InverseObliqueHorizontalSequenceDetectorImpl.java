package org.magneto.mutantDetector.business.mutantDetector.impl;

import org.magneto.mutantDetector.business.mutantDetector.coordinates.Coordinates;

public class InverseObliqueHorizontalSequenceDetectorImpl extends AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl {

	public InverseObliqueHorizontalSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	
	@Override
	public Coordinates transformToCoordinate(int sequenceIndexToSearchIn, int characterPosition) {
		return new Coordinates(sequenceIndexToSearchIn + characterPosition + 1, this.matrixSize - 1 - characterPosition);
	}

}
