package ar.edu.grupo5.jm.dss.QueComemos.Receta;

@SuppressWarnings("serial")
public class NoPuedeEliminarRecetaExeption extends RuntimeException {

	public NoPuedeEliminarRecetaExeption(String message) {
		super(message);
	}
}
