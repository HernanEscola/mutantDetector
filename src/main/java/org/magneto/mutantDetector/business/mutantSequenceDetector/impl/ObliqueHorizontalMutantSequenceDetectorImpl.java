package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.Coordinates;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class ObliqueHorizontalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	
	public ObliqueHorizontalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int transformToColumn(int r, int s) {
		//corro en uno la columna para no repetir la diagonal principal
		return r+1 + s;
	}

	@Override
	public int transformToRow(int r, int s) {
		return s;
	}
	
	@Override
	public Coordinates transformToCoordinate(int r, int s) {
		return new Coordinates(r+1 + s, s);
	}
	

	

}
