package ar.edu.grupo5.jm.dss.QueComemos;

import java.time.LocalDate;
import java.util.Collection;

public class Usuario {
	private String nombre;
	private String sexo;
	private Complexion complexion;
	private LocalDate fechaDeNacimiento;
	private Collection<String> preferenciasAlimenticias;
	private Collection<CondicionDeSalud> condicionesDeSalud;

	public enum Rutina {
		LEVE, NADA, MEDIANA, INTENSIVA, ALTA
	}

	private Rutina rutina;
	private Collection<Receta> recetasPropias;

	private static Collection<Receta> recetasPublicas;

	// XXX long parameter list
	public Usuario(String unNombre, LocalDate unaFechaDeNacimiento,
			Complexion unaComplexion,
			Collection<String> unasPreferenciasAlimenticias,
			Collection<Receta> unasRecetasPropias,
			Collection<CondicionDeSalud> unasCondicionesDeSalud,
			Rutina unaRutina) {
		nombre = unNombre;
		fechaDeNacimiento = unaFechaDeNacimiento;
		complexion = unaComplexion;
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		recetasPropias = unasRecetasPropias;
		condicionesDeSalud = unasCondicionesDeSalud;
		rutina = unaRutina;
	}

	// Setter de variable de clase recetasPublicas
	public static void setRecetasPublicas(Collection<Receta> recetas) {
		recetasPublicas = recetas;
	}

	public double getPeso() {
		return complexion.getPeso();
	}

	public Collection<Receta> getRecetasPropias() {
		return recetasPropias;
	}

	// Punto 1
	// XXX esto podria no ser facil de extender
	public boolean esUsuarioValido() {
		return tieneCamposObligatorios() && esNombreCorto()
				&& fechaDeNacimientoAnteriorAHoy()
				&& esUsuarioValidoParaSusCondiciones();
	}

	public boolean esNombreCorto() {
		return nombre.length() > 4;
	}

	public boolean tieneCamposObligatorios() {
		return nombre != null && complexion.esComplexionValida()
				&& fechaDeNacimiento != null && rutina != null;
	}

	public boolean esUsuarioValidoParaSusCondiciones() {
		return condicionesDeSalud.stream().allMatch(
				condicion -> condicion.esUsuarioValido(this));
	}

	public boolean indicaSexo() {
		return sexo != null && !(sexo.equals(""));
	}

	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
	}

	public boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}

	// Punto 2.a
	public double indiceMasaCorporal() {
		return complexion.indiceMasaCorporal();
	}

	// Punto 2.b
	public boolean sigueRutinaSaludable() {
		return 18 <= indiceMasaCorporal() && indiceMasaCorporal() <= 30
				&& this.subsanaTodasLasCondiciones();
	}

	private boolean subsanaTodasLasCondiciones() {
		if (condicionesDeSalud == null)
			return true; // FIXME asegurarse de que no sea null
		return condicionesDeSalud.stream().allMatch(
				condicion -> condicion.subsanaCondicion(this));
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

	public boolean tieneAlgunaDeEstasPreferencias(
			Collection<String> preferencias) {
		return preferenciasAlimenticias.stream().anyMatch(
				preferencia -> preferencias.contains(preferencia));
	}

	// Punto 3.a
	public void crearReceta(Receta unaReceta) {
		if (!unaReceta.esValida()) {
			throw new RecetaNoValidaException(
					"No se Puede agregar una receta no válida!!!",
					new RuntimeException());
		}
		recetasPropias.add(unaReceta);
	}

	// Punto 3.b en receta
	public boolean sosRecetaInadecuadaParaMi(Receta unaReceta) {
		return condicionesDeSalud.stream().anyMatch(
				condicion -> condicion.esInadecuada(unaReceta));
	}// si no tiene condiciones devuelve false por lo tanto no es inadecuada

	// Punto 4.a y 4.b
	public boolean puedeAcceder(Receta unaReceta) {
		return esRecetaPropia(unaReceta) || esRecetaPublica(unaReceta);
	}

	public boolean esRecetaPropia(Receta unaReceta) {
		return recetasPropias.contains(unaReceta);
	}

	private boolean esRecetaPublica(Receta unaReceta) {
		return recetasPublicas.contains(unaReceta);
	}

	// Punto 4.c
	public void modificarReceta(Receta viejaReceta, Receta nuevaReceta) {
		if (!puedeAcceder(viejaReceta)) {
			throw new NoPuedeAccederARecetaException(
					"No tiene permiso para acceder a esa receta",
					new RuntimeException());
		}

		if (esRecetaPropia(viejaReceta)) {
			modificarRecetaPropia(viejaReceta, nuevaReceta);
		} else {
			crearReceta(nuevaReceta);
		}
	}

	private void modificarRecetaPropia(Receta viejaReceta, Receta nuevaReceta) {
		eliminarRecetaPropia(viejaReceta);
		crearReceta(nuevaReceta);
	}

	public void eliminarRecetaPropia(Receta unaReceta) {
		recetasPropias.remove(unaReceta);
	}

	// Punto 5
	public void crearRecetaConSubRecetas(Receta unaReceta,
			Collection<Receta> unasSubRecetas) {
		unaReceta.agregarSubRecetas(unasSubRecetas);
		crearReceta(unaReceta);
	}
}
