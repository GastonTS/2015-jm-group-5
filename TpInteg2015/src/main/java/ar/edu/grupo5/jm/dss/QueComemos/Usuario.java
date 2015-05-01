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
	
	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura,2);
	}
	
	private boolean esUsuarioValidoParaSusCondiciones() {
		return condicionesPreexistentes.stream().allMatch(condicion -> condicion.esUsuarioValido(this));
	}
	
	public boolean esUsuarioValido (){
		return tieneCamposObligatorios() && esUsuarioValidoParaSusCondiciones();		
	}
	
	private boolean subsanaTodasLasCondiciones() {
		return condicionesPreexistentes.stream().allMatch(condicion -> condicion.subsanaCondicion(this));
	}
	
	public boolean sigueRutinaSaludable(){
		return 18>=indiceMasaCorporal() && indiceMasaCorporal()<=30 && this.subsanaTodasLasCondiciones();
	}
	
	public void crearReceta(){
		//implementar es tipo Receta
	}
	
	public void modificarReceta(){
		//implementar es tipo Receta
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
	
	private boolean tieneCamposObligatorios() {
		return nombre!=null && peso!=0 && estatura!=0 && fechaDeNacimiento!=null && rutina!=null;
	}
}
