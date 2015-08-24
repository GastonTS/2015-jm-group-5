package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.Filtro;
import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.BufferDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StGestorDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class GestorDeConsultas {

	private ConsultorRecetas consultor;
	private Collection<ObservadorConsultas> observadores = new ArrayList<ObservadorConsultas>();

	public GestorDeConsultas(ConsultorRecetas unConsultor) {
		consultor = unConsultor;
	}

	public void setConsultor(ConsultorRecetas unConsultor) {
		consultor = unConsultor;
	}

	public void agregarObservador(ObservadorConsultas unObservador) {
		observadores.add(unObservador);
	}

	public Collection<Receta> consultarRecetas(Filtro unFiltro, Usuario unUsuario) {
		Collection<Receta> recetasConsultadas = unFiltro.filtrarRecetas(consultor.getRecetas(unUsuario), unUsuario);
		this.notificarObservadores(observadores, unUsuario, recetasConsultadas);
		Consulta consulta = new Consulta(unFiltro, unUsuario, recetasConsultadas);
		BufferDeConsultas.instancia.agregarConsulta(consulta);
		return recetasConsultadas;
	}
	
	public void notificarObservadores(Collection<ObservadorConsultas> observadores, Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		for (ObservadorConsultas observador : observadores) {
			observador.notificar(unUsuario, recetasConsultadas);
		}
	}

	public Collection<Receta> consultarRecetasSt(StGestorDeConsultas unFiltrado, Usuario unUsuario) {
		return unFiltrado.aplicarFiltros(consultor.getRecetas(unUsuario), unUsuario);
	}

}
