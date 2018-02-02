package org.magneto.mutantDetector.utils;

import java.util.ArrayList;
import java.util.List;
/**
 * Estructura que contiene un dna input y sus respectivos resultados para luego validar contra el detector de sequencias
 * @author aion
 *
 */
public class DnaInputTest {
		private String[] dna;
		private int horizontalSequences;
		private int verticalSequences;
		private int obliqueSequences;
		private int inverseObliqueSequences;
		private boolean isMutant;
		private boolean valid;
		
		public DnaInputTest(String[] dna, int horizontalSequences, int verticalSequences, int obliqueSequences, int inverseSequences, boolean isValid) {
			super();
			this.dna = dna;
			this.horizontalSequences = horizontalSequences;
			this.verticalSequences = verticalSequences;
			this.obliqueSequences = obliqueSequences;
			this.inverseObliqueSequences = inverseSequences;
			
			this.isMutant = (this.horizontalSequences + this.verticalSequences + obliqueSequences + inverseSequences) >=2;
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

		public boolean isValid() {
			return valid;
		}

		public void setValid(boolean isValid) {
			this.valid = isValid;
		}

		/**
		 * Matriz que tiene 3 soluciones horizontales 3 verticales y 2 diagonales
		 * 
		 * @return
		 */
		public static List<DnaInputTest> getTestMatrices() {
			
			 ArrayList<DnaInputTest> dnas = new ArrayList<DnaInputTest>();
		
			String[] dna1 = { //
					"aaaaa", //
					"addbd", // 1 oblicua
					"adddd", // 1h
					"aaddd", //
					"adddd" };// 1h 1oblique inverse
			// 3 verticals 3 horizontales, 1 oblicuo y 1 oblicuo inverso
			dnas.add(new DnaInputTest(dna1, 3, 3, 1, 1, true));
		
			String[] dna2 = { //
					"accca", //
					"aaaab", //
					"acabb", //
					"aabab", //
					"bbbbb" };//
			// 4 verticals 3 horizontales, 1 oblicuo y 1 oblicuo inverso
			dnas.add(new DnaInputTest(dna2, 2, 2, 1, 2, true));
		
			String[] dna3 = { //
					"TAAAAAAA", //
					"ATGGGGAT", //
					"TTTTTAAC", //
					"CTATAAAG", //
					"CTTCGTAG", //
					"CTCTCGGG", //
					"CCCCGGGG", //
					"CCCCATGG" };//
			dnas.add(new DnaInputTest(dna3, 6, 4, 3, 3, true));
		
			String[] dna4 = { //
					"TADAPAAC", //
					"OKMIJNUH", //
					"BHUNJIMK", //
					"OKMIJNUB", //
					"MKONJIBH", //
					"CTCTCGGO", //
					"MKOOKMMK", //
					"UHBIJNOK" };//
			// 1 hori, y 1 vert
			dnas.add(new DnaInputTest(dna4, 0, 1, 0, 0,true));
		
			return dnas;
		}

		
		
		

	}