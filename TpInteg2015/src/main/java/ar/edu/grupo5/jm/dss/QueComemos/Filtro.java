package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public abstract class Filtro implements IFiltro {

	protected IFiltro subFiltro;
	protected Usuario usuarioAConsiderar;

	public Filtro(IFiltro unFiltro) {
		subFiltro = unFiltro;
	}

	public Filtro(IFiltro unFiltro, Usuario unUsuario) {
		subFiltro = unFiltro;
		usuarioAConsiderar = unUsuario;
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas) {
		return subFiltro.filtrarRecetas(recetas);
	}
}
