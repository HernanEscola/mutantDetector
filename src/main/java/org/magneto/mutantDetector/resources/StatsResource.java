package org.magneto.mutantDetector.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.magneto.mutantDetector.DTO.Stats;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.services.StatsServiceImpl;

import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j;

@Api(value = "Stats Service", description = "API De consulta de Stats sobre los anala'isis realizado")
@Path("/stats")
@Log4j
public class StatsResource {

	@Inject
	public StatsServiceImpl statsService;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Stats getStats() throws DBException {
		Stats stats = statsService.getStats();
		return stats;
	}

}