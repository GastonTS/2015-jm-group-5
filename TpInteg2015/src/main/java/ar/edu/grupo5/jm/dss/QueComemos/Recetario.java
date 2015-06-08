package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Recetario { // FIXME renombrar a recetario

	public static Recetario instancia = new Recetario();

	// FIXME deberia ser de instancia
	private static Collection<Receta> recetasTotales = new ArrayList<Receta>();
	private Collection<ObservadorConsultas> observadores = new ArrayList<ObservadorConsultas>();

	public static void setRecetasTotales(Collection<Receta> unasRecetas) {
		recetasTotales = unasRecetas;
	}

	public void agregarReceta(Receta unaReceta) {
		recetasTotales.add(unaReceta);
	}

	public void quitarReceta(Receta unaReceta) {
		recetasTotales.remove(unaReceta);
	}

	public Collection<Receta> listarTodasPuedeAcceder(Usuario unUsuario) {
		return recetasTotales.stream().filter(receta -> unUsuario.puedeAccederA(receta)).collect(Collectors.toSet());
	}

	public void agregarObservador(ObservadorConsultas unObservador) {
		observadores.add(unObservador);
	}

	public Collection<Receta> consultarRecetas(IFiltro unFiltro, Usuario unUsuario) {

		Collection<Receta> recetasConsultadas = unFiltro.filtrarRecetas(this.listarTodasPuedeAcceder(unUsuario), unUsuario);

		for (ObservadorConsultas observador : observadores) {
			observador.notificar(unUsuario, recetasConsultadas);
		}
		return recetasConsultadas;
	}
}
