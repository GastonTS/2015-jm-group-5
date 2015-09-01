package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.Filtro;
import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.BufferDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Consulta {

	private ConsultorRecetas consultor;
	public Filtro filtro;
	public Usuario usuario;
	public Collection<Receta> recetasConsultadas;
	
	static private Collection<ObservadorConsultas> observadores = new ArrayList<ObservadorConsultas>();

	public Consulta(ConsultorRecetas unConsultor, Filtro unFiltro, Usuario unUsuario) {
		consultor = unConsultor;
		filtro = unFiltro;
		usuario = unUsuario;
		
		recetasConsultadas = filtro.filtrarRecetas(consultor.getRecetas(usuario), usuario);
		this.notificarObservadores(observadores, usuario, recetasConsultadas);
		BufferDeConsultas.instancia.agregarConsulta(this);
	}
	
	public static void agregarObservador(ObservadorConsultas unObservador) {
		observadores.add(unObservador);
	}
	
	public static void quitarObservador(ObservadorConsultas unObservador) {
		observadores.remove(unObservador);
	}

	public void notificarObservadores(Collection<ObservadorConsultas> observadores, Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		observadores.forEach(observador -> observador.notificarConsulta(unUsuario, recetasConsultadas));
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
