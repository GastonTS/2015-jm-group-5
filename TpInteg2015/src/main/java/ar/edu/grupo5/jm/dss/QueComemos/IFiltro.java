package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public interface IFiltro {

	public abstract Collection<Receta> filtrarRecetas(
			Collection<Receta> recetas, Usuario unUsuario);

}
