package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Grupo;
import ar.edu.grupo5.jm.dss.QueComemos.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeAccederARecetaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeEliminarRecetaExeption;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.GestorDeConsultas;

public class Usuario {

	static private Recetario repositorio = new Recetario();

	private Complexion complexion;
	private DatosPersonales datosPersonales;
	private Collection<String> preferenciasAlimenticias;
	private Collection<String> disgustosAlimenticios;
	private Collection<CondicionDeSalud> condicionesDeSalud;
	private Collection<Grupo> grupos;

	public enum Rutina {
		LEVE, NADA, MEDIANA, INTENSIVA, ALTA
	}

	private Rutina rutina;
	private Collection<Receta> recetasFavoritas;

	public Collection<Receta> getRecetasFavoritas() {
		return recetasFavoritas;
	}

	public Usuario(DatosPersonales unosDatosPersonales, Complexion unaComplexion, Collection<String> unasPreferenciasAlimenticias,
			Collection<String> unosDisgustosAlimenticios, Collection<CondicionDeSalud> unasCondicionesDeSalud, Rutina unaRutina) {

		datosPersonales = unosDatosPersonales;
		complexion = unaComplexion;
		grupos = new ArrayList<Grupo>();
		recetasFavoritas = new ArrayList<Receta>();

		// FIXME asumir no nulos
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		disgustosAlimenticios = unosDisgustosAlimenticios;
		condicionesDeSalud = unasCondicionesDeSalud;
		rutina = unaRutina;
	}

	static public void setRepositorio(Recetario unRepositorio) {
		repositorio = unRepositorio;
	}

	public void agregarGrupo(Grupo unGrupo) {
		grupos.add(unGrupo);
		unGrupo.añadirIntegrante(this);
		// FIXME agregar integrante en grupo
	}

	public double getPeso() {
		return complexion.getPeso();
	}

	public boolean indicaSexo() {
		return datosPersonales.indicaSexo();
	}

	// Punto 1
	// XXX esto podria no ser facil de extender
	public boolean esUsuarioValido() {
		return datosPersonales.sonValidos() && complexion.esComplexionValida()
		// FIXME ser consistentes en el uso de null u optional
				&& rutina != null && esUsuarioValidoParaSusCondiciones();
	}

	public boolean esUsuarioValidoParaSusCondiciones() {
		return condicionesDeSalud.stream().allMatch(condicion -> condicion.esUsuarioValido(this));
	}

	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
	}

	// Punto 2.b
	public boolean sigueRutinaSaludable() {
		return 18 <= complexion.indiceMasaCorporal() && complexion.indiceMasaCorporal() <= 30 && this.subsanaTodasLasCondiciones();
	}

	private boolean subsanaTodasLasCondiciones() {
		return condicionesDeSalud.stream().allMatch(condicion -> condicion.subsanaCondicion(this));
	}

	public boolean tieneRutinaActiva() {
		return this.tieneRutinaIntensiva() || rutina == Rutina.ALTA;
	}

	public boolean tieneRutinaIntensiva() {
		return rutina == Rutina.INTENSIVA;
	}

	public boolean tienePreferencia(String preferencia) {
		return preferenciasAlimenticias.contains(preferencia);
	}

	public boolean tieneAlgunaDeEstasPreferencias(Collection<String> preferencias) {
		return preferenciasAlimenticias.stream().anyMatch(preferencia -> preferencias.contains(preferencia));
	}

	// Punto 3.b en receta
	public boolean sosRecetaInadecuadaParaMi(Receta unaReceta) {
		return condicionesDeSalud.stream().anyMatch(condicion -> condicion.esInadecuada(unaReceta));
	}

	public boolean esRecetaDeGrupo(Receta unaReceta) {
		return grupos.stream().anyMatch(unGrupo -> unGrupo.alguienTiene(unaReceta));
	}

	public boolean puedeSugerirse(Receta unaReceta) {
		return noLeDisgusta(unaReceta) && !sosRecetaInadecuadaParaMi(unaReceta) && puedeAccederA(unaReceta);
	}

	public Collection<Receta> consultarRecetas(IFiltro unFiltro) {
		return unFiltro.filtrarRecetas(// FIXME tener una instancia de clase
										// publica en el recetario
				repositorio.listarTodasPuedeAcceder(this), this);
	}

	public Collection<Receta> consultarRecetasSt(GestorDeConsultas unFiltrado) {
		return unFiltrado.aplicarFiltros(repositorio.listarTodasPuedeAcceder(this), this);
	}

	public boolean noLeDisgusta(Receta unaReceta) {
		return !unaReceta.tieneAlgunIngredienteDeEstos(disgustosAlimenticios);
	}

	public void agregarAFavorita(Receta unaReceta) {
		recetasFavoritas.add(unaReceta);
	}

	public void quitarRecetaFavorita(Receta unaReceta) {
		recetasFavoritas.remove(unaReceta);
	}

	// TODO seria mejor en el recetario
	public void crearReceta(Receta unaReceta) {
		if (!unaReceta.esValida()) {
			throw new RecetaNoValidaException("No se Puede agregar una receta no válida!!!");
		}

		unaReceta.setDueño(this);
		repositorio.agregarReceta(unaReceta);
	}

	public void eliminarReceta(Receta unaReceta) {
		if (!unaReceta.esElDueño(this)) {
			throw new NoPuedeEliminarRecetaExeption(
			// FIXME no es necesario poner una causa
					"No puede eliminar una receta que no creó");

		}
		repositorio.quitarReceta(unaReceta);
		quitarRecetaFavorita(unaReceta);
	}

	public boolean puedeAccederA(Receta unaReceta) {
		return unaReceta.esPublica() || unaReceta.esElDueño(this) || esRecetaDeGrupo(unaReceta);
	}

	public void modificarReceta(Receta viejaReceta, Receta nuevaReceta) {
		if (!puedeAccederA(viejaReceta)) {
			throw new NoPuedeAccederARecetaException("No tiene permiso para acceder a esa receta");
		}

		if (viejaReceta.esElDueño(this)) {
			eliminarReceta(viejaReceta);
		}
		crearReceta(nuevaReceta);
	}

	// Punto 5
	public void crearRecetaConSubRecetas(Receta unaReceta, Collection<Receta> unasSubRecetas) {
		if (unasSubRecetas.stream().anyMatch(unaSubReceta -> !puedeAccederA(unaSubReceta))) {
			throw new NoPuedeAccederARecetaException("No puede agregar subrecetas a las que no tenga permiso de acceder");
		}
		unaReceta.agregarSubRecetas(unasSubRecetas);
		crearReceta(unaReceta);
	}

	// GETTERS FEOS Y MALOS para punto 1 entrega 3

	public String getNombre() {
		return datosPersonales.getNombre();
	}

	public Collection<CondicionDeSalud> getCondicionesDeSalud() {
		return condicionesDeSalud;
	}

	// Fin de getter feos y malos

	public boolean tieneCondicionDeSalud(CondicionDeSalud unaCondicion) {
		return condicionesDeSalud.contains(unaCondicion);
	}

	public boolean esDeSexo(String sexo) {
		return datosPersonales.getSexo().equals(sexo);
	}

}
