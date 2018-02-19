package org.magneto.mutantDetector.business.mutantSequenceDetector.impl;

import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetectorBaseImpl;

public class InverseObliqueVerticalMutantSequenceDetectorImpl extends MutantSequenceDetectorBaseImpl {

	

	public InverseObliqueVerticalMutantSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public int getColumn(int r, int s) {
		return s;
	}

	@Override
	public int getRow(int r, int s) {
		return r - s;
		// size-1 - r - s; //Este otro retorno tambien es válido pero tiene mas
		// oferaciones. Si supiera que las matrices son de 6x6 siempre, me servir'ia
		// para optimizar un poco mas, cortando antes de llegar a los limites, pero si
		// en cambio son matrices grandes, voy a estar haciendo operaciones de mas la
		// mayoria del tiempo
	}

}
