package org.magneto.mutantDetector.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.utils.DnaInputTestCaseInput;
import org.magneto.mutantDetector.utils.TestWithNewRedisServerInstance;

public class MutantDaoTest extends TestWithNewRedisServerInstance {

	@BeforeAll
	public void setup() {
		super.setup();
	}

	@AfterAll
	public void finish() {
		super.finish();
	}

	@Test
	public void saveMutantDnaTest() {
		DnaInputTestCaseInput it = DnaInputTestCaseInput.getMutantDNA();
		MutantDao mutantDao;
		mutantDao = new MutantDao();
		try {
			Assertions.assertTrue(mutantDao.saveMutantDna(it.getDna()), () -> "Se registro una nueva cadena");
			Assertions.assertFalse(mutantDao.saveMutantDna(it.getDna()), () -> "Se registro nuevammente la misma cadena");
		} catch (DBException e) {
			Assertions.fail(e);
		}
	}

}