package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class Condimento {

	private String nombre;
	private double cantidad;
	
	public Condimento(String unNombre,double unaCantidad){
		nombre= unNombre;
		cantidad  = unaCantidad;
	}
	public Condimento(String unNombre){
		nombre= unNombre;
	}
	
	public boolean esCondimentoConMasDe(String unNombre, double unaCantidad) {
		return nombre.equals(unNombre)&& cantidad>unaCantidad;
	}
	public  boolean sosIgualAUnoDe(Collection<Condimento> condimentosProhibidos) {
		return condimentosProhibidos.stream().anyMatch(condimento -> condimento.sosIgualA(this));
	}
	
	public boolean sosIgualA(Condimento unCondimento){
		
		return nombre.equals(unCondimento.nombre);	
	}
	public boolean mayorCantidadQue(Condimento unCondimento) {
		return this.sosIgualA(unCondimento) && this.cantidad > unCondimento.cantidad;
	}
	
}
