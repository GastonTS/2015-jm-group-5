package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.Updateable;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;

@Entity
public class Usuario {

	@Id
	ObjectId id;
	@Transient
	@Updateable
	private Complexion complexion;
	@Updateable
	private DatosPersonales datosPersonales;
	@Updateable
	@Transient
	private Collection<String> preferenciasAlimenticias;
	@Transient
	@Updateable
	private Collection<String> disgustosAlimenticios;
	@Transient
	@Updateable
	private Collection<CondicionDeSalud> condicionesDeSalud;
	@Transient
	@Updateable
	private Collection<Grupo> grupos;
	public enum Rutina {
		LEVE, NADA, MEDIANA, INTENSIVA, ALTA
	}
	@Transient
	@Updateable
	private Rutina rutina;
	@Reference
	@Updateable
	private Collection<Receta> recetasFavoritas;
	@Transient
	private String mail;

	public Collection<Receta> getRecetasFavoritas() {
		return recetasFavoritas;
	}
	
	public Usuario(){
		
	}

	public Usuario(DatosPersonales unosDatosPersonales, Complexion unaComplexion, Collection<String> unasPreferenciasAlimenticias,
			Collection<String> unosDisgustosAlimenticios, Collection<CondicionDeSalud> unasCondicionesDeSalud, Rutina unaRutina, String unMail) {

		datosPersonales = unosDatosPersonales;
		complexion = unaComplexion;
		grupos = new ArrayList<Grupo>();
		recetasFavoritas = new ArrayList<Receta>();

		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		disgustosAlimenticios = unosDisgustosAlimenticios;
		condicionesDeSalud = unasCondicionesDeSalud;
		rutina = unaRutina;
		
		mail = unMail;
	}
	
	public ObjectId getId(){
		return id;
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
	
	public String getMail() {
		return mail;
	}
}
