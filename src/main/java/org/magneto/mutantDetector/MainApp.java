package org.magneto.mutantDetector;

import java.io.IOException;

import org.magneto.mutantDetector.utils.ServersManager;

/**
 * Inicia la aplicación sin necesidad de deployarla ni tener activo un server de
 * Redis
 * 
 * @author hescola
 *
 */
public class MainApp {

	public static void main(String[] args) throws IOException {
		ServersManager.StartServers(true);

	}

}
