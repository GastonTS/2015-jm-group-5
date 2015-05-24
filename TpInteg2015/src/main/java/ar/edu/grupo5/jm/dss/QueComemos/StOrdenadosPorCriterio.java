package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StOrdenadosPorCriterio implements IFiltroStrategy {

	private Comparator<Receta> criterio;

	public StOrdenadosPorCriterio(Comparator<Receta> unComparador) {
		criterio = unComparador;
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> unasRecetas,
			Usuario unUsuario) {
		List<Receta> resultadoRecetas = (List<Receta>) unasRecetas;

		Collections.sort(resultadoRecetas, criterio);

		return resultadoRecetas;
	}

}
