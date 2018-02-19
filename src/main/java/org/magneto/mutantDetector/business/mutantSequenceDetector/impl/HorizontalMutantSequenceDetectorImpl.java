package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class HorizontalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {



	public HorizontalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int getColumn(int r, int s) {
		return s;
	}

	@Override
	public int getRow(int r, int s) {
		return r;
	}

	/**
	 * Implementación genérica para evitar buscar resultados fuera los límites
	 */
	public boolean isInsideMatrix(int row, int column) {
		return row < size && column < size;
	}



}
