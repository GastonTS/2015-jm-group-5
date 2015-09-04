package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class NoLeDisgustaAlUsuario extends PreFiltro {

	public NoLeDisgustaAlUsuario(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario) {
		return unUsuario.noLeDisgusta(unaReceta);
	}
	
	@Override
	public String getNombre() {
		return "No me disgusta";
	}
}