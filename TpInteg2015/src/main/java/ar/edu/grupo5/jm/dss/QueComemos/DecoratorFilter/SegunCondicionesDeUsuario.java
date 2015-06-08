package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.function.Predicate;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class SegunCondicionesDeUsuario extends PreProcesamiento {

	public SegunCondicionesDeUsuario(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected Predicate<Receta> setCriterio() {
		return (unaReceta -> !usuario.sosRecetaInadecuadaParaMi(unaReceta));
	}
}
