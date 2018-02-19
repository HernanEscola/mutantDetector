package org.magneto.mutantDetector.business.mutantDetector.impl;

import org.magneto.mutantDetector.business.mutantDetector.SequenceDetector;
import org.magneto.mutantDetector.business.mutantDetector.coordinates.Coordinates;

public abstract class AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl implements SequenceDetector {

	private int maxNumberOfSequencesTryingToDetect;
	private int sequenceLength;
	protected int matrixSize;
	private String dna[];

	public AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl(int sequenceLength) {
		super();
		this.sequenceLength = sequenceLength;
	}

	/**
	 * La parte complicada del algoritmo es la implementacion de este metodo. Debe
	 * devolver las coordenadas para que iterando NxN recorra la matriz en busqueda
	 * de sequencai de numeros consecutivos
	 * 
	 * Constraint: Este metodo debe retornar para 0,0 => una coordinada valida, sino
	 * el algoritmo puede tener un para que funcione correctamente
	 * 
	 * @param chainIndex
	 * @param characterIndex
	 * @return
	 */
	public abstract Coordinates transformToCoordinate(int sequenceIndexToSearchIn, int characterIndex);

	public int detect(String[] dna, int maxNumberOfSequencesTryingToDetect) {
		this.dna = dna;
		this.matrixSize = dna.length;
		this.maxNumberOfSequencesTryingToDetect = maxNumberOfSequencesTryingToDetect;
		int found = 0;
		for (int chainIndex = 0; chainIndex < matrixSize && found < maxNumberOfSequencesTryingToDetect; chainIndex++) {
			found = searchForward(chainIndex, found);
		}
		return found;
	}

	/**
	 * 
	 * Retorna la cantidad acumulado de sequencias de letras consecutivas del largo
	 * sequenceLength pasado en el constructor de esta clase Recorre todos los
	 * caracteres para la sequen avanzando segun la implemetacion del
	 * transformToCoordinate(sequenceIndex, charIdnex)
	 * 
	 * @param found
	 * @param sequenceIndexToSearchIn
	 * @return
	 */
	private int searchForward(int sequenceIndexToSearchIn, int found) {
		int characterIndex = -1;
		int count = 0;
		Coordinates coordToCompareWithNextChar = null;
		Coordinates coordsForNextChar;
		while (++characterIndex < matrixSize && found < maxNumberOfSequencesTryingToDetect) {
			if (coordToCompareWithNextChar == null) {
				coordToCompareWithNextChar = transformToCoordinate(sequenceIndexToSearchIn, characterIndex);
				count = 1;
				if (!isInsideMatrix(coordToCompareWithNextChar)) {
					return found;
				}
			} else {
				coordsForNextChar = transformToCoordinate(sequenceIndexToSearchIn, characterIndex);
				if (!isInsideMatrix(coordsForNextChar)) {
					break;
				}
				if (areTheSameCharacters(coordToCompareWithNextChar, coordsForNextChar)) {
					count++;
					if (count == sequenceLength) {
						if (++found >= maxNumberOfSequencesTryingToDetect) {
							return found;
						}
						coordToCompareWithNextChar = null;
					}
				} else {
					count = 1;
				}
				coordToCompareWithNextChar = coordsForNextChar;
			}
		}
		return found;
	}
	
	private boolean isInsideMatrix(Coordinates coords) {
		return coords.y < matrixSize && coords.x < matrixSize && coords.y >= 0;
	}

	private boolean areTheSameCharacters(Coordinates coordToCompareWithNextChar, Coordinates coordsForNextChar) {
		return dna[coordToCompareWithNextChar.y].charAt(coordToCompareWithNextChar.x) == dna[coordsForNextChar.y].charAt(coordsForNextChar.x);
	}

}
