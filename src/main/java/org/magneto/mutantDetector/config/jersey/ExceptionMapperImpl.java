package org.magneto.mutantDetector.config.jersey;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.magneto.mutantDetector.DTO.ResponseDTO;

import lombok.extern.log4j.Log4j;

@Provider
@Log4j
public class ExceptionMapperImpl implements ExceptionMapper<Exception> {
	@Override
	/**
	 * TODO: Falta un DTO para retornar serializado los errores 
	 */
	public Response toResponse(Exception ex) {
		log.error("Request Error",ex); 
		//POr ahora retorno el mensaje a modo de debug
		return Response.status(500).entity(new ResponseDTO(ex)).build();
	}

}