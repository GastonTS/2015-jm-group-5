package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class OrdenadosPorCriterio extends PostProcesamiento {

	private Comparator<Receta> criterio;
	private String nombreCriterio;

	public OrdenadosPorCriterio(Filtro unFiltro, Comparator<Receta> unComparador, String unNombreCriterio) {
		super(unFiltro);
		criterio = unComparador;
		nombreCriterio = unNombreCriterio;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		Collections.sort((List<Receta>) recetas, criterio);
		return recetas;
	}
	
	@Override
	public String getNombre() {
		return "Ordenadas por " + nombreCriterio + ".";
	}

}