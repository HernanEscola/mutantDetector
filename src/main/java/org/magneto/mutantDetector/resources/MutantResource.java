package org.magneto.mutantDetector.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.magneto.mutantDetector.DTO.Dna;
import org.magneto.mutantDetector.business.enums.EDnaType;
import org.magneto.mutantDetector.exceptions.DBException;
import org.magneto.mutantDetector.exceptions.InvalidDnaException;
import org.magneto.mutantDetector.services.MutantService;

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
	public MutantService mutantService;

	/**
	 * @param dna DNA Analizar
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Servicio de Analisis de Cadena de ADN", notes = "Recibe un array de Strings representando de una cadena de ADN cuyo largo de string deben ser igual al tamaño del array, formando una especie de matriz cuadrada de caracteres.\nUn input es considerado mutante si hay al menos dos secuencias de caracteres iguales consecutivos en sentido horizontal, vertical u oblicuo. "
			+ "Ejemplo mutante: en /json/adn_mutante_example.json  , Ejemplo humano: /json/adn_humano_example.json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Es una cadena de ADN Mutante"), @ApiResponse(code = 403, message = "Es una cadena de ADN Humano"),
			@ApiResponse(code = 400, message = "Es una cadena de ADN INVALIDA") })
	public Response isMutant(Dna dna) throws DBException {
		log.debug("Analizing DNA");
		try {
			EDnaType type = mutantService.analizeDna(dna);
			if (type == EDnaType.MUTANT) {
				return Response.ok("ADN Mutante").build();
			} else {
				return Response.status(Status.FORBIDDEN).entity("ADN Humano").build();
			}
		} catch (InvalidDnaException e) {
			// No sabía que decisión tomar en el caso de que sea invalida la cadena ya que
			// no se encontraba definido. Pensé en devolver forbidden pero con otro mensaje,
			// aunque finalmente no me convenció y terminé eligiendo BAD REQUEST aunque no
			// estoy seguro que sea el propósito de este código.
			return Response.status(Status.BAD_REQUEST).entity("ADN INVALIDO").build();
		}

	}
}