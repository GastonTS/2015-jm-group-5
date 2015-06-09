package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public interface ObservadorConsultas {

	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas);

}
