package ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdaterTest;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.Updateable;

public class HumanoTesteableFeliz extends Humano{
	
	@Updateable
	private int edad;
	@Updateable
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
	
	public void setEdad(int unaEdad) {
		this.edad = unaEdad;
	}

}
