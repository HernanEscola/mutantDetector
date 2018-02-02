package org.magneto.mutantDetector.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.services.MutantServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j;

@Api(value = "Mutant Service", description = "API para analizar ADNs en busca de Genes Mutantes")
@Path("/mutant")
@Log4j
public class MutantResource {

	@Inject
	public MutantServiceImpl mutantService;
	
//	@GET
//	@Produces(MediaType.TEXT_PLAIN)
//	public Response ping() throws Exception {
//		return Response.ok("Active").build();
//	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Servicio de Analisis de Cadena de ADN", notes = "Recibe un array de Strings representando de una cadena de ADN cuyo largo de string deben ser igual al tamaño del array, formando una especie de matriz cuadrada de caracteres.\nUn input es considerado mutante si hay al menos dos secuencias de caracteres iguales consecutivos en sentido horizontal, vertical u oblicuo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Es una cadena de ADN Mutante"), @ApiResponse(code = 403, message = "Es una cadena de ADN Humano") })
	public Response isMutant(Dna dna) throws Exception {
		log.debug("Analizing DNA");
		if (mutantService.analizeDna(dna)) { 
			return Response.ok("ADN Mutante").build();
		} else {
			return Response.status(Status.FORBIDDEN).entity("ADN Humado").build();
		}

	}
}