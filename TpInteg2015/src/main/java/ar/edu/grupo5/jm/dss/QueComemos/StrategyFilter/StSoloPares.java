package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class StSoloPares implements StPostProcesamiento {

	@Override
	public Collection<Receta> procesarRecetas(Collection<Receta> unasRecetas) {
		boolean esPar = false;
		Collection<Receta> recetasTotales = new ArrayList<Receta>();
		for (Receta receta : unasRecetas) {
			if (esPar) {
				recetasTotales.add(receta);
			}
			esPar = !esPar;
		}
		return recetasTotales;
	}

}
