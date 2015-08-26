package ar.edu.grupo5.jm.dss.QueComemos.RecetasExternas;

@SuppressWarnings("serial")
public class DificultadDeRepoExternoNoValidaException extends IllegalArgumentException {

	public DificultadDeRepoExternoNoValidaException(String dificultadString) {
		super("Dificultad Invalida: " + dificultadString);
	}
}
