package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.Updateable;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;

@Entity
public class Usuario implements WithGlobalEntityManager {

	@Id
	@GeneratedValue
	private Long usuarioId;
	@Updateable
	private Boolean aceptado = false;
	@Embedded
	@Updateable
	private Complexion complexion;
	@Embedded
	@Updateable
	private DatosPersonales datosPersonales;
	@ManyToMany
	@Updateable
	private Collection<Ingrediente> preferenciasAlimenticias;
	@ManyToMany
	@Updateable
	private Collection<Ingrediente> disgustosAlimenticios;
	@ManyToMany
	@Updateable
	private Collection<CondicionDeSalud> condicionesDeSalud;
	@ManyToMany
	@Updateable
	private Collection<Grupo> grupos;

	public enum Rutina {
		LEVE, NADA, MEDIANA, INTENSIVA, ALTA
	}

	@Enumerated
	@Updateable
	private Rutina rutina;
	@ManyToMany
	@Updateable
	private Collection<Receta> recetasFavoritas;

	private String mail;

	public Usuario(DatosPersonales unosDatosPersonales, Complexion unaComplexion, Collection<Ingrediente> unasPreferenciasAlimenticias,
			Collection<Ingrediente> unosDisgustosAlimenticios, Collection<CondicionDeSalud> unasCondicionesDeSalud, Rutina unaRutina,
			String unMail) {

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

	public Boolean fueAceptado() {
		return aceptado;
	}
	
	public void aceptar() {
		aceptado = true;
	}
	
	public Long getId() {
		return usuarioId;
	}

	public double getPeso() {
		return complexion.getPeso();
	}

	public double getEstatura() {
		return complexion.getEstatura();
	}

	public String getNombre() {
		return datosPersonales.getNombre();
	}

	public Sexo getSexo() {
		return datosPersonales.getSexo();
	}

	public LocalDate getFechaDeNacimiento() {
		return datosPersonales.getFechaDeNacimiento();
	}

	public Collection<Ingrediente> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}

	public Collection<Ingrediente> getDisgustosAlimenticios() {
		return disgustosAlimenticios;
	}

	public Collection<CondicionDeSalud> getCondicionesDeSalud() {
		return condicionesDeSalud;
	}

	public Collection<Grupo> getGrupos() {
		return grupos;
	}

	public Rutina getRutina() {
		return rutina;
	}

	public Collection<Receta> getRecetasFavoritas() {
		return recetasFavoritas;
	}

	public String getMail() {
		return mail;
	}

	public void agregarGrupo(Grupo unGrupo) {
		grupos.add(unGrupo);
		unGrupo.añadirIntegrante(this);
	}

	public boolean indicaSexo() {
		return datosPersonales.indicaSexo();
	}

	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
	}

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

	public boolean tienePreferencia(Ingrediente preferencia) {
		return preferenciasAlimenticias.contains(preferencia);
	}

	public boolean tieneAlgunaDeEstasPreferencias(Collection<Ingrediente> preferencias) {
		return preferenciasAlimenticias.stream().anyMatch(preferencia -> preferencias.contains(preferencia));
	}

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

	public boolean tieneCondicionDeSalud(CondicionDeSalud unaCondicion) {
		return condicionesDeSalud.contains(unaCondicion);
	}

	public boolean esVegano() {
		return condicionesDeSalud.stream().anyMatch(condicion -> condicion.esCondicionVegana());
	}

	public void persistir() {
		entityManager().persist(this);
	}

}
