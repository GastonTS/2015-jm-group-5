package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class StExcesoDeCalorias implements IFiltroStrategy {

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> unasRecetas,
			Usuario unUsuario) {

		return unasRecetas.stream()
				.filter((unaReceta -> unaReceta.getCantCalorias() < 500))
				.collect(Collectors.toList());
	}

}
