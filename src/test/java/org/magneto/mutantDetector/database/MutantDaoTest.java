package org.magneto.mutantDetector.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.database.MutantDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.utils.DnaInputTest;
import org.magneto.mutantDetector.utils.TestWithNewRedisServerInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		DnaInputTest it = DnaInputTest.getTestMatrices().get(0);
		MutantDao mutantDao;
		mutantDao = new MutantDao();
		try {
			Assertions.assertTrue(mutantDao.saveMutantDna(it.getDna()), () -> "Se registro una nueva cadena");
			Assertions.assertFalse(mutantDao.saveMutantDna(it.getDna()), () -> "Se registro nuevammente la misma cadena");
			Assertions.assertFalse(mutantDao.saveMutantDna(it.getDna()), () -> "Se registro nuevammente la misma cadena");
		} catch (DBException e) {
			Assertions.fail(e);
		}
	}

}