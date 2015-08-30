package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.BufferDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.Filtrado;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class GestorDeConsultas {

	private ConsultorRecetas consultor;
	static private Collection<ObservadorConsultas> observadores = new ArrayList<ObservadorConsultas>();

	public GestorDeConsultas(ConsultorRecetas unConsultor) {
		consultor = unConsultor;
	}

	public void setConsultor(ConsultorRecetas unConsultor) {
		consultor = unConsultor;
	}

	public static void agregarObservador(ObservadorConsultas unObservador) {
		observadores.add(unObservador);
	}

	public void notificarObservadores(Collection<ObservadorConsultas> observadores, Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		observadores.forEach(observador -> observador.notificarConsulta(unUsuario, recetasConsultadas));
	}

	public Collection<Receta> consultarRecetasSt(Filtrado unFiltrado, Usuario unUsuario) {
		Collection<Receta> recetasConsultadas = unFiltrado.aplicarFiltros(consultor.getRecetas(unUsuario), unUsuario);
		this.notificarObservadores(observadores, unUsuario, recetasConsultadas);
		Consulta consulta = new Consulta(unFiltrado, unUsuario, recetasConsultadas);
		BufferDeConsultas.instancia.agregarConsulta(consulta);
		return recetasConsultadas;
	}

}
