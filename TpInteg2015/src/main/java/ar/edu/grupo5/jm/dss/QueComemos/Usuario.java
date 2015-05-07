package ar.edu.grupo5.jm.dss.QueComemos;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;


public class Usuario 
{
	private String nombre;
	private String sexo;
	private LocalDate fechaDeNacimiento;
	private double peso;
	private double estatura;
	private Collection<String> preferenciasAlimenticias;
	private Collection<String> disgustosAlimenticios;
	private Collection<CondicionPreexistente> condicionesPreexistentes;
	private String rutina;
	private static Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasPropias;
	
	
	public Usuario(double unPeso, double unaEstatura, String UnNombre,LocalDate UnaFechaDeNacimiento,
			Collection<String> unasPreferenciasAlimenticias, Collection<String> unosDisgustosAlimenticios,
			Collection<Receta> unasRecetasPropias, Collection<CondicionPreexistente> unasCondicionesPreexistentes,
			String unaRutina) {
		peso = unPeso;
		estatura = unaEstatura;
		fechaDeNacimiento = UnaFechaDeNacimiento;
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		disgustosAlimenticios = unosDisgustosAlimenticios;
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
	public void setCondicionesPreexistentes(Collection<CondicionPreexistente> condicionesPreexistentes) {
		this.condicionesPreexistentes = condicionesPreexistentes;
	}
	
	//Punto 1
	public boolean esUsuarioValido (){
		return tieneCamposObligatorios() && nombre.length()>4 && esUsuarioValidoParaSusCondiciones() && fechaDeNacimientoAnteriorAHoy();		
	}
	
	private boolean tieneCamposObligatorios() {
		return nombre!=null && peso!=0 && estatura!=0 && fechaDeNacimiento!=null && rutina!=null;
	}
	
	private boolean esUsuarioValidoParaSusCondiciones() {
		return getCondicionesPreexistentes().stream().allMatch(condicion -> condicion.esUsuarioValido(this));
	}
	
	public boolean indicaSexo() {
		return sexo!=null && !(sexo.equals(""));
	}
	
	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
	}
	
	private boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}

	//Punto 2.a
	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura,2);
	}
	
	//Punto 2.b
	public boolean sigueRutinaSaludable(){
		return 18>=indiceMasaCorporal() && indiceMasaCorporal()<=30 && this.subsanaTodasLasCondiciones();
	}
	
	private boolean subsanaTodasLasCondiciones() {
		return getCondicionesPreexistentes().stream().allMatch(condicion -> condicion.subsanaCondicion(this));
	}
	
	public boolean tieneRutinaActiva() {
		return this.tieneRutinaIntensiva() || rutina.equals("Alta");
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
	public Receta crearReceta(Receta unaReceta){
		//tirar excepcion si receta no es valida
		recetasPropias.add(unaReceta);
		return unaReceta;
		//decidi devolver la receta creada, Puede ser que mas adelante convenga que sea una metodo void
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
	
	public boolean esRecetaPublica(Receta unaReceta){
		return unaReceta.estasEnEstasRecetas(recetasPublicas);
	}
	
	//Punto 4.c
	public void modificarReceta(Receta unaReceta){
		if(this.esRecetaPropia(unaReceta)) {
			this.modificarRecetaPropia(unaReceta);
		}
		this.crearReceta(unaReceta);
		
		
	}

	private void modificarRecetaPropia(Receta unaReceta) {
		eliminarRecetaPropia(unaReceta.getNombre());
		recetasPropias.add(unaReceta);
	}

	private void eliminarRecetaPropia(String nombreReceta) {
		Optional<Receta> recetaAEliminar = recetasPropias.stream().filter(unaReceta -> unaReceta.getNombre().equals(nombreReceta)).findFirst();
		recetasPropias.remove(recetaAEliminar.get());
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
