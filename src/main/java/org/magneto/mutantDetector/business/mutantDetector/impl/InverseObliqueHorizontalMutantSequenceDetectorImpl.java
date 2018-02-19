package org.magneto.mutantDetector.business.mutantDetector.impl;

import org.magneto.mutantDetector.business.mutantDetector.coordinates.Coordinates;

public class InverseObliqueHorizontalMutantSequenceDetectorImpl extends AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl {

	public InverseObliqueHorizontalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	
	@Override
	public Coordinates transformToCoordinate(int sequenceIndexToSearchIn, int characterPosition) {
		return new Coordinates(sequenceIndexToSearchIn + characterPosition + 1, this.matrixSize - 1 - characterPosition);
	}

}
