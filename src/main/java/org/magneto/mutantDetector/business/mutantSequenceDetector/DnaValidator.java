package org.magneto.mutantDetector.business.mutantSequenceDetector;

import java.util.HashSet;
import java.util.Set;

import org.magneto.mutantDetector.exceptions.InvalidDnaException;

public class DnaValidator {

	public static char[] VALID_CHARACTERS = { 'A', 'T', 'C', 'G' };
	private final static Set<Character> VALID_CHARACTERS_SET = new HashSet<Character>(VALID_CHARACTERS.length);
	private final static int SEQUENCE_LENGTH = 4;

	{
		// Inicializo el set con los caracteres validos
		for (char validChar : VALID_CHARACTERS) {
			VALID_CHARACTERS_SET.add(validChar);
		}
	}

	/**
	 * Chequea si la cadena de ADN contiene caracteres validos
	 * 
	 * @param dna
	 *            Cadena de ADN a validar cuyos caracteres sean [ATCG]
	 * 
	 * 
	 * @return true si la cadena es valida
	 */
	public boolean validate(String[] dna) throws InvalidDnaException {
		validateMinLength(dna);
		validateSquareMatrix(dna);
		validateCharacters(dna);
		return true;
	}

	private void validateCharacters(String[] dna) throws InvalidDnaException {

		for (int i = 0; i < dna.length; i++) {
			if (!hasValidCharactersInLine(dna[i])) {
				throw new InvalidDnaException("La cadena de ADN debe contener solo los siguientes caracteres: A,T,C,G]" + SEQUENCE_LENGTH);
			}
		}
	}

	private boolean hasValidCharactersInLine(String dnaLine) {
		int length = dnaLine.length();
		for (int j = 0; j < length; j++) {
			if (!isValidChar(dnaLine.charAt(j))) {
				return false;
			}
		}

		return true;
	}

	private void validateMinLength(String[] dna) throws InvalidDnaException {
		if (dna.length < SEQUENCE_LENGTH) {
			throw new InvalidDnaException("El largo de la cadena de ADN debe ser de al menos " + SEQUENCE_LENGTH);
			// throw new InvalidDnaException("DNA length must have at least a length of " +
						// SEQUENCE_LENGTH );
		}
	}

	private void validateSquareMatrix(String[] dna) throws InvalidDnaException {

		int size = dna.length;
		for (int j = 0; j < size; j++) {
			if (dna[j].length() != size) {
				throw new InvalidDnaException("La Cadena de ADN debe representar una matriz N x N ");
				// throw new InvalidDnaException("The DN " + SEQUENCE_LENGTH );
			}
		}
	}

	private boolean isValidChar(char charToValidate) {
		return VALID_CHARACTERS_SET.contains(charToValidate);
	}

}
