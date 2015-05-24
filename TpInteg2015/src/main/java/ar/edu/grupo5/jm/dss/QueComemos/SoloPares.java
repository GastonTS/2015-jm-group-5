package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;

public class SoloPares extends Filtro {

	public SoloPares(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		Collection<Receta> recetasParciales = subFiltro.filtrarRecetas(recetas, unUsuario);

		boolean esPar = false;
		Collection<Receta> recetasTotales = new ArrayList<Receta>();
		for (Receta receta : recetasParciales) {
			if (esPar) {
				recetasTotales.add(receta);
			}
			esPar = !esPar;
		}
		return recetasTotales;

	}

}
