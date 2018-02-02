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
import org.magneto.mutantDetector.business.enums.STATS_KEYS;
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
			statsMap.put(STATS_KEYS.MUTATNS, 0);
			statsMap.put(STATS_KEYS.HUMANS, 0);

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
		for (DnaInputTestCaseInput dnaStruct : DnaInputTestCaseInput.getTestMatrices()) {
			testSingleMutantApiRequest(baseUrl, client, statsMap, dnaStruct);

		}
	}

	private void testSingleMutantApiRequest(final URI baseUrl, Client client, Map<Object, Integer> statsMap, DnaInputTestCaseInput dnaStruct) {
		Dna dna = new Dna();
		dna.setDna(dnaStruct.getDna());
		WebTarget postTarget = client.target(baseUrl).path("/mutant");
		Response responseObject = postTarget.request(MediaType.TEXT_PLAIN_TYPE).post(Entity.entity(dna, MediaType.APPLICATION_JSON_TYPE),
				Response.class);

		log.info(responseObject.toString());
		if (dnaStruct.isMutant()) {
			incrMapCount(statsMap, STATS_KEYS.MUTATNS);
			Assertions.assertEquals(Status.OK.getStatusCode(), responseObject.getStatus());
		} else {
			incrMapCount(statsMap, STATS_KEYS.HUMANS);
			Assertions.assertEquals(Status.FORBIDDEN.getStatusCode(), responseObject.getStatus());
		}
	}
	
	
	//
	// public void otro() throws Exception {
	// for (int i = 0; i < 5; i++) {
	//
	// final URI baseUrl = new
	// URI("http://52.67.163.113:8080/magnetoCorp/api/");
	//
	// // MainApp.main(new String[0]); // start the app
	// Client client = JerseyClientBuilder.newBuilder().build();
	// client.register(JacksonJsonProvider.class);
	// client.register(JacksonFeature.class);
	// int humansCount = 0;
	// int mutantsCount = 0;
	// log.info("DNA PING TEST");
	//
	// WebTarget target = client.target(baseUrl).path("/mutant/test");
	// Response testResponse = target.request(MediaType.TEXT_PLAIN_TYPE).get();
	// log.info(testResponse.toString());
	// Assertions.assertEquals(200, testResponse.getStatus());
	// Assertions.assertEquals("Activo", testResponse.readEntity(String.class));
	//
	// log.info("Test Post DNA");
	//
	// for (DnaInputTestCaseInput dnaStruct : DnaInputTestCaseInput.getTestMatrices()) {
	//
	// WebTarget postTarget = client.target(baseUrl).path("/mutant");
	// Response responseObject =
	// postTarget.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(dnaStruct.getDna(),
	// MediaType.APPLICATION_JSON_TYPE),
	// Response.class);
	//
	// log.info(responseObject.toString());
	// if (dnaStruct.isMutant()) {
	// mutantsCount++;
	// Assertions.assertEquals(Status.OK.getStatusCode(),
	// responseObject.getStatus());
	// } else {
	// humansCount++;
	// Assertions.assertEquals(Status.FORBIDDEN.getStatusCode(),
	// responseObject.getStatus());
	// }
	//
	// }
	//
	// }
	// }

	/**
	 * TODO: Armar lib con utilidaes
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

		Integer humansCount = statsMap.get(STATS_KEYS.HUMANS);
		Integer mutantsCount = statsMap.get(STATS_KEYS.MUTATNS);

		Stats expectedStats = new Stats(mutantsCount, humansCount, (float) humansCount / (float) mutantsCount);
		Assertions.assertEquals(expectedStats.getCount_human_dna(), stats.getCount_human_dna(), "Humans Stat");
		Assertions.assertEquals(expectedStats.getCount_mutant_dna(), stats.getCount_mutant_dna(), "Mutants Stat");
		Assertions.assertEquals(expectedStats.getRatio(), stats.getRatio(), "Ratio Stat");
	}

}