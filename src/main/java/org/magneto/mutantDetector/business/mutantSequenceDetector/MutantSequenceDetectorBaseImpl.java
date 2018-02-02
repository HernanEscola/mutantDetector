package org.magneto.mutantDetector.business.mutantSequenceDetector;

import java.util.Arrays;

import lombok.extern.log4j.Log4j;

@Log4j
public abstract class MutantSequenceDetectorBaseImpl implements IMutantSequenceDetector {
	
	public static char[] VALID_CHARACTERS = {'A', 'T','C','G'};
	protected int numberOfSequencesToFind;
	protected int size;
	protected int sequenceLength;
	protected String[] dna;

	public MutantSequenceDetectorBaseImpl(int sequenceLength, int numberOfSequencesToFind) {
		super();
		init(sequenceLength, numberOfSequencesToFind);
	}
	
	public MutantSequenceDetectorBaseImpl() {
	}

	public int getSize() {
		return size;
	}

	public int getNumberOfSequencesToFind() {
		return numberOfSequencesToFind;
	}

	public int getSequenceLength() {
		return sequenceLength;
	}

	public IMutantSequenceDetector init(int sequenceLength, int numberOfSequencesToFind) {
		this.sequenceLength = sequenceLength;
		this.numberOfSequencesToFind = numberOfSequencesToFind;
		return this;
	}

	/**
	 * Implementación genérica para evitar buscar resultados fuera los límites
	 */
	public boolean isInsideMatrix(int row, int column) {
		return row < size && column < size && row >= 0;
	}

	public abstract int getRow(int r, int offset);

	public abstract int getColumn(int r, int offset);

	@Override
	public int detect(String[] dna) {
		// TODO Auto-generated method stub
		size = dna.length;
		return find(dna);
	}

	public int find(String[] dna) {
		int count = 0;
		int found = 0;

		int row = 0, column = 0;
		char current;
		char charForSequence = ' ';
		boolean isFirstCharacter = false;
		/**
		 * TODO: Puedo acotar el valor de R inicial para optimizarlo un poco más 
		 */
		for (int r = 0; r < size && found < numberOfSequencesToFind; r++) {
			
			isFirstCharacter = true;
			count = 0;
			/**
			 * Recoro la sequencia
			 */

			 String word = "";
			for (int offset = 0; offset < size && found < numberOfSequencesToFind; offset++) {

				row = getRow(r, offset);
				
				column = getColumn(r, offset);

				// valido que la posición sea válida
				if (checkIfInvalidPosition(row, column)  && !isValidCharacter(row, column)) {
					break;
				} else {
					// si es una solución válida
					current = dna[row].charAt(column);
					if (isFirstCharacter) {
						charForSequence = current;
						isFirstCharacter = false;
						 word = "";
						// chequear si la siguiente sequencia finaliza en una
						// columna y fila válida
						if(checkIfInvalidPosition(r, offset+3))break;
					}

					// Si matchea avanzo en busqueda de la sequencia
					if (current == charForSequence) {

						count++;
						 word += charForSequence + "";
						if (count == sequenceLength) {

							 log.info("found " + word + " in " + row+", "
							 +column);
							if (++found >= numberOfSequencesToFind) {
								break;
							}
							//charForSequence = ' ';
							isFirstCharacter = true;
							count = 0;
							 word="";
						}

					} else {
						// Si no corresponde a una secuencia inicio la búsqueda
						// con el current char y continuo
						charForSequence = current;
						count = 1;
						
						if(checkIfInvalidPosition(r, offset+3))break;
						// log.info(word);
						// word = charForSequence+ "";
					}
				}

			}
		}
		return found;
	}

	private boolean isValidCharacter(int row, int column) {
		for(char valid : VALID_CHARACTERS) {
			//Quizás puedo recibir las coordenadas y tener un array de booleans donde me dice si un caracter es valido o no
			//Especie de CACHE
			if(dna[row].charAt(column) == valid) {
				return true;
			}
		}
		return false;
	}

	private boolean checkIfInvalidPosition(int r, int offset) {
		return !isInsideMatrix(getRow(r, offset), getColumn(r, offset));
	}

}
