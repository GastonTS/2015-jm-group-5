package ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdaterTest;

public class HumanoTesteableConSetEdadPrivado extends Humano{

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
	
	
	@SuppressWarnings("unused")
	private void setEdad(int unaEdad) {
		this.edad = unaEdad;
	}
	protected void setE(int unaEdad) {
		this.edad = unaEdad;
	}
	
	public void ajustarEdad(int unaEdad) {
		this.edad = unaEdad;
	}
}
