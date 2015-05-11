package ar.edu.grupo5.jm.dss.QueComemos;

import java.time.LocalDate;

public class DatosPersonales {

	private String nombre;
	private String sexo;
	private LocalDate fechaDeNacimiento;

	public DatosPersonales(String unNombre, String unSexo, LocalDate unaFechaDeNacimiento){
		nombre = unNombre;
		sexo = unSexo;
		fechaDeNacimiento = unaFechaDeNacimiento;
	}
	
	public boolean esNombreCorto() {
		return nombre.length() < 4;
	}

	public boolean indicaSexo() {
		return sexo != null && !(sexo.equals(""));
	}
	
	public boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}
	
	public boolean esDatosPersonalesValido(){
		return nombre != null && fechaDeNacimiento != null; 
	}
}
