package org.magneto.mutantDetector.business.mutantDetector.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.magneto.mutantDetector.business.mutantDetector.coordinates.Coordinates;

/**
 * Implementación de prueb apara evaluar si Utilizar Expresiones regulares
 * hubiera sido más óptimo, pero es varias veces más lento que la solución
 * propuesta originalmente
 * 
 * @author hescola
 *
 */
public class HorizontalRegexSequenceDetectorImpl extends AbstractFixedSequenceWithConsecutiveCharactersDetectorImpl {

	private final String REGEX = "(A{4}|T{4}|C{4}|G{4})";
	private final Pattern pattern = Pattern.compile(REGEX);

	public HorizontalRegexSequenceDetectorImpl(int sequenceLength) {
		super(sequenceLength);
	}

	@Override
	public Coordinates transformToCoordinate(int sequenceIndexToSearchIn, int characterPosition) {
		return new Coordinates(characterPosition, sequenceIndexToSearchIn);
	}

	@Override
	protected int searchForward(int sequenceIndexToSearchIn, int found) {
		Matcher matcher = pattern.matcher(dna[sequenceIndexToSearchIn]);
		while (found < maxNumberOfSequencesTryingToDetect && matcher.find()) {
			found++;
		}
		return found;
	}

}
