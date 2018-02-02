package org.magneto.mutantDetector.services;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.mutantSequenceDetector.IMutantSequenceDetector;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.HorizontalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.InverseObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.ObliqueMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.business.mutantSequenceDetector.impl.VerticalMutantSequenceDetectorImpl;
import org.magneto.mutantDetector.database.MutantDao;
import org.magneto.mutantDetector.database.StatsDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;
import org.magneto.mutantDetector.services.MutantServiceImpl;
import org.magneto.mutantDetector.services.StatsServiceImpl;
import org.magneto.mutantDetector.utils.DnaInputTestCaseInput;
import org.magneto.mutantDetector.utils.TestWithNewRedisServerInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
public class MutantServiceImplTest extends TestWithNewRedisServerInstance {

	List<DnaInputTestCaseInput> dnaTestCases;

	@BeforeAll
	public void setup() {
		super.setup();
	}

	@AfterAll
	public void finish() {
		super.finish();
	}

	/**
	 * Separar cada caso en un test
	 * 
	 * @throws Exception
	 */
	@Test
	public void checkMutantSequenceDetectorTest() throws Exception {
		List<DnaInputTestCaseInput> dnas = DnaInputTestCaseInput.getTestMatrices();

		/**
		 * hago dos iteraciones, una con el limite minimo para que sea mutante
		 * para ver si el las funciones cortan al encontrarlo, y la segunda
		 * iteracion es para ver si encuentra todas las variantes posible para
		 * cada direccion
		 */

		int[] minVal = {  Integer.MAX_VALUE };

		int sequenceLength = 4;
		IMutantSequenceDetector horizontalMsd = new HorizontalMutantSequenceDetectorImpl();
		IMutantSequenceDetector verticalMsd = new VerticalMutantSequenceDetectorImpl();
		IMutantSequenceDetector inverseObliquelMsd = new InverseObliqueMutantSequenceDetectorImpl();
		IMutantSequenceDetector obliquelMsd = new ObliqueMutantSequenceDetectorImpl();
		for (DnaInputTestCaseInput dnaStruct : dnas) {
			String[] dna = dnaStruct.getDna();
			for (int sequencesToFind : minVal) {
				horizontalMsd.init(sequenceLength, sequencesToFind);
				Assert.assertEquals("Cantidad de secuencias Horitonzales Detector", Math.min(sequencesToFind, dnaStruct.getHorizontalSequences()),
						horizontalMsd.detect(dna));
				verticalMsd.init(sequenceLength, sequencesToFind);

				Assert.assertEquals("Cantidad de secuencias Verticales Detector", Math.min(sequencesToFind, dnaStruct.getVerticalSequences()),
						verticalMsd.detect(dna));

				inverseObliquelMsd.init(sequenceLength, sequencesToFind);
				Assert.assertEquals("Cantidad de secuencias Oblicuas Inversas", Math.min(sequencesToFind, dnaStruct.getInverseObliqueSequences()),
						inverseObliquelMsd.detect(dna));

				obliquelMsd.init(sequenceLength, sequencesToFind);
				Assert.assertEquals("Cantidad de secuencias Oblicuas", Math.min(sequencesToFind, dnaStruct.getObliqueSequences()),
						obliquelMsd.detect(dna));

			}
		}
	}

	@Test
	public void testIsMutant() {

		List<DnaInputTestCaseInput> dnas = DnaInputTestCaseInput.getTestMatrices();

		for (DnaInputTestCaseInput dnaStruct : dnas) {
			Dna dna = new Dna();
			Assert.assertEquals(dnaStruct.isMutant(), createMuntantService().isMutant(dnaStruct.getDna()));
		}
	}

	@Test
	public void analizeDnaTest() {

		List<DnaInputTestCaseInput> dnas = DnaInputTestCaseInput.getTestMatrices();

		for (DnaInputTestCaseInput dnaStruct : dnas) {
			Dna dna = new Dna();
			dna.setDna(dnaStruct.getDna());
			try {
				MutantServiceImpl service = createMuntantService();

				Assertions.assertEquals(dnaStruct.isMutant(), service.analizeDna(dna));
			} catch (InvalidDnaException e) {
				Assertions.assertFalse(dnaStruct.isValid());
			} catch (DBException e) {
				Assertions.fail(e);
			}
		}
	}

	private MutantServiceImpl createMuntantService() {

		return new MutantServiceImpl(new MutantDao(), new StatsServiceImpl(new StatsDao()));
	}

	// @Test
	// public void testSpeedOfDetectors() throws Exception {
	// // create a jersey client
	// int iterations = 100000;
	// Long startTime = System.currentTimeMillis();
	//
	// for (int i = 0; i < iterations; i++) {
	// checkMutantSequenceDetectorTest();
	// }
	// Long finish = System.currentTimeMillis();
	//
	// log.debug("DETECTOR: Ejecutadas " + iterations + " iteraciones en " +
	// (finish - startTime));
	//
	// }
}