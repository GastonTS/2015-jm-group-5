package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class PreProcesamiento implements IFiltro {

	protected IFiltro subFiltro;
	protected Predicate<Receta> criterio;

	protected Usuario usuario;

	public PreProcesamiento(IFiltro unFiltro) {
		subFiltro = unFiltro;
	}

	protected abstract Predicate<Receta> setCriterio();

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		usuario = unUsuario;
		criterio = setCriterio();
		Collection<Receta> recetasFiltradas = recetas.stream().filter(criterio).collect(Collectors.toList());
		return subFiltro.filtrarRecetas(recetasFiltradas, unUsuario);
	}
}
