package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public interface ConsultorRecetas {

	public abstract Collection<Receta> getRecetas(Usuario unUsuario);
	
}
