package org.magneto.mutantDetector.business.mutantSequenceDetector;

import lombok.extern.log4j.Log4j;

@Log4j
public abstract class MutantSequenceDetectorBaseImpl implements IMutantSequenceDetector {

	private int maxNumberOfSequencesTryingToDetect;
	private int sequenceLength;
	protected int matrixSize;
	private String dna[];

	public MutantSequenceDetectorBaseImpl(int sequenceLength) {
		super();
		this.sequenceLength = sequenceLength;
	}

	/**
	 * Implementación genérica para evitar buscar resultados fuera los límites
	 */
	public boolean isInsideMatrix(Coordinates coords) {
		return coords.y < matrixSize && coords.x < matrixSize && coords.y >= 0;
	}

	public abstract int transformToRow(int r, int offset);

	public abstract int transformToColumn(int r, int offset);

	public abstract Coordinates transformToCoordinate(int r, int offset);

	public int detect(String[] dna, int maxNumberOfSequencesTryingToDetect) {
		this.dna = dna;
		this.matrixSize = dna.length;
		this.maxNumberOfSequencesTryingToDetect = maxNumberOfSequencesTryingToDetect;
		return find();
	}

	public int find() {
		int found = 0;

		for (int sequenceStartPoint = 0; sequenceStartPoint < matrixSize && found < maxNumberOfSequencesTryingToDetect; sequenceStartPoint++) {
			found = searchForward(sequenceStartPoint, found);
		}
		return found;
	}

	/**
	 * Recorre la matriz partiendo del punto de partida definido por "r", y offset
	 * define el index del valor
	 * 
	 * Para explicarlo un poco mas sencillo, por cada R lo que realiza es algo
	 * similar a una funci'on linel, en cada detector defino que dada las entradas
	 * "r" y Offset, obtengo un X e Y. A mismo valor de "r" cada valor de offset
	 * describe un punto de una recta. En el caso del detector horizontal, "r" es
	 * equivalente al valor de "Y", y "offset" es "X, es decir que con Y=0, X
	 * describe la recta que inicia en el origen y finaliza en X = N-1, donde N es
	 * el tamaño de la matriz
	 * 
	 * @param dna
	 * @param found
	 * @param sequenceStartPoint
	 * @return
	 */
	private Integer searchForward(int sequenceStartPoint, int found) {
		int indexForSequence = -1;
		int count = 0;
		Coordinates coordToCompareWithNextChar = null;
		Coordinates coordsForNextChar;

		while (++indexForSequence < matrixSize && found < maxNumberOfSequencesTryingToDetect) {
			if (coordToCompareWithNextChar == null) {
				coordToCompareWithNextChar = transformToCoordinate(sequenceStartPoint, indexForSequence);
				count = 1;
				if (!isInsideMatrix(coordToCompareWithNextChar)) {
					return found;
				}
			} else {
				coordsForNextChar = transformToCoordinate(sequenceStartPoint, indexForSequence);
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

	private boolean areTheSameCharacters(Coordinates coordToCompareWithNextChar, Coordinates coordsForNextChar) {
		return dna[coordToCompareWithNextChar.y].charAt(coordToCompareWithNextChar.x) == dna[coordsForNextChar.y].charAt(coordsForNextChar.x);
	}

//	private char getCharacterByCoordinates(Coordinates coordToCompareWithNextChar) {
//		return dna[coordToCompareWithNextChar.y].charAt(coordToCompareWithNextChar.x);
//	}

	// private boolean reinitSearch(int r, int found) {
	// row = transformToRow(r, offset);
	// column = transformToColumn(r, offset);
	// count = 1;
	// if (!isInsideMatrix(row, column)) {
	// return false;
	// }
	//
	// return true;
	// }

	// /**
	// * Itera hacia delante (segund el detector que se utilice) en busqueda de una
	// * secuencia de caracteres iguales al parametro "toSearch"
	// *
	// * @param dna
	// * Matriz a buscar secuencia
	// * @param r
	// * Posici'on de inicio de la busqueda de secuencia
	// * @param offset
	// * Es el offset actual del cual se obtuvo el caracter a buscar.
	// * @param toSearch
	// * caracter a buscar
	// * @return
	// */
	// private int countSequenceLength(String[] dna, int r, int offset, char
	// toSearch) {
	// int row;
	// int column;
	// int limit = offset + sequenceLength;
	// int count = 1;
	//
	// for (int i = offset + 1; i < limit; i++) {
	// row = getRow(r, i);
	// column = getColumn(r, i);
	// if (!isInsideMatrix(row, column) || toSearch != dna[row].charAt(column)) {
	// break;
	// }
	// count++;
	// }
	//
	// return count;
	// }

}
