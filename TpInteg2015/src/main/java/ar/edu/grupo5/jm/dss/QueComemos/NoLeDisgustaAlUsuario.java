package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.function.Predicate;

public class NoLeDisgustaAlUsuario extends PreProcesamiento {

	public NoLeDisgustaAlUsuario(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected Predicate<Receta> setCriterio() {
		return (unaReceta -> usuario.noLeDisgusta(unaReceta));
	}
}