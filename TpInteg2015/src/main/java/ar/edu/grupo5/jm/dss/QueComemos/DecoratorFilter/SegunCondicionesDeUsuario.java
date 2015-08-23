package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class SegunCondicionesDeUsuario extends PreProcesamiento {

	public SegunCondicionesDeUsuario(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario) {
		return !unUsuario.sosRecetaInadecuadaParaMi(unaReceta);
	}
}
