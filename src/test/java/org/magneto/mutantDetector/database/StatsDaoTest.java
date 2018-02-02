package org.magneto.mutantDetector.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.DTO.Stats;
import org.magneto.mutantDetector.database.StatsDao;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.utils.ServersManager;
import org.magneto.mutantDetector.utils.TestWithNewRedisServerInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatsDaoTest extends TestWithNewRedisServerInstance {

	private StatsDao statsDao;

	@BeforeAll
	public void setup() {
		super.setup();
		statsDao = new StatsDao();
	}

	@AfterAll
	public void finish() {
		super.finish();
	}

	@Test
	public void addHumanTest() {
		Long humans = 0l;
		// Todo: Refactorizar
		try {
			assertEquals(statsDao.addHuman(), ++humans, () -> "Humanos consultados");
			assertEquals(statsDao.addHuman(), ++humans, () -> "Humanos consultados");
			assertStats(0L, humans);
		} catch (DBException e1) {
			fail(e1);
		}
		try {
			ServersManager.stopRedisCurrentInstance();
			statsDao.addHuman(); // Arroja excepción al no tener el server
			Assertions.fail("Se Esperaba una excepción");
		} catch (Exception e) {
			// TODO: hay que validar si es la excepción que esperaba
			assertEquals(DBException.class, e.getClass(), "Incorrecto tipo de Excepción");
		} finally {
			try {
				ServersManager.getRedisStarted();
			} catch (IOException e) {
				fail(e);
			}
		}
	}

	/**
	 * TODO: Implementar Mockito para poder lograr atomizar todos los test
	 */
	@Test
	public void addMutantTest() {
		Long mutants = 0l;
		try {

			// Todo: Refactorizar
			assertEquals(statsDao.addMutant(), ++mutants, () -> "Se registraro 1 Mutantes");
			assertEquals(statsDao.addMutant(), ++mutants, () -> "Se registraron 2 Mutantes");
			assertStats(mutants, 0l);
		} catch (Exception e) {
			fail(e);
		}
		// test Excepttion
		try {
			ServersManager.stopRedisCurrentInstance();
			statsDao.addMutant(); // Arroja excepción al no tener el server
			fail("Se esperaba una DaoException pero no se arrojó");
		} catch (Exception e) {
			// TODO: hay que validar si es la excepción que esperaba
			assertEquals(DBException.class, e.getClass(), "Incorrecto tipo de Excepción");
		} finally {
			try {
				ServersManager.getRedisStarted();
			} catch (IOException e) {
				fail(e);
			}
		}
	}

	@Test
	public void ratioTest() {
		try {
			ServersManager.startCleanRedisInstance();
		} catch (IOException e) {
			fail(e);
		}
		long mutants = 4, humans = 2;
		try {
			assertStats(0L, 0L);
			for (int i = 0; i < mutants; i++) {
				statsDao.addMutant();
			}
			for (int i = 0; i < humans; i++) {
				statsDao.addHuman();
			}
			assertStats(mutants, humans);

		} catch (Exception e) {
			fail(e);
		}
	}

	private Stats assertStats(Long mutants, Long humans) throws DBException {
		Stats stats = statsDao.getStats();
		Float ratio = (float) humans / ((float) mutants == 0 ? 1 : mutants);
		assertEquals(stats.getCount_human_dna(), Integer.valueOf(humans.intValue()), () -> "Humans Stat");
		assertEquals(stats.getCount_mutant_dna(), Integer.valueOf(mutants.intValue()), () -> "Mutants Stat ");
		assertEquals(stats.getRatio(), ratio, () -> "Ratio Stat Humans/Mutant");
		return stats;
	}

}