package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class OrdenadosPorCriterio extends PostProcesamiento {

	private Comparator<Receta> criterio;

	public OrdenadosPorCriterio(Filtro unFiltro, Comparator<Receta> unComparador) {
		super(unFiltro);
		criterio = unComparador;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		Collections.sort((List<Receta>) recetas, criterio);
		return recetas;
	}

}