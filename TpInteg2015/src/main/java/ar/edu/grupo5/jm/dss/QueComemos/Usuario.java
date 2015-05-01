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
	private Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasPropias;
	private Collection<CondicionPreexistente> condicionesPreexistentes;
	private String rutina;
	
	
	
	public Usuario(double unPeso, double unaEstatura, String UnNombre,LocalDate UnaFechaDeNacimiento,
			Collection<String> unasPreferenciasAlimenticias, Collection<String> unosDisgustosAlimenticios,
			Collection<Receta> unasRecetasPublicas, Collection<Receta> unasRecetasPropias,
			Collection<CondicionPreexistente> unasCondicionesPreexistentes, String unaRutina) {
		peso = unPeso;
		estatura = unaEstatura;
		fechaDeNacimiento = UnaFechaDeNacimiento;
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		disgustosAlimenticios = unosDisgustosAlimenticios;
		recetasPublicas = unasRecetasPublicas;
		recetasPropias = unasRecetasPropias;
		condicionesPreexistentes = unasCondicionesPreexistentes;
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
		return 18>=indiceMasaCorporal() && indiceMasaCorporal()<=30 && this.subsanaTodasLasCondiciones();
	}
	
	//Punto 3.a
	public void crearReceta(){
		//implementar es tipo Receta
	}
	
	//Punto 4.c
	public void modificarReceta(){
		//implementar es tipo Receta
	}
	
	
	
	private boolean esUsuarioValidoParaSusCondiciones() {
		return condicionesPreexistentes.stream().allMatch(condicion -> condicion.esUsuarioValido(this));
	}
	
	private boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}
	
	private boolean subsanaTodasLasCondiciones() {
		return condicionesPreexistentes.stream().allMatch(condicion -> condicion.subsanaCondicion(this));
	}
	
	private boolean tieneCamposObligatorios() {
		return nombre!=null && peso!=0 && estatura!=0 && fechaDeNacimiento!=null && rutina!=null;
	}
	

	
	public double getPeso() {
		return peso; 
	}
	
	public boolean indicaSexo() {
		return sexo!=null && !(sexo.equals(""));
	}
	
	public boolean tieneAlgunaPreferencia() {
		return preferenciasAlimenticias.size()>0;
	}
	
	public boolean tieneRutinaActiva() {
		return rutina.equals("Intensiva") || rutina.equals("Alta");
	}
	

}
