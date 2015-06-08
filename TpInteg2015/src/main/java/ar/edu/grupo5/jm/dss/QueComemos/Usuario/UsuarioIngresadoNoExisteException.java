package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

@SuppressWarnings("serial")
public class UsuarioIngresadoNoExisteException extends RuntimeException {

	public UsuarioIngresadoNoExisteException(String message) {
		super(message);
	}
}
