package ar.edu.grupo5.jm.dss.QueComemos;

@SuppressWarnings("serial")
public class NoPuedeEliminarRecetaExeption extends RuntimeException {

	public NoPuedeEliminarRecetaExeption(String message) {
		super(message);
	}
}
