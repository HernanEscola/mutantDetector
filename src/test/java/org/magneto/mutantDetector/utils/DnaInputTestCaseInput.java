package org.magneto.mutantDetector.utils;

import java.util.ArrayList;
import java.util.List;

import org.magneto.mutantDetector.services.MutantServiceImpl;

/**
 * Estructura que contiene un dna input y sus respectivos resultados para luego
 * validar contra el detector de sequencias
 * 
 * Estos casos solo son validos para testear sequencias de longitud 4
 * 
 * @author aion
 *
 */
public class DnaInputTestCaseInput {
	private String[] dna;
	private int horizontalSequences;
	private int verticalSequences;
	private int obliqueSequences;
	private int inverseObliqueSequences;
	private boolean isMutant;

	public DnaInputTestCaseInput(String[] dna, int horizontalSequences, int verticalSequences, int obliqueSequences, int inverseSequences) {
		super();
		this.dna = dna;
		this.horizontalSequences = horizontalSequences;
		this.verticalSequences = verticalSequences;
		this.obliqueSequences = obliqueSequences;
		this.inverseObliqueSequences = inverseSequences;

		this.isMutant = (this.horizontalSequences + this.verticalSequences + obliqueSequences + inverseSequences) >= MutantServiceImpl.N_SEQUENCES_TO_FIND;
	}

	public String[] getDna() {
		return dna;
	}

	public int getHorizontalSequences() {
		return horizontalSequences;
	}

	public int getVerticalSequences() {
		return verticalSequences;
	}

	public int getObliqueSequences() {
		return obliqueSequences;
	}

	public int getInverseObliqueSequences() {
		return inverseObliqueSequences;
	}

	public boolean isMutant() {
		return isMutant;
	}

	/**
	 * Caso mutante proporcionado en el enunciado
	 * 
	 * @return
	 */
	public static DnaInputTestCaseInput getMutantDNA() {

		String[] dna = { //
				"ATGCGA", //
				"CAGTGC", //
				"TTATGT", //
				"AGAAGG", //
				"CCCCTA", //
				"TCACTG",//
		};
		DnaInputTestCaseInput ret = new DnaInputTestCaseInput(dna, 1, 1, 1, 0);
		return ret;
	}

	/**
	 * Caso humano derivado del mutante
	 * 
	 * @return
	 */
	public static DnaInputTestCaseInput getHumanDNA() {
		String[] dna = { //
				"ATGCGA", //
				"CAGTGC", //
				"TTGTGT", //
				"AGAAGG", //
				"TCCCTA", //
				"TCACTG",//
		};
		DnaInputTestCaseInput ret = new DnaInputTestCaseInput(dna, 0, 1, 0, 0);
		return ret;
	}

	public static DnaInputTestCaseInput getHumanWithInvalidSequences() {
		String[] dna = { //
				"0 A G ", //
				"0 A C ", //
				"0 A G ", //
				"0GGGGG", //
				"      ", //
				"BBBBBG",//
		};

		DnaInputTestCaseInput ret = new DnaInputTestCaseInput(dna, 1, 0, 0, 0);
		return ret;
	}

	/**
	 * Matriz que tiene 3 soluciones horizontales 3 verticales y 2 diagonales
	 * 
	 * @return
	 */
	public static List<DnaInputTestCaseInput> getTestMatrices() {

		ArrayList<DnaInputTestCaseInput> dnas = new ArrayList<DnaInputTestCaseInput>();

		// String[] dna1 = { //
		// "AAAAA", //
		// "ATTCT", //
		// "ATTTT", //
		// "AATTT", //
		// "ATTTT" };//
		// // 3 verticals 3 horizontales, 1 oblicuo y 1 oblicuo inverso
		// dnas.add(new DnaInputTestCaseInput(dna1, 3, 3, 1, 1, true));
		//
		// String[] dna2 = { //
		// "ACCCA", //
		// "AAAAT", //
		// "ACATT", //
		// "AATAT", //
		// "TTTTT" };//
		// // 4 verticals 3 horizontales, 1 oblicuo y 1 oblicuo inverso
		// dnas.add(new DnaInputTestCaseInput(dna2, 2, 2, 1, 2, true));

		String[] dna3 = { //
				"TAAAAAAA", //
				"ATGGGGAT", //
				"TTTTTAAC", //
				"CTATAAAG", //
				"CTTCGTAG", //
				"CTCTCGGG", //
				"CCCCGGGG", //
				"CCCCATGG" };//
		dnas.add(new DnaInputTestCaseInput(dna3, 6, 4, 3, 3));

		String[] dna4 = { // //
				"QQQQQQQQ", //
				"QTTTTQQQ", //
				"QTQQQQQQ", //
				"QTQQQQQQ", //
				"QTQQQQQQ", //
				"QQQQQQQQ", //
				"QQQQQQQQ", //
				"QQQQQQQQ" };// // es inválido si valido que esté contenido en {A,T,C,G}
		// solo contiene una columna de Ts, porl o tanto las otras sequencias que
		// enceuntre no debería validarlo como mutante
		dnas.add(new DnaInputTestCaseInput(dna4, 1, 1, 0, 0));

		String[] dna5 = { // //
				"QQQQQQQQ", //
				"QTT TQQQ", //
				"QTQQQQQQ", //
				"QTQQQQQQ", //
				"QTQQQQQQ", //
				"QQQQQQQQ", //
				"QQQQQQQQ", //
				"QQQQQQQQ" };// // es inválido si valido que esté contenido en {A,T,C,G}
		// solo contiene una columna de Ts, porl o tanto las otras sequencias que
		// enceuntre no debería validarlo como mutante
		dnas.add(new DnaInputTestCaseInput(dna5, 0, 1, 0, 0));

		return dnas;
	}

}