package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.Filtrado;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Consulta {

	public Filtrado filtrado;
	public Usuario usuario;
	public Collection<Receta> recetasConsultadas;

	public Consulta(Filtrado unFiltro, Usuario unUsuario, Collection<Receta> unasRecetasConsultadas) {
		filtrado = unFiltro;
		usuario = unUsuario;
		recetasConsultadas = unasRecetasConsultadas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Collection<Receta> getRecetasConsultadas() {
		return recetasConsultadas;
	}

	public Filtrado getFiltro() {
		return filtrado;
	}

	public String getNombre() {
		return usuario.getNombre();
	}

	public int cantidadConsultas() {
		return recetasConsultadas.size();
	}

}
