package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class ObliqueHorizontalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	
	public ObliqueHorizontalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int getColumn(int r, int s) {
		//corro en uno la columna para no repetir la diagonal principal
		return r+1 + s;
	}

	@Override
	public int getRow(int r, int s) {
		return s;
	}
	
	

	

}
