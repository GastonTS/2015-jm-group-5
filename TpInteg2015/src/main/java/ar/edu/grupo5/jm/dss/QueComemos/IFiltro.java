package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public interface IFiltro {

//	public abstract boolean filtrarRecetas(
	public abstract Collection<Receta> filtrarRecetas(
			Collection<Receta> recetas, Usuario unUsuario);
//FIXME en vez de tener filtros que todos hagan filter tener condiciones para filtrar una receta
}
