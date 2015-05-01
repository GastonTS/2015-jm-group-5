package ar.edu.grupo5.jm.dss.QueComemos;

import java.time.LocalDate;
import java.util.Collection;


public class Usuario 
{
	private double peso;
	private double estatura;
	private String nombre;
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
	
	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura,2);
	}
	
	public boolean esUsuarioValido (){
		//implementar!
		return true;		
	}
	
	public boolean sigueRutinaSaludable(){
		//implementar!
		return true;
	
	}
	
	public void crearReceta(){
		//implementar es tipo Receta
	}
	
	public void modificarReceta(){
		//implementar es tipo Receta
	}
}
