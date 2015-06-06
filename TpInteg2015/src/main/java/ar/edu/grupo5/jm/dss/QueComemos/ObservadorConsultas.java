package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public interface ObservadorConsultas {
	
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas);

}
