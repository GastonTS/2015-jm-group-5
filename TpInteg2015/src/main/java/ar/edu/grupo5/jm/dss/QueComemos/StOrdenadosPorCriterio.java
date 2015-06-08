package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StOrdenadosPorCriterio implements StPostProcesamiento {

	private Comparator<Receta> criterio;

	public StOrdenadosPorCriterio(Comparator<Receta> unComparador) {
		criterio = unComparador;
	}

	@Override
	public Collection<Receta> procesarRecetas(Collection<Receta> unasRecetas) {
		List<Receta> resultadoRecetas = (List<Receta>) unasRecetas;

		Collections.sort(resultadoRecetas, criterio);

		return resultadoRecetas;
	}

}
