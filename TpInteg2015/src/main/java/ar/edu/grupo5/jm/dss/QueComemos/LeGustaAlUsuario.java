package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class LeGustaAlUsuario extends Filtro {

	public LeGustaAlUsuario(IFiltro unFiltro, Usuario unUsuario) {
		super(unFiltro, unUsuario);
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas) {
		Collection<Receta> recetasParciales = recetas.stream()
				.filter((unaReceta -> usuarioAConsiderar.teGusta(unaReceta)))
				.collect(Collectors.toList());

		return subFiltro.filtrarRecetas(recetasParciales);
	}

}