package org.magneto.mutantDetector.exceptions;

/**
 * Implementación de Excepción para identificar errores de la capa DAO o
 * inferior
 * 
 * @author hescola
 *
 */
public class DBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3764464631268438421L;

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}

}
