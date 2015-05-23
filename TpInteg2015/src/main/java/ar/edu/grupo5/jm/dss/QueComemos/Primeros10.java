package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class Primeros10 extends Filtro {

	public Primeros10(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas) {
		Collection<Receta> recetasParciales = subFiltro.filtrarRecetas(recetas);

		return recetasParciales.stream().limit(10).collect(Collectors.toList());
	}

}