package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Consulta {
	
	public IFiltro unFiltro;
	public Usuario usuario;
	public Collection<Receta> recetasConsultadas;

}
