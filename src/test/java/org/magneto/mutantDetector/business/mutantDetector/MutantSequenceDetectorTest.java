
package org.magneto.mutantDetector.business.mutantDetector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.magneto.mutantDetector.business.mutantSequenceDetector.DnaValidator;
import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.MutantDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.HorizontalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.InverseObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.ObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.VerticalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;
import org.magneto.mutantDetector.services.MutantServiceImpl;
import org.magneto.mutantDetector.services.interfaces.MutantService;
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
	/**
	 * TODO: Convertir en 3 test
	 */
	public void testIsValidDNA() {
		DnaInputTestCaseInput humanDna = DnaInputTestCaseInput.getHumanDNA();
		DnaInputTestCaseInput mutantDna = DnaInputTestCaseInput.getMutantDNA();
		DnaInputTestCaseInput invalidDna = DnaInputTestCaseInput.getInvalidDNA();

		MutantDetector msd = new MutantDetector();
		DnaValidator dnaValidator = new DnaValidator();
		String mensaje = "ADN VALIDO";
		try {
			Assertions.assertTrue(dnaValidator.validate(humanDna.getDna()), mensaje);
			Assertions.assertTrue(dnaValidator.validate(mutantDna.getDna()), mensaje);
		} catch (InvalidDnaException e) {
			// TODO Auto-generated catch block
			Assertions.fail(e);
		}

		// Refactorizar excepciones
		Assertions.assertThrows(InvalidDnaException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				dnaValidator.validate(invalidDna.getDna());

			}
		}, mensaje);

	}

	/**
	 * ANALIZA CADENAS DE ADN VALIDAS EN BUSCA DE FALLAS EN LOS COMPONENTES DEL
	 * DETECTOR DE ADN HORIZONTAL
	 *
	 * @throws Exception
	 */
	@Test
	public void horizontalSequenceDetectorTest() throws Exception {
		IMutantSequenceDetector horizontalMsd = new HorizontalMutantSequenceDetectorImpl(MutantDetector.MUTANT_SEQUENCE_LENGTH);
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
		IMutantSequenceDetector horizontalMsd = new VerticalMutantSequenceDetectorImpl(MutantDetector.MUTANT_SEQUENCE_LENGTH);
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
		IMutantSequenceDetector oblicqueMsd = new ObliqueMutantSequenceDetectorImpl(MutantDetector.MUTANT_SEQUENCE_LENGTH);
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
		IMutantSequenceDetector oblicqueMsd = new InverseObliqueMutantSequenceDetectorImpl(MutantDetector.MUTANT_SEQUENCE_LENGTH);
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
			int[] maxNumbersOfSequence = { MutantDetector.N_SEQUENCES_TO_FIND, Integer.MAX_VALUE };
			for (int maxNumberOfSequence : maxNumbersOfSequence) {
				Assertions.assertEquals(Math.min(maxNumberOfSequence, expected), mutantSeqDetector.detect(dna, maxNumberOfSequence), message);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Assertions.fail(e);
		}
	}

	@Test
	/**
	 * TODO: Convertir en 2 test
	 */
	public void testIsMutant() {
		DnaInputTestCaseInput humanDna = DnaInputTestCaseInput.getHumanDNA();
		DnaInputTestCaseInput mutantDna = DnaInputTestCaseInput.getMutantDNA();
		MutantDetector mutantDetector = new MutantDetector();
		Assertions.assertFalse(mutantDetector.isMutant(humanDna.getDna()), "Es mutante");
		Assertions.assertTrue(mutantDetector.isMutant(mutantDna.getDna()), "Es mutante");
	}

	@Test
	public void isMutantSpeedTest() {

		String[] dna = DnaInputTestCaseInput.getHumanDNA().getDna();
		MutantDetector mutantDetector = new MutantDetector();
		Long start = System.currentTimeMillis();
		int iterations = 1000000;
		for (int i = 0; i < iterations; i++) {
			mutantDetector.isMutant(dna);
		}

		Long finish = System.currentTimeMillis();
		log.info("Finish Speed test in" + (finish - start) + "ms");
	}
}
