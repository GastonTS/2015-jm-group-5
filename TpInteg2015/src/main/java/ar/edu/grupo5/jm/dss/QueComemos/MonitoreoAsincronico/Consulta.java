package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.Collection;
import java.util.stream.Stream;

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

	public String getNombre() {
		return usuario.getNombre();
	}

	public int cantidadConsultadas() {
		return recetasConsultadas.size();
	}
	
	public String getDestinatario() {
		return usuario.getMail();
	}
	
	public String parametrosDeBusquedaToString() {
		Stream<String> nombreParametros = filtro.getNombresFiltros();
		return nombreParametros.reduce("", (texto, nombreParametro) -> texto + "\t-> " + nombreParametro + ".\n");
	}

}
