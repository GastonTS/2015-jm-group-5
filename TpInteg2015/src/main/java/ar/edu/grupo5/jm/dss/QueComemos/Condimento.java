package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class Condimento {//TODO pensar un nombre mejor

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
	public  boolean sosIgualAUnoDe(Collection<String> condimentosProhibidos) {
		  return condimentosProhibidos.stream().anyMatch(condimento -> this.sosIgualA(condimento));
		 }
		 //TODO nombre? sos? capaz sea mejor hablar de tipo
	public boolean sosIgualA(String unCondimento){
		  
		  return nombre.equals(unCondimento); 
	 }
		 
	public boolean mayorCantidadQue(Condimento unCondimento) {
		return this.sosIgualA(unCondimento.nombre) && this.cantidad > unCondimento.cantidad;
	}
	
}
