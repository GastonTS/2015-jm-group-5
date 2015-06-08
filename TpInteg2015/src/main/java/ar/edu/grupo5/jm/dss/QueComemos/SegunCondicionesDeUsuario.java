package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.function.Predicate;

public class SegunCondicionesDeUsuario extends PreProcesamiento {

	public SegunCondicionesDeUsuario(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected Predicate<Receta> setCriterio() {
		return (unaReceta -> !usuario.sosRecetaInadecuadaParaMi(unaReceta));
	}
}
