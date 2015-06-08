package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;

public class SoloPares extends PostProcesamiento {

	public SoloPares(IFiltro unFiltro) {
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
