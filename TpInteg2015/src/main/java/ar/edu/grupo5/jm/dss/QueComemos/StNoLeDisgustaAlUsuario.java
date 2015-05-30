package ar.edu.grupo5.jm.dss.QueComemos;

public class StNoLeDisgustaAlUsuario implements StFiltro {
	public boolean filtrar(Receta unaReceta, Usuario unUsuario) {
		return unUsuario.noLeDisgusta(unaReceta);
	}
}