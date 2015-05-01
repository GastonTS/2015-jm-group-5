package ar.edu.grupo5.jm.dss.QueComemos;

public class Condimento {

	private String nombre;
	private double cantidad;
	private String unidad;
	
	public boolean esCondimentoConMasDe(String unNombre, double unaCantidad, String unaUnidad) {
		return nombre.equals(unNombre)&& cantidad>unaCantidad && unidad.equals(unaUnidad);
	}
	
}
