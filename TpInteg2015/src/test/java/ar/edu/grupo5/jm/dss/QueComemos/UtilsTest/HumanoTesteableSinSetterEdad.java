package ar.edu.grupo5.jm.dss.QueComemos.UtilsTest;

import ar.edu.grupo5.jm.dss.QueComemos.Utils.ObjectUpdater;

public class HumanoTesteableSinSetterEdad implements ObjectUpdater {

	private int edad;
	private String nombre;
	
	public int getEdad() {
		return edad;
	}
	public String getNombre() {
		return nombre;
	}

	
	public void setNombre(String unNombre) {
		this.nombre = unNombre;
	}
	
	public void ajustarEdad(int unaEdad) {
		this.edad = unaEdad;
	}
	
}
