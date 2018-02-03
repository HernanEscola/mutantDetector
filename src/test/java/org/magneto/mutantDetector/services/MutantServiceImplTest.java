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

}