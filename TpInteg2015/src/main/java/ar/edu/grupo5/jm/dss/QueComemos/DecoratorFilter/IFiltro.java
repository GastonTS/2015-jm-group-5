package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public interface IFiltro {
	public abstract Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario);
}
