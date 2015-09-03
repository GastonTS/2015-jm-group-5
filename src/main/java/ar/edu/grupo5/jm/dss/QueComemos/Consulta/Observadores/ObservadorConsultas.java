package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public interface ObservadorConsultas {

	public abstract void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas);

}
