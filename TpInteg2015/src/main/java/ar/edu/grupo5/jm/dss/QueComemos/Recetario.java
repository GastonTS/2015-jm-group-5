package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeAccederARecetaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeEliminarRecetaExeption;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.GestorDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Recetario {

	public static Recetario instancia = new Recetario();
	private Collection<Receta> recetasTotales = new ArrayList<Receta>();
	private Collection<ObservadorConsultas> observadores = new ArrayList<ObservadorConsultas>();

	public void setRecetasTotales(Collection<Receta> unasRecetas) {
		recetasTotales = unasRecetas;
	}

	public Collection<Receta> getRecetasTotales() {
		return recetasTotales;
	}

	public void quitarReceta(Receta unaReceta) {
		recetasTotales.remove(unaReceta);
	}

	public Collection<Receta> listarTodasPuedeAcceder(Usuario unUsuario) {
		return recetasTotales.stream().filter(receta -> unUsuario.puedeAccederA(receta)).collect(Collectors.toSet());
	}

	public void crearReceta(Receta unaReceta, Usuario unUsuario) {
		if (!unaReceta.esValida()) {
			throw new RecetaNoValidaException("No se Puede agregar una receta no válida!!!");
		}

		unaReceta.setDueño(unUsuario);
		recetasTotales.add(unaReceta);
	}

	public void crearRecetaConSubRecetas(Receta unaReceta, Collection<Receta> unasSubRecetas, Usuario unUsuario) {
		if (unasSubRecetas.stream().anyMatch(unaSubReceta -> !unUsuario.puedeAccederA(unaSubReceta))) {
			throw new NoPuedeAccederARecetaException("No puede agregar subrecetas a las que no tenga permiso de acceder");
		}
		unaReceta.agregarSubRecetas(unasSubRecetas);
		crearReceta(unaReceta, unUsuario);
	}

	public void eliminarReceta(Receta unaReceta, Usuario unUsuario) {
		if (!unaReceta.esElDueño(unUsuario)) {
			throw new NoPuedeEliminarRecetaExeption("No puede eliminar una receta que no creó");

		}
		recetasTotales.remove(unaReceta);
		unUsuario.quitarRecetaFavorita(unaReceta);
	}

	public void modificarReceta(Receta viejaReceta, Receta nuevaReceta, Usuario unUsuario) {
		if (!unUsuario.puedeAccederA(viejaReceta)) {
			throw new NoPuedeAccederARecetaException("No tiene permiso para acceder a esa receta");
		}

		if (viejaReceta.esElDueño(unUsuario)) {
			eliminarReceta(viejaReceta, unUsuario);
		}
		crearReceta(nuevaReceta, unUsuario);
	}

	public void agregarObservador(ObservadorConsultas unObservador) {
		observadores.add(unObservador);
	}

	public Collection<Receta> consultarRecetas(IFiltro unFiltro, Usuario unUsuario) {

		Collection<Receta> recetasConsultadas = unFiltro.filtrarRecetas(listarTodasPuedeAcceder(unUsuario), unUsuario);

		for (ObservadorConsultas observador : observadores) {
			observador.notificar(unUsuario, recetasConsultadas);
		}

		return recetasConsultadas;
	}

	public Collection<Receta> consultarRecetasSt(GestorDeConsultas unFiltrado, Usuario unUsuario) {
		return unFiltrado.aplicarFiltros(listarTodasPuedeAcceder(unUsuario), unUsuario);
	}

}
