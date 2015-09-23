package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.time.LocalDate;

import javax.persistence.Embeddable;

@Embeddable
public class DatosPersonales {

	public enum Sexo {
		MASCULINO, FEMENINO
	}

	private String nombre;

	private Sexo sexo;

	private LocalDate fechaDeNacimiento;
	
	public DatosPersonales() {
		
	}

	public DatosPersonales(String unNombre, Sexo unSexo, LocalDate unaFechaDeNacimiento) {
		nombre = unNombre;
		sexo = unSexo;
		fechaDeNacimiento = unaFechaDeNacimiento;

		if (!sonValidos()) {
			throw new DatosPersonalesNoValidosException("Los Datos Personales no son Validos!!!");
		}
	}

	public boolean sonValidos() {
		return nombre != null && fechaDeNacimiento != null && !tieneNombreCorto() && fechaDeNacimientoAnteriorAHoy();
	}

	public boolean tieneNombreCorto() {
		return nombre.length() < 4;
	}

	public boolean indicaSexo() {
		return sexo != null;
	}

	public boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}

	public String getNombre() {
		return nombre;
	}

	public Sexo getSexo() {
		return sexo;
	}
}
