package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class SinFiltro implements IFiltro {

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		return recetas;
	}

}
