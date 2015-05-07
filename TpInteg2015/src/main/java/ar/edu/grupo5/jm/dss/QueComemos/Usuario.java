package ar.edu.grupo5.jm.dss.QueComemos;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;


public class Usuario 
{//TODO ser consistentes en la identacion
	private String nombre;
	private String sexo;
	private LocalDate fechaDeNacimiento;
	private double peso;
	private double estatura;
	private Collection<String> preferenciasAlimenticias;
	private Collection<CondicionPreexistente> condicionesPreexistentes;
	private String rutina;
	private Collection<Receta> recetasPropias;
	
	private static Collection<Receta> recetasPublicas;
	
	//XXX long parameter list
	public Usuario(double unPeso, double unaEstatura, String unNombre,LocalDate unaFechaDeNacimiento,
			Collection<String> unasPreferenciasAlimenticias, Collection<String> unosDisgustosAlimenticios,
			Collection<Receta> unasRecetasPropias, Collection<CondicionPreexistente> unasCondicionesPreexistentes,
			String unaRutina) {
		nombre = unNombre;
		peso = unPeso;
		estatura = unaEstatura;
		fechaDeNacimiento = unaFechaDeNacimiento;
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		setRecetasPropias(unasRecetasPropias);
		setCondicionesPreexistentes(unasCondicionesPreexistentes);
		rutina = unaRutina;
	}
	
	//Setter de variable de clase recetasPublicas
	public static void recetasPublicas(Collection<Receta> recetas){
		recetasPublicas = recetas;
	}
	
	public double getPeso() {
		return peso; 
	}
	
	/**
	 * @return the condicionesPreexistentes
	 */
	public Collection<CondicionPreexistente> getCondicionesPreexistentes() {
		return condicionesPreexistentes;
	}

	/**
	 * @param condicionesPreexistentes the condicionesPreexistentes to set
	 */
	//TODO minimizar las mutaciones, ergo, no poner setters inutiles
	public void setCondicionesPreexistentes(Collection<CondicionPreexistente> condicionesPreexistentes) {
		this.condicionesPreexistentes = condicionesPreexistentes;
	}
	
	//Punto 1
	//XXX esto podria no ser facil de extender
	public boolean esUsuarioValido (){
		return tieneCamposObligatorios() && 
				esNombreCorto() && 
				esUsuarioValidoParaSusCondiciones() && 
				fechaDeNacimientoAnteriorAHoy();		
	}
	
	public boolean esNombreCorto() {
		return nombre.length()>4;
	}
	
	public boolean tieneCamposObligatorios() {
		return nombre!=null && peso!=0 && estatura!=0 && fechaDeNacimiento!=null && rutina!=null;
	}
	
	public boolean esUsuarioValidoParaSusCondiciones() {
		return getCondicionesPreexistentes().stream().allMatch(condicion -> condicion.esUsuarioValido(this));
	}
	
	public boolean indicaSexo() {
		return sexo!=null && !(sexo.equals(""));
	}
	
	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
	}
	
	public boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}

	//Punto 2.a
	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura,2);
	}
	
	//Punto 2.b
	public boolean sigueRutinaSaludable(){
		return 18<=indiceMasaCorporal() && indiceMasaCorporal()<=30 && this.subsanaTodasLasCondiciones();
	}
	
	private boolean subsanaTodasLasCondiciones() {
		if (getCondicionesPreexistentes()==null) return true; //FIXME asegurarse de que no sea null
		return getCondicionesPreexistentes().stream().allMatch(condicion -> condicion.subsanaCondicion(this));
	}
	
	public boolean tieneRutinaActiva() {
		return this.tieneRutinaIntensiva() || rutina.equals("Alta"); //TODO usar enums
	}
	
	public boolean tieneRutinaIntensiva(){
		return rutina.equals("Intensiva");
	}
	
	public boolean tienePreferencia(String preferencia) {
		return preferenciasAlimenticias.contains(preferencia);
	}
	
	public boolean tieneAlgunaDeEstasPreferencias(Collection<String> preferencias){
		return preferenciasAlimenticias.stream().anyMatch(preferencia -> preferencias.contains(preferencia));
	}
	
	
	//Punto 3.a
	public void crearReceta(Receta unaReceta){
		if(!unaReceta.esValida()) {
			throw new RecetaNoValidaException("No se Puede agregar una receta no válida!!!",new IOException()); //ejjejej
		}
		recetasPropias.add(unaReceta);
	}
	
	//Punto 3.b en receta
	public boolean sosRecetaInadecuadaParaMi(Receta unaReceta){
		return condicionesPreexistentes.stream().anyMatch(condicion -> condicion.esInadecuada(unaReceta));
	}//si no tiene condiciones devuelve false por lo tanto no es inadecuada
	
	//Punto 4.a y 4.b
	public boolean puedeAcceder(Receta unaReceta){
		return esRecetaPropia(unaReceta) || esRecetaPublica(unaReceta);
	}
	
	public boolean esRecetaPropia(Receta unaReceta){
		return unaReceta.estasEnEstasRecetas(recetasPropias);
	}
	
	private boolean esRecetaPublica(Receta unaReceta){
		return unaReceta.estasEnEstasRecetas(recetasPublicas);
	}
	
	//Punto 4.c
	public void modificarReceta(Receta unaReceta){
		if(esRecetaPropia(unaReceta)) {
			modificarRecetaPropia(unaReceta);
		} else 
			crearReceta(unaReceta);
	}

	private void modificarRecetaPropia(Receta unaReceta) {
		eliminarRecetaPropia(unaReceta.getNombre());
		recetasPropias.add(unaReceta);
	}

	public void eliminarRecetaPropia(String nombreReceta) {
		Receta recetaAEliminar = recetasPropias.stream().filter(unaReceta -> unaReceta.getNombre().equals(nombreReceta)).findFirst().get();
		recetasPropias.remove(recetaAEliminar);
	}

	
	//Punto 5
	public void crearRecetaConSubRecetas(Receta unaReceta, Collection<Receta> unasSubRecetas){
		unaReceta.agregarSubRecetas(unasSubRecetas);
		crearReceta(unaReceta);
	}
	/**
	 * @return the recetasPropias
	 */
	public Collection<Receta> getRecetasPropias() {
		return recetasPropias;
	}

	/**
	 * @param recetasPropias the recetasPropias to set
	 */
	public void setRecetasPropias(Collection<Receta> recetasPropias) {
		this.recetasPropias = recetasPropias;
	}
	

}
