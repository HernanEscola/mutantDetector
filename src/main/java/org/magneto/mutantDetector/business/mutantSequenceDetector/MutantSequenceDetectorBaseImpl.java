package org.magneto.mutantDetector.business.mutantSequenceDetector;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.log4j.Log4j;

@Log4j
public abstract class MutantSequenceDetectorBaseImpl implements IMutantSequenceDetector {

	protected int numberOfSequencesToFind;
	protected int size;
	protected int sequenceLength;
	protected String[] dna;

	// Mapa para
	public static char[] VALID_CHARACTERS = { 'A', 'T', 'C', 'G' };
	protected final static Set<Character> VALID_CHARACTERS_MAP = new HashSet<Character>(VALID_CHARACTERS.length);
	{
		for (char c : VALID_CHARACTERS) {
			VALID_CHARACTERS_MAP.add(c);
		}
	}

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
//
//	public int find(String[] dna) {
//		int count = 0;
//		int found = 0;
//
//		int row = 0, column = 0;
//		char current;
//		char charForSequence = ' ';
//		boolean isFirstCharacter = false;
//		/**
//		 * TODO: Puedo acotar el valor de R inicial para optimizarlo un poco más
//		 */
//		for (int r = 0; r < size && found < numberOfSequencesToFind; r++) {
//
//			isFirstCharacter = true;
//			count = 0;
//			/**
//			 * Recoro la sequencia
//			 */
//
//			String word = "";
//			for (int offset = 0; offset < size && found < numberOfSequencesToFind; offset++) {
//
//				row = getRow(r, offset);
//				column = getColumn(r, offset);
//				
//				
//				
//				// valido que la posición sea válida
//				//si la última coordenada de la secuencia excede 
////				if (!isValidCharacter(r, offset + 3/*Parametrizar con respecto al tamaño de secuencia a buscar*/, dna)){
////					break;
////				}
//				
//				if(!isValidCharacter(row, column, dna)) {
//					isFirstCharacter=true;
//					continue;
//				} else {
//					//index y char válidos
//					current = dna[row].charAt(column);
//					if (isFirstCharacter) {
//						if (!isValidCharacter(r, offset + 3,dna))
//							break;
//						charForSequence = current;
//						isFirstCharacter = false;
//						word = "";
//					}
//
//					// Si matchea avanzo en busqueda de la sequencia
//					if (current == charForSequence) {
//
//						count++;
//						word += charForSequence + "";
//						if (count == sequenceLength) {
//
//							log.info("found " + word + " in " + row + ", " + column);
//							if (++found >= numberOfSequencesToFind) {
//								break;
//							}
//							// charForSequence = ' ';
//							isFirstCharacter = true;
//							count = 0;
//							//word = ""; // no ex necesario
//						}
//
//					} else {
//						// Si no corresponde a una secuencia inicio la búsqueda
//						// con el current char y continuo
//						charForSequence = current;
//						count = 0;
//						isFirstCharacter = true;
//						
//						
//						// log.info(word);
//						// word = charForSequence+ "";
//					}
//				}
//
//			}
//		}
//		return found;
//	}
	
	
	public int find(String[] dna) {
		int count = 0;
		int found = 0;

		int row = 0, column = 0, rowEnd = 0, columnEnd = 0;
		char current;
		char charForSequence = ' ';
		boolean isFirstCharacter = false;
		int finalPositionOffset;
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
				finalPositionOffset = offset+3;
				
				row = getRow(r, offset);
				column = getColumn(r, offset);
				
				if (isFirstCharacter ){
					
					//si inicio busqueda de resultado
					//corroboro primero que no me pase sino corto
					rowEnd =  getRow(r, finalPositionOffset);
					columnEnd = getColumn(r, finalPositionOffset);
					
					if(!isInsideMatrix(rowEnd, columnEnd)) {
						continue; // ya no hay resultados válidos en esta interación
						//TODO, si acomodo bien, puedo directamente tirar un break acá y evitar iterar al pedo
					}
					
					current = dna[row].charAt(column);
				}
				
				if(isValidCharacter(row, column, dna)){
					
					//estoy parado en un caracter válido
					//checkeo que la útltima posición sea válida
					
					
					
					isValidCharacter(row, column, dna)){
					}
				}else{
					//estoy sobre un char no válido
					isFirstCharacter=true;
					continue;
				}
						break;
				
				// valido que la posición sea válida
				//si la última coordenada de la secuencia excede 
//				if (!isValidCharacter(r, offset + 3/*Parametrizar con respecto al tamaño de secuencia a buscar*/, dna)){
//					break;
//				}
				
				if(!isValidCharacter(row, column, dna)) {
					isFirstCharacter=true;
					continue;
				} else {
					//index y char válidos
					current = dna[row].charAt(column);
					if (isFirstCharacter) {
						
						//si inicio busqueda de resultado
						//corroboro primero que no me pase
						if (!isValidCharacter(r, offset + 3,dna))
							break;
						charForSequence = current;
						isFirstCharacter = false;
						word = "";
					}

					// Si matchea avanzo en busqueda de la sequencia
					if (current == charForSequence) {

						count++;
						word += charForSequence + "";
						if (count == sequenceLength) {

							log.info("found " + word + " in " + row + ", " + column);
							if (++found >= numberOfSequencesToFind) {
								break;
							}
							// charForSequence = ' ';
							isFirstCharacter = true;
							count = 0;
							//word = ""; // no ex necesario
						}

					} else {
						// Si no corresponde a una secuencia inicio la búsqueda
						// con el current char y continuo
						charForSequence = current;
						count = 0;
						isFirstCharacter = true;
						
						
						// log.info(word);
						// word = charForSequence+ "";
					}
				}

			}
		}
		return found;
	}


//	private boolean isValidCharacter(int row, int column, String[] dna) {
//
//		return isInsideMatrix(row, column) ;// && VALID_CHARACTERS_MAP.contains(dna[row].charAt(column));
//	}
	
	//tengo que evaluar donde inician los oblicuos
	private boolean isValidCharacter(int row, int column, String[]dna) {
		return  VALID_CHARACTERS_MAP.contains(dna[row].charAt(column));
	}

}
