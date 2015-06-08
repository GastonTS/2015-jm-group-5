package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdenadosPorCriterio extends PostProcesamiento {

	private Comparator<Receta> criterio;

	public OrdenadosPorCriterio(IFiltro unFiltro, Comparator<Receta> unComparador) {
		super(unFiltro);
		criterio = unComparador;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		Collections.sort((List<Receta>) recetas, criterio);
		return recetas;
	}

}