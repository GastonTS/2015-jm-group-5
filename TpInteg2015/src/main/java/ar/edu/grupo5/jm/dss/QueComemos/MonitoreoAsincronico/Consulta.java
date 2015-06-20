package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Consulta {

	public IFiltro filtro;
	public Usuario usuario;
	public Collection<Receta> recetasConsultadas;

	public Consulta(IFiltro unFiltro, Usuario unUsuario, Collection<Receta> unasRecetasConsultadas) {
		filtro = unFiltro;
		usuario = unUsuario;
		recetasConsultadas = unasRecetasConsultadas;
	}
	
	public boolean tieneMasDe100(){
		return cantidadConsultas()> 100;
	}
	
	public String getNombre(){
		return usuario.getNombre();
	}
	
	public int cantidadConsultas(){
		return recetasConsultadas.size();
	}
	
}
