package org.magneto.mutantDetector.utils;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.magneto.mutantDetector.MainApp;
import org.magneto.mutantDetector.config.jersey.RestApplication;

import io.swagger.jaxrs.config.BeanConfig;
import lombok.extern.log4j.Log4j;
import redis.clients.jedis.Jedis;
import redis.embedded.RedisServer;

@Log4j

/**
 * 
 * TODO: refactorizar como Singleton
 * 
 * @author hescola
 *
 */
public class ServersManager {
	public static RedisServer redisServer;
	public static HttpServer httpServer;

	public final static String CONTEXT_PATH = "";// "/magnetoCorp";
	public final static String BASE = "http://0.0.0.0:8082" + CONTEXT_PATH;
	public final static URI ADDRESS = UriBuilder.fromPath(BASE + "/api").build();

	/**
	 * Si hay un server corriendo, lo para y crea una nueva instancia limpia
	 * 
	 * @return
	 * @throws IOException
	 */
	public static RedisServer startCleanRedisInstance() throws IOException {
		try {

			if (redisServer == null) {
				redisServer = new RedisServer();

			} else {
				redisServer.stop();

			}

			redisServer.start();

		} catch (Exception e) {
			shutdownRedisServer();
			redisServer = new RedisServer();
			redisServer.start();
		}
		return redisServer;
	}

	/**
	 * 
	 * @return La instancia del server actual. Puede ser null si no se
	 *         inicializó ninguna
	 * @throws IOException
	 */
	public static RedisServer getRedisServerCurrentInstance() throws IOException {
		return redisServer;
	}

	/**
	 * Retorna una instancia activa del server. Si no existe lo crea. Si existía
	 * y está parado lo activa.
	 * 
	 * @return
	 * @throws IOException
	 * 
	 */
	public static RedisServer getRedisStarted() throws IOException {
		if (redisServer == null) {
			redisServer = startCleanRedisInstance();
		} else if (!redisServer.isActive()) {
			redisServer.start();
		}
		return redisServer;
	}
	
	/**
	 * Arranca el server de Redis. Lo utilizo para probar en local con el tomcat ya que no tengo una instancia de redis corriendo
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ServersManager.getRedisStarted();
		log.info("Redis Activo");

	}
	public static RedisServer stopRedisCurrentInstance() throws IOException {
		redisServer.stop();
		return redisServer;
	}

	public static void shutdownRedisServer() {
		Jedis jedi = new Jedis();
		jedi.shutdown();
		log.debug("Apagando server redis");
		jedi.close();
		redisServer = null;
	}

	public static void shutdownHttpServer() {
		httpServer.shutdown();
		httpServer = null;
	}

	/**
	 * Solo instancia el HttpServer
	 * 
	 * @throws IOException
	 * 
	 */
	public static HttpServer getHttpServerInstance() throws IOException {

		if (httpServer == null) {
			httpServer = GrizzlyHttpServerFactory.createHttpServer(ADDRESS, getResourceConfig());

			ClassLoader loader = MainApp.class.getClassLoader();
			CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "/static/");
			docsHandler.setFileCacheEnabled(false);

			ServerConfiguration cfg = httpServer.getServerConfiguration();
			cfg.addHttpHandler(docsHandler, CONTEXT_PATH + "/");
			httpServer.start();
			log.info(("API - Documentación Online : " + BASE + "/docs/").replaceAll("0.0.0.0", "localhost"));
		}

		return httpServer;

	}

	public static ResourceConfig getResourceConfig() {
		final ResourceConfig resourceConfig = new RestApplication(null);

		BeanConfig beanConfig = RestApplication.getConfig();
		//beanConfig.setBasePath(CONTEXT_PATH + "api/");
		return resourceConfig;

	}

	/**
	 * Inicia el servidor de Grizzly y el server de redis
	 * 
	 * @param waitForInput
	 * @return Instancia del server creado
	 */
	public static HttpServer StartServers(boolean waitForInput) {
		try {
			HttpServer s = getHttpServerInstance();

			redisServer = startCleanRedisInstance();

			if (waitForInput) {
				log.info(String.format("Mutant Detector Iniciado.\nPresione una tecla para finalizar...", ADDRESS));
				System.in.read();
				shutdownHttpServer();
				shutdownRedisServer();
			}
			return s;
		} catch (Exception e) {
			throw new RuntimeException("No se pudieron iniciar los server", e); // TODO:
																				// Implementar
																				// excepcion
		}
	}

}
