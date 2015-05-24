package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public abstract class Filtro implements IFiltro {

	protected IFiltro subFiltro;

	public Filtro(IFiltro unFiltro) {
		subFiltro = unFiltro;
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas,
			Usuario unUsuario) {
		return subFiltro.filtrarRecetas(recetas, unUsuario);
	}
}
