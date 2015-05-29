package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;

public class StSoloPares implements PostProcesamiento {

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
