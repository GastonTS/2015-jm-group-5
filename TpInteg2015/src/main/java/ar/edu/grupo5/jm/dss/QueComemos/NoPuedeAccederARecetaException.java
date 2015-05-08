package ar.edu.grupo5.jm.dss.QueComemos;

@SuppressWarnings("serial")
public class NoPuedeAccederARecetaException extends RuntimeException {

	public NoPuedeAccederARecetaException(String message, Throwable cause) {
		super(message, cause);
	}
}
