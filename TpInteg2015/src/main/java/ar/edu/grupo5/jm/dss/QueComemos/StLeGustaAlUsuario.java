package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class StLeGustaAlUsuario implements IFiltroStrategy {

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> unasRecetas,
			Usuario unUsuario) {
		return unasRecetas.stream()
				.filter((unaReceta -> unUsuario.noLeDisgusta(unaReceta)))
				.collect(Collectors.toList());
	}

}
