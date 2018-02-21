
package org.magneto.mutantDetector.business.mutantDetector.impl;

import java.util.ArrayList;
import java.util.List;

import org.magneto.mutantDetector.business.mutantDetector.SequenceDetector;
import org.magneto.mutantDetector.business.mutantDetector.coordinates.Coordinates;

/**
 * Esta clase envuelve a los otros detectores y los combina para realizar el
 * escaneo necesario
 * 
 * @author hescola
 *
 */
public class MultiDetectorImpl implements SequenceDetector {

	private int maxNumberOfSequencesTryingToDetect;
	private int sequenceLength;
	protected int matrixSize;
	private String dna[];
	

	private ArrayList<AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl> detectors = new ArrayList<AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl>();
	AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl[] detectoresArray;
	/**
	 * Implementar agregando los detectores que se desee
	 * 
	 * @return
	 */
	public void addDetector(AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl detector) {
		detectors.add(detector);
	}

	public MultiDetectorImpl(int sequenceLength) {
		this.sequenceLength = sequenceLength;
	}

	public int detect(String[] dna, int maxNumberOfSequencesTryingToDetect) {
		this.dna = dna;
		this.matrixSize = dna.length;
		this.maxNumberOfSequencesTryingToDetect = maxNumberOfSequencesTryingToDetect;
		int found = 0;
		detectoresArray = detectors.toArray(new AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl[detectors.size()]);

		for (AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl detector : detectoresArray) {
			detector.coordToCompareWithNextChar = null;
			detector.count = 0;
			detector.matrixSize = this.matrixSize;
		}
		for (int chainIndex = 0; chainIndex < this.matrixSize && found < maxNumberOfSequencesTryingToDetect; chainIndex++) {
			found = searchForward(chainIndex, found);
		}
		return found;
	}

	/**
	 * 
	 * Retorna la cantidad acumulado de sequencias de letras consecutivas del
	 * largo sequenceLength pasado en el constructor de esta clase Recorre todos
	 * los caracteres para la sequen avanzando segun la implemetacion del
	 * transformToCoordinate(sequenceIndex, charIdnex)
	 * 
	 * @param found
	 * @param sequenceIndexToSearchIn
	 * @return
	 */
	private int searchForward(int sequenceIndexToSearchIn, int found) {
		int characterIndex = -1;
		AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl detector;
		 int detectorIdx;
		while (++characterIndex < matrixSize && found < maxNumberOfSequencesTryingToDetect) {
			for (  detectorIdx = 0; detectorIdx< detectoresArray.length ; detectorIdx++) {
				detector = detectoresArray[detectorIdx];
				if (detector.coordToCompareWithNextChar == null) {
					detector.coordToCompareWithNextChar = detector.transformToCoordinate(sequenceIndexToSearchIn, characterIndex);
					detector.count = 1;
					if (!isInsideMatrix(detector.coordToCompareWithNextChar)) {
						continue;
					}
				} else {
					detector.coordsForNextChar = detector.transformToCoordinate(sequenceIndexToSearchIn, characterIndex);
					if (!isInsideMatrix(detector.coordsForNextChar)) {
						continue;
					}
					if (areTheSameCharacters(detector.coordToCompareWithNextChar, detector.coordsForNextChar)) {
						detector.count++;
						if (detector.count == sequenceLength) {
							if (++found >= maxNumberOfSequencesTryingToDetect) {
								return found;
							}
							detector.coordToCompareWithNextChar = null;
						}
					} else {
						detector.count = 1;
					}
					detector.coordToCompareWithNextChar = detector.coordsForNextChar;
				}
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
