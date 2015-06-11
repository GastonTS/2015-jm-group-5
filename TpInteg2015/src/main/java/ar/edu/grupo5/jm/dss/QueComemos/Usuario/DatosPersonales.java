package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.time.LocalDate;

public class DatosPersonales {

	private String nombre;
	private String sexo;
	private LocalDate fechaDeNacimiento;

	public DatosPersonales(String unNombre, String unSexo, LocalDate unaFechaDeNacimiento) {
		nombre = unNombre;
		sexo = unSexo;
		fechaDeNacimiento = unaFechaDeNacimiento;
		
		if(!sonValidos()) {
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
		return sexo != null && !(sexo.equals(""));
	}

	public boolean fechaDeNacimientoAnteriorAHoy() {
		return fechaDeNacimiento.isBefore(LocalDate.now());
	}

	public String getNombre() {
		return nombre;
	}

	public String getSexo() {
		return sexo;
	}

}
