package org.magneto.mutantDetector.business.mutantSequenceDetector;

import java.util.HashSet;
import java.util.Set;

import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.HorizontalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.InverseObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.MultiMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.ObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.VerticalMutantSequenceDetectorImpl;

/**
 * Clase que, compuesta de las otros detectores, ejecuta el algoritmo para
 * hallar una secuencia válida Asegurarse de Inicializar la instancia antes de
 * llamar a detect
 * 
 * @author Hernan A. Escola
 *
 */
public class MutantSequenceDetector extends MultiMutantSequenceDetectorImpl {

	public static char[] VALID_CHARACTERS = { 'A', 'T', 'C', 'G' };
	public static char PLACEHOLDER_FOR_INVALID_CHARACTER = ' ';
	protected final static Set<Character> VALID_CHARACTERS_MAP = new HashSet<Character>(VALID_CHARACTERS.length);
	{
		for (char c : VALID_CHARACTERS) {
			VALID_CHARACTERS_MAP.add(c);
		}
	}

	public MutantSequenceDetector() {
		super();
		addDetector(new HorizontalMutantSequenceDetectorImpl());
		addDetector(new VerticalMutantSequenceDetectorImpl());
		addDetector(new ObliqueMutantSequenceDetectorImpl());
		addDetector(new InverseObliqueMutantSequenceDetectorImpl());
	}

	@Override
	public int detect(String[] dna) {
		return super.detect(dna);
	}

	/**
	 * Chequea si la cadena de ADN es valida
	 * 
	 * @param dna
	 *            Cadena de ADN a validar cuyos caracteres sean [ATCG]
	 * 
	 * 
	 * @return true si la cadena es valida
	 */
	public boolean isValid(String[] dna, int sequencesLength) {

		// No estoy seguro si este caso es inv'alido o Humano
		if (dna.length < sequencesLength) {

			// la logitud secuencia a encontrar es menor que el tamaño de la matriz
			return false;
		}
		int size = dna.length;
		for (int i = 0; i < size; i++) {
			if (dna[i].length() != dna.length) {
				// NO ES UNA MATRIZ CUADRADA
				return false;
			}
			for (int j = 0; j < size; j++) {
				if (!isValidChar(dna[i].charAt(j))) {
					return false;
				}
			}
		}
		/**
		 * LO MALO DE RETORNAR BOOLEANE S NO PODER INFORMAR CUAL FUE LA VALIDACION QUE
		 * RECHAZO EL ADN
		 */
		return true;
	}

	/**
	 * Esta implementacion la hice porque en un principio pense que solo se podian
	 * tomar validas secuencias mutantes ATCG
	 * 
	 * @param dna
	 * @return
	 */
	// public String[] filter(String[] dna) {
	//
	// int size = dna.length;
	// char current;
	// String[] filtered = new String[dna.length];
	//
	// for (int i = 0; i < size; i++) {
	// StringBuilder row = new StringBuilder(size);
	//
	// for (int j = 0; j < size; j++) {
	// // reemplazo todo lo que no sea valido
	// current = isValidChar(dna[i].charAt(j)) ? dna[i].charAt(j) :
	// PLACEHOLDER_FOR_INVALID_CHARACTER;
	// row.append(current);
	// }
	// filtered[i] = row.toString();
	// }
	//
	// return filtered;
	// }

	private boolean isValidChar(char charAt) {
		// TODO Auto-generated method stub
		return VALID_CHARACTERS_MAP.contains(charAt);
	}

}
