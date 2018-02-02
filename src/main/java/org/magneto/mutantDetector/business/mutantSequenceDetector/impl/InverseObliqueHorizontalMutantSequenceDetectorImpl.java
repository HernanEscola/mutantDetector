package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class InverseObliqueHorizontalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	
	@Override
	public int getColumn(int r, int s) {
		//corro en uno la columna para no repetir la diagonal principal
		return r+s+1;
	}

	@Override
	public int getRow(int r, int s) {
		// TODO Auto-generated method stub
		return size - 1 - s;
	}
	
	

	

}
