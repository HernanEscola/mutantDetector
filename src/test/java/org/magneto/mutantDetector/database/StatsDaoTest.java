package org.magneto.mutantDetector.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
	/**
	 * Test utilizando caso de ejemplo del enunciado
	 */
	public void testRatio() {
		try {
			int humans = 100;
			int mutants = 40;
			Stats expected = new Stats(mutants, humans, ((float) mutants / humans));
			for (int h = 0; h < humans; h++) {
				statsDao.addHuman();
			}
			for (int m = 0; m < mutants; m++) {
				statsDao.addMutant();
			}

			Stats stats = statsDao.getStats();

			assertEquals(expected.getCount_human_dna(), stats.getCount_human_dna(), () -> "Humans Stat");
			assertEquals(expected.getCount_mutant_dna(), stats.getCount_mutant_dna(), () -> "Mutants Stat ");
			assertEquals(expected.getRatio(), stats.getRatio(), () -> "Ratio Stat Humans/Mutant");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assertions.fail(e);
		}
	}

	@Test
	public void addHumanTest() {
		Long humans = 0l;
		// Todo: Refactorizar
		try {
			assertEquals(statsDao.addHuman(), ++humans, () -> "Humanos consultados");
			assertEquals(statsDao.addHuman(), ++humans, () -> "Humanos consultados");
			ServersManager.stopRedisCurrentInstance();
			statsDao.addHuman(); // Arroja excepción al no tener el server
			Assertions.fail("Se Esperaba una excepción");
		} catch (Exception e) {
			// TODO: hay que validar si es la excepción que esperaba
			assertEquals(DBException.class, e.getClass(), "Incorrecto tipo de Excepción");
		} finally {
			try {
				ServersManager.getRedisStarted();
			} catch (Exception e) {
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
		} catch (Exception e) {
			fail(e);
		}
		// test Exception
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

}