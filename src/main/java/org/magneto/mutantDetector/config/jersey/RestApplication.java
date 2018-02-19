package org.magneto.mutantDetector.config.jersey;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;
import lombok.extern.log4j.Log4j;

@Log4j
@SwaggerDefinition(basePath = "api", info = @Info(description = "API para la deteccion de genes Mutantes en humanos", version = "1.0.2", title = "Magneto - Mutant Detector ", contact = @Contact(name = "Hernan Adriel Escola", email = "hernan.a.escola@gmail.com")), consumes = {
		"application/json" }, produces = { "application/json" }, schemes = {
				SwaggerDefinition.Scheme.HTTP }, externalDocs = @ExternalDocs(value = "Documentacion completa de la API ", url = "../javadocs/index.html"))
@ApplicationPath("api")
public class RestApplication extends ResourceConfig {
	public static final String RESOURCE_PACKAGE = "org.magneto.mutantDetector";

	public RestApplication(@Context ServletContext servletContext) {
		initResourceConfig(this);
		BeanConfig beanConfig = config();
		/**
		 * Deberñia levantar el contexto desde la configuracion de la app
		 */
		String basePath = "";
		if (servletContext != null) {
			basePath += servletContext.getContextPath();
		} else {
			log.error("No hay contexto para extraer el path de configuración de swagger");
		}
		basePath += "/api/";
		log.info("Se registró el basePath de swagger con el siguiente valor: " + basePath);
		beanConfig.setBasePath(basePath);

	}

	public ResourceConfig initResourceConfig(ResourceConfig rc) {
		rc.register(io.swagger.jaxrs.listing.ApiListingResource.class);
		rc.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		rc.register(JacksonJsonProvider.class);
		rc.packages(RESOURCE_PACKAGE);

		return rc;
	}

	public static BeanConfig config() {

		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.2");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("");
		beanConfig.setResourcePackage(RESOURCE_PACKAGE);
		beanConfig.setScan(true);
		return beanConfig;

	}
}