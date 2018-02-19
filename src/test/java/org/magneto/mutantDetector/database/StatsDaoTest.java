package org.magneto.mutantDetector.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.DTO.Stats;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.utils.ServersManager;
import org.magneto.mutantDetector.utils.TestWithNewRedisServerInstance;

public class StatsDaoTest extends TestWithNewRedisServerInstance {

	private StatsDao statsDao;

	@BeforeEach
	public void setup() {
		super.setup();
		statsDao = new StatsDao();
	}

	@AfterAll
	public void finish() {
		super.finish();
	}

	@Test
	public void testRatio() {
		try {
			int humans = 100;
			int mutants = 40;
			Stats expected = new Stats(mutants, humans, ((float) mutants / humans));
			addHumansAndMutantsToDB(humans, mutants);
			Stats stats = statsDao.getStats();
			assertEquals(expected.getCount_human_dna(), stats.getCount_human_dna(), () -> "Humans Stat");
			assertEquals(expected.getCount_mutant_dna(), stats.getCount_mutant_dna(), () -> "Mutants Stat ");
			assertEquals(expected.getRatio(), stats.getRatio(), () -> "Ratio Stat Humans/Mutant");
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

	private void addHumansAndMutantsToDB(int humans, int mutants) throws DBException {
		for (int h = 0; h < humans; h++) {
			statsDao.addHuman();
		}
		for (int m = 0; m < mutants; m++) {
			statsDao.addMutant();
		}
	}

	@Test
	public void addHumanTest() {
		Long humans = 0l;
		try {
			assertEquals(statsDao.addHuman(), ++humans, () -> "Humanos consultados");
			assertEquals(statsDao.addHuman(), ++humans, () -> "Humanos consultados");
		} catch (DBException e) {
			fail(e);
		}

	}

	/**
	 * TODO: Implementar Mockito para poder lograr atomizar todos los test
	 */
	@Test
	public void addMutantTest() {
		Long mutants = 0l;
		try {
			assertEquals(statsDao.addMutant(), ++mutants, () -> "Se registraro 1 Mutantes");
			assertEquals(statsDao.addMutant(), ++mutants, () -> "Se registraron 2 Mutantes");
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	public void test_redis_db_excepcion() {
		// test Exception
		try {
			ServersManager.stopRedisCurrentInstance();
			statsDao.addMutant(); // Arroja excepción al no tener el server
			fail("Se esperaba una DaoException pero no se arrojó");
		} catch (Exception e) {
			assertEquals(DBException.class, e.getClass(), "Incorrecto tipo de Excepción");
		} finally {
			try {
				ServersManager.getRedisStarted();
			} catch (IOException e) {
				fail(e);
			}
		}
	}

}