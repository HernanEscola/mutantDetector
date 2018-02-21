package org.magneto.mutantDetector.business.mutantDetector.impl;

import org.magneto.mutantDetector.business.mutantDetector.coordinates.Coordinates;

public abstract class AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl {

	protected int matrixSize;
	public Coordinates coordToCompareWithNextChar = null;
	public Coordinates coordsForNextChar;
	public int count = 0;
	
	public AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl(int matrixSize) {
		super();
		this.matrixSize = matrixSize;
	}

	/**
	 * La parte complicada del algoritmo es la implementacion de este metodo.
	 * Debe devolver las coordenadas para que iterando NxN recorra la matriz en
	 * busqueda de sequencai de numeros consecutivos
	 * 
	 * Constraint: Este metodo debe retornar para 0,0 => una coordinada valida,
	 * sino el algoritmo puede tener un para que funcione correctamente
	 * 
	 * @param chainIndex
	 * @param characterIndex
	 * @return
	 */
	public abstract Coordinates transformToCoordinate(int sequenceIndexToSearchIn, int characterIndex);


}
