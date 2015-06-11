package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.GestorDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Consulta {

	private ConsultorRecetas consultor;
	private Collection<ObservadorConsultas> observadores = new ArrayList<ObservadorConsultas>();

	public Consulta(ConsultorRecetas unConsultor) {
		consultor = unConsultor;
	}

	public void setConsultor(ConsultorRecetas unConsultor) {
		consultor = unConsultor;
	}

	public void agregarObservador(ObservadorConsultas unObservador) {
		observadores.add(unObservador);
	}

	public Collection<Receta> consultarRecetas(IFiltro unFiltro, Usuario unUsuario) {

		Collection<Receta> recetasConsultadas = unFiltro.filtrarRecetas(consultor.getRecetasAConsultar(unUsuario), unUsuario);

		for (ObservadorConsultas observador : observadores) {
			observador.notificar(unUsuario, recetasConsultadas);
		}

		return recetasConsultadas;
	}

	public Collection<Receta> consultarRecetasSt(GestorDeConsultas unFiltrado, Usuario unUsuario) {
		return unFiltrado.aplicarFiltros(consultor.getRecetasAConsultar(unUsuario), unUsuario);
	}

}
