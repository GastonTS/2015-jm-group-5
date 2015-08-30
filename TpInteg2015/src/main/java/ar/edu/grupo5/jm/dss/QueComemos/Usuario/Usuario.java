package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Grupo;
import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectUpdater;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;

public class Usuario implements ObjectUpdater {

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

		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		disgustosAlimenticios = unosDisgustosAlimenticios;
		condicionesDeSalud = unasCondicionesDeSalud;
		rutina = unaRutina;
	}

	public void agregarGrupo(Grupo unGrupo) {
		grupos.add(unGrupo);
		unGrupo.añadirIntegrante(this);
	}

	public double getPeso() {
		return complexion.getPeso();
	}

	public boolean indicaSexo() {
		return datosPersonales.indicaSexo();
	}

	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
	}

	// Punto 2.b
	public boolean sigueRutinaSaludable() {
		return indiceMasaCorporalSaludable() && this.subsanaTodasLasCondiciones();
	}

	private boolean indiceMasaCorporalSaludable() {
		return 18 <= complexion.indiceMasaCorporal() && complexion.indiceMasaCorporal() <= 30;
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

	public boolean noLeDisgusta(Receta unaReceta) {
		return !unaReceta.tenesAlgunIngredienteDeEstos(disgustosAlimenticios);
	}

	public void agregarAFavorita(Receta unaReceta) {
		recetasFavoritas.add(unaReceta);
	}

	public void quitarRecetaFavorita(Receta unaReceta) {
		recetasFavoritas.remove(unaReceta);
	}

	public boolean puedeAccederA(Receta unaReceta) {
		return unaReceta.esPublica() || unaReceta.esElDueño(this) || esRecetaDeGrupo(unaReceta);
	}

	public String getNombre() {
		return datosPersonales.getNombre();
	}

	public Collection<CondicionDeSalud> getCondicionesDeSalud() {
		return condicionesDeSalud;
	}

	public boolean tieneCondicionDeSalud(CondicionDeSalud unaCondicion) {
		return condicionesDeSalud.contains(unaCondicion);
	}

	public Sexo getSexo() {
		return datosPersonales.getSexo();
	}

	public boolean esVegano() {
		return condicionesDeSalud.stream().anyMatch(condicion -> condicion.esCondicionVegana());
	}
}
