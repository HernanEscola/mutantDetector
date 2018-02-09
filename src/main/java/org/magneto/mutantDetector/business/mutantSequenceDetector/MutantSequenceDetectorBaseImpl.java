package org.magneto.mutantDetector.business.mutantSequenceDetector;

import lombok.extern.log4j.Log4j;

@Log4j
public abstract class MutantSequenceDetectorBaseImpl implements IMutantSequenceDetector {

	protected int numberOfSequencesToFind;
	protected int size;
	protected int sequenceLength;
	// protected String[] dna;

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
		size = dna.length;
		return find(dna);
	}

	/**
	 * Este metodo es medio sucio
	 * 
	 * Quizas si lo pienso un poco mas puedo llegar a acomodarlo
	 * 
	 * @param dna
	 * @return
	 */
	public int find(String[] dna) {
		int found = 0;
		// int count = 0;

		//
		// int row = 0, column = 0;
		// char current;
		// char charForSequence;
		/**
		 * Puedo acotar optimizarlo un poco más
		 * 
		 * Itero por los puntos de partida desde los cuales recorro sobre la matriz en
		 * una direcci'on espec'ifica seg'un la implementaci'on del detector, en
		 * b'usqueda de las secuencias
		 * 
		 *
		 */
		for (int r = 0; r < size && found < numberOfSequencesToFind; r++) {

			found = searchForward(dna, found, r);

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
	 * @param r
	 * @return
	 */
	private int searchForward(String[] dna, int found, int r) {
		char toSearch;
		int row;
		int column;
		int length;
		for (int offset = 0; offset < size && found < numberOfSequencesToFind; offset++) {
			row = getRow(r, offset);
			column = getColumn(r, offset);

			if (!isInsideMatrix(row, column)) {
				break;
			}

			toSearch = dna[row].charAt(column);
			length = countSequenceLength(dna, r, offset, toSearch);
			if (length == sequenceLength) {
				if (++found >= numberOfSequencesToFind) {
					return found;
				}
			}

			// ya recorr'i Lenght, por lo tanto continuo iterando desde ese punto. Resto uno
			// porque el for le suma uno
			offset += (length - 1);

		}
		return found;
	}

	/**
	 * Itera hacia delante (segund el detector que se utilice) en busqueda de una
	 * secuencia de caracteres iguales al parametro "toSearch"
	 * 
	 * @param dna
	 *            Matriz a buscar secuencia
	 * @param r
	 *            Posici'on de inicio de la busqueda de secuencia
	 * @param offset
	 *            Es el offset actual del cual se obtuvo el caracter a buscar.
	 * @param toSearch
	 *            caracter a buscar
	 * @return
	 */
	private int countSequenceLength(String[] dna, int r, int offset, char toSearch) {
		int row;
		int column;
		int limit = offset + sequenceLength;
		int count = 1;

		for (int i = offset + 1; i < limit; i++) {
			row = getRow(r, i);
			column = getColumn(r, i);
			if (!isInsideMatrix(row, column) || toSearch != dna[row].charAt(column)) {
				break;
			}
			count++;
		}

		return count;
	}

}
