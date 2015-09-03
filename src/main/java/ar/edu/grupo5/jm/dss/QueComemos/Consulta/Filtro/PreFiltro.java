package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public abstract class PreFiltro implements Filtro {

	protected Filtro subFiltro;
	protected Predicate<Receta> criterio;

	public PreFiltro(Filtro unFiltro) {
		subFiltro = unFiltro;
	}

	protected abstract boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario);

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		Collection<Receta> recetasFiltradas = recetas.stream().filter(receta -> this.cumpleCriterio(receta, unUsuario)).collect(Collectors.toList());
		return subFiltro.filtrarRecetas(recetasFiltradas, unUsuario);
	}

	public abstract String getNombre();
	
	@Override
	public Stream<String> getNombresFiltros() {
		return Stream.concat(Stream.of(this.getNombre()), subFiltro.getNombresFiltros());
	}
}
