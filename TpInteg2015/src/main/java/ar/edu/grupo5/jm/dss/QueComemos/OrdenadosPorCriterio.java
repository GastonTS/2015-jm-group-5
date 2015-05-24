package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdenadosPorCriterio extends Filtro {

	private Comparator<Receta> criterio;

	public OrdenadosPorCriterio(IFiltro unFiltro,
			Comparator<Receta> unComparador) {
		super(unFiltro);
		criterio = unComparador;
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas,
			Usuario unUsuario) {
		List<Receta> recetasParciales = (List<Receta>) subFiltro
				.filtrarRecetas(recetas, unUsuario);

		Collections.sort(recetasParciales, criterio);

		return recetasParciales;
	}

}