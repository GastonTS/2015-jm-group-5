package ar.edu.grupo5.jm.dss.QueComemos.Receta;

@SuppressWarnings("serial")
public class NoPuedeAccederARecetaException extends RuntimeException {

	public NoPuedeAccederARecetaException(String message) {
		super(message);
	}
}
