package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Collection;
import java.util.List;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public abstract class PostProcesamiento implements Filtro {

	protected Filtro subFiltro;

	public PostProcesamiento(Filtro unFiltro) {
		subFiltro = unFiltro;
	}

	protected abstract Collection<Receta> procesar(Collection<Receta> recetas);

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		List<Receta> recetasParciales = (List<Receta>) subFiltro.filtrarRecetas(recetas, unUsuario);

		return procesar(recetasParciales);
	}
}
