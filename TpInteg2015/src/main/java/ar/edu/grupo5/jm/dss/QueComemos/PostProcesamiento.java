package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.List;

public abstract class PostProcesamiento implements IFiltro {

	protected IFiltro subFiltro;

	public PostProcesamiento(IFiltro unFiltro) {
		subFiltro = unFiltro;
	}

	protected abstract Collection<Receta> procesar(Collection<Receta> recetas);

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		List<Receta> recetasParciales = (List<Receta>) subFiltro.filtrarRecetas(recetas, unUsuario);

		return procesar(recetasParciales);
	}
}
