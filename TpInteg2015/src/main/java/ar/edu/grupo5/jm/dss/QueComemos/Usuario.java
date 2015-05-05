package ar.edu.grupo5.jm.dss.QueComemos;

import java.time.LocalDate;
import java.util.Collection;


public class Usuario 
{
	private String nombre;
	private String sexo;
	private double peso;
	private double estatura;
	private LocalDate fechaDeNacimiento;
	private Collection<String> preferenciasAlimenticias;
	private Collection<String> disgustosAlimenticios;
	private static Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasPropias;
	private Collection<CondicionPreexistente> condicionesPreexistentes;
	private String rutina;
	
	
	
	public Usuario(double unPeso, double unaEstatura, String UnNombre,LocalDate UnaFechaDeNacimiento,
			Collection<String> unasPreferenciasAlimenticias, Collection<String> unosDisgustosAlimenticios,
			Collection<Receta> unasRecetasPropias, Collection<CondicionPreexistente> unasCondicionesPreexistentes,
			String unaRutina) {
		peso = unPeso;
		estatura = unaEstatura;
		fechaDeNacimiento = UnaFechaDeNacimiento;
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		disgustosAlimenticios = unosDisgustosAlimenticios;
		recetasPropias = unasRecetasPropias;
		setCondicionesPreexistentes(unasCondicionesPreexistentes);
		rutina = unaRutina;
	}
	
	//Punto 1
	public boolean esUsuarioValido (){
		return tieneCamposObligatorios() && nombre.length()>4 && esUsuarioValidoParaSusCondiciones() && fechaDeNacimientoAnteriorAHoy();		
	}

	//Punto 2.a
	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura,2);
	}
	
	//Punto 2.b
	public boolean sigueRutinaSaludable(){
		//usar isBetween o algo similar. Estoy repitiendo lógica acá y en receta para ver si es valida
		return 18>=indiceMasaCorporal() && indiceMasaCorporal()<=30 && this.subsanaTodasLasCondiciones();
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
	public boolean puedeModificar(Receta unaReceta){
		return puedeAcceder(unaReceta);//La definicion de visualizar/modificar segun el enunciado es la misma
	}
	
	//Punto 4.c
	public void modificarReceta(){
		//implementar es tipo Receta
	}
	
	
	
	private boolean esUsuarioValidoParaSusCondiciones() {
		return getCondicionesPreexistentes().stream().allMatch(condicion -> condicion.esUsuarioValido(this));
	}
	
	private boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}
	
	private boolean subsanaTodasLasCondiciones() {
		return getCondicionesPreexistentes().stream().allMatch(condicion -> condicion.subsanaCondicion(this));
	}
	
	private boolean tieneCamposObligatorios() {
		return nombre!=null && peso!=0 && estatura!=0 && fechaDeNacimiento!=null && rutina!=null;
	}
	
	//auxiliares para punto 4.a y 4.b
	private boolean esRecetaPropia(Receta unaReceta){
		return recetasPropias.contains(unaReceta);
	}
	
	private boolean esRecetaPublica(Receta unaReceta){
		return recetasPublicas.contains(unaReceta);
	}
	//Setter de variable de clase recetasPublicas
	public static void recetasPublicas(Collection<Receta> recetas){
		recetasPublicas = recetas;
	}
	
	public double getPeso() {
		return peso; 
	}
	
	public boolean indicaSexo() {
		return sexo!=null && !(sexo.equals(""));
	}
	
	public boolean tieneAlgunaPreferencia() {
		return !preferenciasAlimenticias.isEmpty();
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
	public boolean tienePreferencias(Collection<String> preferencias){
		return preferenciasAlimenticias.containsAll(preferencias);
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
}
