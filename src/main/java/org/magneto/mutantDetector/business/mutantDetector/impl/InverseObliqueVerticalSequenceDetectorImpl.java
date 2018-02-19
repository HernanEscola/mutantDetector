package org.magneto.mutantDetector.business.mutantDetector.impl;

import org.magneto.mutantDetector.business.mutantDetector.coordinates.Coordinates;

public class InverseObliqueVerticalSequenceDetectorImpl extends AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl {

	public InverseObliqueVerticalSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public Coordinates transformToCoordinate(int sequenceIndexToSearchIn, int characterPosition) {
		return new Coordinates(characterPosition, sequenceIndexToSearchIn - characterPosition);
	}

}
