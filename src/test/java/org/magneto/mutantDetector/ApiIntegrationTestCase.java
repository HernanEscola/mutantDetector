package org.magneto.mutantDetector;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.DTO.Stats;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.utils.DnaInputTestCaseInput;
import org.magneto.mutantDetector.utils.ServersManager;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * Generé un caso completo para testear la API
 * 
 * @author hescola
 *
 */
public class ApiIntegrationTestCase {

	@Test
	public void integrationTest() throws Exception {
		try {
			ServersManager.StartServers(false);
			final URI baseUrl = ServersManager.ADDRESS;

			Client client = JerseyClientBuilder.newBuilder().build();
			client.register(JacksonJsonProvider.class);
			client.register(JacksonFeature.class);
			Map<Object, Integer> statsMap = new HashMap<>();
			statsMap.put(EDnaType.MUTANT, 0);
			statsMap.put(EDnaType.HUMAN, 0);

			log.info("Test Post DNA");

			testAllAdnTestCasesSingleMutantApiRequest(baseUrl, client, statsMap);

			statsRestRequest(baseUrl, client, statsMap);
			ServersManager.shutdownRedisServer();
		} catch (Exception e) {
			log.error("Error inesperado ");
			Assert.fail("Ocurrio un error inesperado. Ver exception:" + e.getMessage());
		}
	}

	private void testAllAdnTestCasesSingleMutantApiRequest(final URI baseUrl, Client client, Map<Object, Integer> statsMap) {
		for (DnaInputTestCaseInput dnaStruct : DnaInputTestCaseInput.getAllTestMatrices()) {
			testSingleMutantApiRequest(baseUrl, client, statsMap, dnaStruct);
		}
	}

	private void testSingleMutantApiRequest(final URI baseUrl, Client client, Map<Object, Integer> statsMap, DnaInputTestCaseInput dnaStruct) {
		Dna dna = new Dna();
		dna.setDna(dnaStruct.getDna());
		WebTarget postTarget = client.target(baseUrl).path("/mutant");
		Response responseObject = postTarget.request(MediaType.TEXT_PLAIN_TYPE).post(Entity.entity(dna, MediaType.APPLICATION_JSON_TYPE), Response.class);

		log.info(responseObject.toString());
		if (!dnaStruct.isValid()) {
			Assertions.assertEquals(Status.BAD_REQUEST.getStatusCode(), responseObject.getStatus());
		} else if (dnaStruct.isMutant()) {
			incrMapCount(statsMap, EDnaType.MUTANT);
			Assertions.assertEquals(Status.OK.getStatusCode(), responseObject.getStatus());
		} else {
			incrMapCount(statsMap, EDnaType.HUMAN);
			Assertions.assertEquals(Status.FORBIDDEN.getStatusCode(), responseObject.getStatus());
		}
	}

	/**
	 * TODO: Mover a una Clase utils Incrementa en uno el valor que se encuentra en
	 * la clave pasada por par
	 * 
	 */
	private Integer incrMapCount(Map<Object, Integer> map, Object key) {
		return map.compute(key, (k, v) -> v == null ? 1 : v + 1);
	}

	private void statsRestRequest(final URI baseUrl, Client client, Map<Object, Integer> statsMap) {
		log.info("Stats Test");
		Response testResponse;
		WebTarget target;
		target = client.target(baseUrl).path("/stats");
		testResponse = target.request(MediaType.APPLICATION_JSON).get();
		log.info(testResponse.toString());
		Assertions.assertEquals(200, testResponse.getStatus());
		Stats stats = testResponse.readEntity(Stats.class);

		Integer humansCount = statsMap.get(EDnaType.HUMAN);
		Integer mutantsCount = statsMap.get(EDnaType.MUTANT);

		Stats expectedStats = new Stats(mutantsCount, humansCount, calculateRatio(humansCount, mutantsCount));
		Assertions.assertEquals(expectedStats.getCount_human_dna(), stats.getCount_human_dna(), "Humans Stat");
		Assertions.assertEquals(expectedStats.getCount_mutant_dna(), stats.getCount_mutant_dna(), "Mutants Stat");
		Assertions.assertEquals(expectedStats.getRatio(), stats.getRatio(), "Ratio Stat");
	}

	private float calculateRatio(Integer humansCount, Integer mutantsCount) {
		// no se si es correcto proque el resultado va a dar una relacion 1/1 cuando no
		// hay humanos registrados, pero en teor'ia hay mas humanos que mutantes
		return (float) mutantsCount / ((float) humansCount == 0 ? 1 : humansCount);
	}

}