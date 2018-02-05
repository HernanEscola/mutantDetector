package org.magneto.mutantDetector.services;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.database.MutantDao;
import org.magneto.mutantDetector.database.StatsDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;
import org.magneto.mutantDetector.services.interfaces.MutantService;
import org.magneto.mutantDetector.utils.DnaInputTestCaseInput;
import org.magneto.mutantDetector.utils.TestWithNewRedisServerInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
public class MutantServiceTest extends TestWithNewRedisServerInstance {

	List<DnaInputTestCaseInput> dnaTestCases;

	@BeforeAll
	public void setup() {
		super.setup();
	}

	@AfterAll
	public void finish() {
		super.finish();
	}

	@Test
	public void testIsMutant() {
		// List<DnaInputTestCaseInput> dnas = DnaInputTestCaseInput.getTestMatrices();
		DnaInputTestCaseInput humanDna = DnaInputTestCaseInput.getHumanDNA();
		DnaInputTestCaseInput mutantDna = DnaInputTestCaseInput.getMutantDNA();
		MutantService mutantService = instantiateMuntantService();
		Assertions.assertFalse(mutantService.isMutant(humanDna.getDna()), "Es mutante");
		Assertions.assertTrue(mutantService.isMutant(mutantDna.getDna()), "Es mutante");
	}

	@Test
	public void isMutantSpeedTest() {

		String[] dna = DnaInputTestCaseInput.getHumanDNA().getDna();
		MutantService service = instantiateMuntantService();
		Long start = System.currentTimeMillis();
		int iterations = 1000000;
		for (int i = 0; i < iterations; i++) {
			service.isMutant(dna);
		}

		Long finish = System.currentTimeMillis();
		log.info("Finish Speed test in" + (finish - start) + "ms");
	}

	@Test
	/**
	 * TODO: Agregar Mock para solamente testear analizeDNA
	 */
	public void analizeDnaTest() {

		List<DnaInputTestCaseInput> dnas = DnaInputTestCaseInput.getAllTestMatrices();
		MutantService service = instantiateMuntantService();
		for (DnaInputTestCaseInput dnaStruct : dnas) {
			Dna dna = new Dna();
			dna.setDna(dnaStruct.getDna());
			try {

				Assertions.assertEquals((dnaStruct.isMutant() ? EDnaType.MUTANT : EDnaType.HUMAN), service.analizeDna(dna));
			} catch (DBException e) {
				Assertions.fail(e);
			} catch (InvalidDnaException e) {
				Assertions.assertFalse(dnaStruct.isValid());
			}
		}
	}

	private MutantService instantiateMuntantService() {

		return new MutantServiceImpl(new MutantDao(), new StatsServiceImpl(new StatsDao()));
	}

}