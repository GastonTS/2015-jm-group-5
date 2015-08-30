package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.Filtro;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Consulta {

	public Filtro filtro;
	public Usuario usuario;
	public Collection<Receta> recetasConsultadas;

	public Consulta(Filtro unFiltro, Usuario unUsuario, Collection<Receta> unasRecetasConsultadas) {
		filtro = unFiltro;
		usuario = unUsuario;
		recetasConsultadas = unasRecetasConsultadas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Collection<Receta> getRecetasConsultadas() {
		return recetasConsultadas;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public String getNombre() {
		return usuario.getNombre();
	}

	public int cantidadConsultas() {
		return recetasConsultadas.size();
	}

}
