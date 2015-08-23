package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public abstract class PreProcesamiento implements Filtro {

	protected Filtro subFiltro;
	protected Predicate<Receta> criterio;

	public PreProcesamiento(Filtro unFiltro) {
		subFiltro = unFiltro;
	}

	protected abstract boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario);

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		Collection<Receta> recetasFiltradas = recetas.stream().filter(receta -> this.cumpleCriterio(receta, unUsuario)).collect(Collectors.toList());
		return subFiltro.filtrarRecetas(recetasFiltradas, unUsuario);
	}
}
