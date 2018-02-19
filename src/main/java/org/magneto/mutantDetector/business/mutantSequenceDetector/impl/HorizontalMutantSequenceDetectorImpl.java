package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.Coordinates;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class HorizontalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {



	public HorizontalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int transformToColumn(int r, int s) {
		return s;
	}

	@Override
	public int transformToRow(int r, int s) {
		return r;
	}
	
	@Override
	public Coordinates transformToCoordinate(int r, int offset) {
		return new Coordinates(offset, r);
	}

	/**
	 * Implementación genérica para evitar buscar resultados fuera los límites
	 */
	public boolean isInsideMatrix(int row, int column) {
		return row < matrixSize && column < matrixSize;
	}
	
	



}
