package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class SoloPares extends PostProcesamiento {

	public SoloPares(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		boolean esPar = false;
		Collection<Receta> recetasTotales = new ArrayList<Receta>();
		for (Receta receta : recetas) {
			if (esPar) {
				recetasTotales.add(receta);
			}
			esPar = !esPar;
		}
		return recetasTotales;
	}
}
