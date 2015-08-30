package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class OrdenadosPorCriterio implements PostProcesamiento {

	private Comparator<Receta> criterio;

	public OrdenadosPorCriterio(Comparator<Receta> unComparador) {
		criterio = unComparador;
	}

	@Override
	public Collection<Receta> procesarRecetas(Collection<Receta> unasRecetas) {
		List<Receta> resultadoRecetas = (List<Receta>) unasRecetas;

		Collections.sort(resultadoRecetas, criterio);

		return resultadoRecetas;
	}

}
