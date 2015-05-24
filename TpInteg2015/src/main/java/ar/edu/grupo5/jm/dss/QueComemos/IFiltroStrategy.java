package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public interface IFiltroStrategy {

	public Collection<Receta> filtrarRecetas(Collection<Receta> unasRecetas, Usuario unUsuario);
	
}
