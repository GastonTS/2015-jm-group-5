package ar.edu.grupo5.jm.dss.QueComemos.RecetasExternas;

public class DificultadDeRepoExternoNoValidaException extends IllegalArgumentException {

	public DificultadDeRepoExternoNoValidaException(String dificultadString) {
		super("Dificultad Invalida: " + dificultadString);
	}
}
