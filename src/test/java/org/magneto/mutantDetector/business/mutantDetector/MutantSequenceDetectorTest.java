
package org.magneto.mutantDetector.business.mutantDetector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantSequenceDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.HorizontalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.InverseObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.ObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.VerticalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.services.MutantServiceImpl;
import org.magneto.mutantDetector.utils.DnaInputTestCaseInput;

import lombok.extern.log4j.Log4j;

/**
 * TODO: Mejorar atomizando el test en los metodos que debe implementar
 * MutantSequenceDetector
 * 
 * @author hescola
 *
 */
@Log4j
public class MutantSequenceDetectorTest {

	@Test
	public void testIsValidDNA() {
		DnaInputTestCaseInput humanDna = DnaInputTestCaseInput.getHumanDNA();
		DnaInputTestCaseInput mutantDna = DnaInputTestCaseInput.getMutantDNA();
		DnaInputTestCaseInput invalidDna = DnaInputTestCaseInput.getInvalidDNA();

		MutantSequenceDetector msd = new MutantSequenceDetector();

		int sequencelength = MutantServiceImpl.MUTANT_SEQUENCE_LENGTH;
		String mensaje = "ADN VALIDO";
		Assertions.assertTrue(msd.isValid(humanDna.getDna(), sequencelength), mensaje);
		Assertions.assertTrue(msd.isValid(mutantDna.getDna(), sequencelength), mensaje);
		Assertions.assertFalse(msd.isValid(invalidDna.getDna(), sequencelength), mensaje);
	}

	/**
	 * ANALIZA CADENAS DE ADN VALIDAS EN BUSCA DE FALLAS EN LOS COMPONENTES DEL
	 * DETECTOR DE ADN HORIZONTAL
	 *
	 * @throws Exception
	 */
	@Test
	public void horizontalSequenceDetectorTest() throws Exception {
		IMutantSequenceDetector horizontalMsd = new HorizontalMutantSequenceDetectorImpl();
		DnaInputTestCaseInput dna = DnaInputTestCaseInput.getExhativeCaseMutantDNA();
		String message = "HORIZONTALES";
		genericMutantSequenceDetectorTest(horizontalMsd, message, dna.getHorizontalSequences(), dna.getDna());
	}

	/**
	 * ANALIZA CADENAS DE ADN VALIDAS EN BUSCA DE FALLAS EN LOS COMPONENTES DEL
	 * DETECTOR DE ADN VERTICAL
	 *
	 * @throws Exception
	 */
	@Test
	public void verticalSequenceDetectorTest() throws Exception {
		IMutantSequenceDetector horizontalMsd = new VerticalMutantSequenceDetectorImpl();
		DnaInputTestCaseInput dna = DnaInputTestCaseInput.getExhativeCaseMutantDNA();
		String message = "VERTICALES";
		genericMutantSequenceDetectorTest(horizontalMsd, message, dna.getVerticalSequences(), dna.getDna());
	}

	/**
	 * ANALIZA CADENAS DE ADN VALIDAS EN BUSCA DE FALLAS EN LOS COMPONENTES DEL
	 * DETECTOR DE ADN OBLICUOS
	 *
	 * @throws Exception
	 */
	@Test
	public void obliqueSequenceDetectorTest() throws Exception {
		IMutantSequenceDetector oblicqueMsd = new ObliqueMutantSequenceDetectorImpl();
		DnaInputTestCaseInput dna = DnaInputTestCaseInput.getExhativeCaseMutantDNA();
		String message = "OBLICUOS";
		genericMutantSequenceDetectorTest(oblicqueMsd, message, dna.getObliqueSequences(), dna.getDna());
	}

	/**
	 * ANALIZA CADENAS DE ADN VALIDAS EN BUSCA DE FALLAS EN LOS COMPONENTES DEL
	 * DETECTOR DE ADN OBLICUOS INVERSOS
	 *
	 * @throws Exception
	 */
	@Test
	public void inverseObliqueSequenceDetectorTest() throws Exception {
		IMutantSequenceDetector oblicqueMsd = new InverseObliqueMutantSequenceDetectorImpl();
		DnaInputTestCaseInput dna = DnaInputTestCaseInput.getExhativeCaseMutantDNA();
		String message = "OBLICUOS INVERSOS";
		genericMutantSequenceDetectorTest(oblicqueMsd, message, dna.getInverseObliqueSequences(), dna.getDna());
	}

	/**
	 * Pone a prueba los componentes del detector de secuencias mutantes testeando
	 * todo su correcto comportamiento La primer iteracion comprueba que el
	 * algoritmo se interrumpa al encontrar el resutlado minimo necesario. La
	 * segunda simplemente comprueba que el algoritmo encuentre todas las secuencias
	 * que haya.
	 * 
	 */
	private void genericMutantSequenceDetectorTest(IMutantSequenceDetector mutantSeqDetector, String tipoDetector, int expected, String[] dna) {

		try {
			String message = "Detector de Cantidad de secuencias " + tipoDetector;
			int sequenceLength = 4;
			int[] minVal = {2,  Integer.MAX_VALUE };

			// INVOCACION AL METODO PARA FILTRAR LOS CARACERES
			// INVALIDOS REEMPLAZANDOLOS POR UN PLACEGHOLDER
			// CARACTERES INVALIDOS.
			// dna = filter.filter(dna);

			for (int sequencesToFind : minVal) {
				mutantSeqDetector.init(sequenceLength, sequencesToFind);
				Assertions.assertEquals(Math.min(sequencesToFind, expected), mutantSeqDetector.detect(dna), message);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Assertions.fail(e);
		}
	}

}
