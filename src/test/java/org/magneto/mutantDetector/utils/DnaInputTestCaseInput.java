package org.magneto.mutantDetector.utils;

import java.util.ArrayList;
import java.util.List;

import org.magneto.mutantDetector.business.mutantDetector.MutantDetector;

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
	private boolean isValid;

	public DnaInputTestCaseInput(String[] dna, int horizontalSequences, int verticalSequences, int obliqueSequences, int inverseSequences) {
		super();
		this.dna = dna;
		this.horizontalSequences = horizontalSequences;
		this.verticalSequences = verticalSequences;
		this.obliqueSequences = obliqueSequences;
		this.inverseObliqueSequences = inverseSequences;
		this.isValid = true;
		this.isMutant = (this.horizontalSequences + this.verticalSequences + obliqueSequences + inverseSequences) >= MutantDetector.N_SEQUENCES_TO_FIND;
	}

	public DnaInputTestCaseInput(String[] dna, boolean valid) {
		super();
		this.dna = dna;
		this.isValid = valid;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
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
	public static DnaInputTestCaseInput getHumanDNAInvalidOnlyForSpeedTest(int repeat) {
		String[] finalDNA;
		String[] dna = { //
				"RTGCPQ", //
				"FGHJKL", //
				"MNBTCX", //
				"HGFDSZ", //
				"MBVCTA", //
				"15AZTX",//
		};
		if (repeat <= 1) {
			finalDNA = dna;
		} else {
			int size = dna.length * repeat;
			finalDNA = new String[dna.length * repeat];
			for (int i = 0; i < size; i++) {
				StringBuilder line = new StringBuilder();
				for (int j = 0; j < repeat; j++) {
					line.append(dna[i%dna.length]);
				}
				finalDNA[i] = line.toString();
			}
		}
		DnaInputTestCaseInput ret = new DnaInputTestCaseInput(finalDNA, 0, 0, 0, 0);
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
		
		DnaInputTestCaseInput ret = new DnaInputTestCaseInput(dna, 0, 0, 0, 0);
		return ret;
	}

	public static DnaInputTestCaseInput getInvalidDNA() {
		String[] dna = { //
				"0 A G ", //
				"0 A C ", //
				"0 A G ", //
				"0GGGGG", //
				"      ", //
				"BBBBBG",//
		};

		DnaInputTestCaseInput ret = new DnaInputTestCaseInput(dna, false);
		return ret;
	}

	public static DnaInputTestCaseInput getExhativeCaseMutantDNA() {

		String[] dna3 = { //
				"TAAAAAAA", //
				"ATGGGGAT", //
				"TTTTTAAC", //
				"CTATAAAG", //
				"CTTCGTAG", //
				"CTCTCGGG", //
				"CCCCGGGG", //
				"CCCCATGG" };//
		return new DnaInputTestCaseInput(dna3, 6, 4, 3, 3);
	}

	public static List<DnaInputTestCaseInput> getAllTestMatrices() {

		ArrayList<DnaInputTestCaseInput> dnas = new ArrayList<DnaInputTestCaseInput>();

		dnas.add(getHumanDNA());
		dnas.add(getMutantDNA());
		dnas.add(getInvalidDNA());
		return dnas;
	}

}