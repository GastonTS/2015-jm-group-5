package ar.edu.grupo5.jm.dss.QueComemos;

public class NoPuedeEliminarRecetaExeption extends RuntimeException {

	public NoPuedeEliminarRecetaExeption(String message, Throwable cause) {
		super(message, cause);
	}
}
