package ar.edu.grupo5.jm.dss.QueComemos;

public class StCondicionUsuario implements StFiltro {
	public boolean filtrar(Receta unaReceta, Usuario unUsuario) {
		return !unUsuario.sosRecetaInadecuadaParaMi(unaReceta);
	}
}
