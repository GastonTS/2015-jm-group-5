package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public interface ConsultorRecetas {

	public abstract Collection<Receta> getRecetasAConsultar(Usuario unUsuario);

}
