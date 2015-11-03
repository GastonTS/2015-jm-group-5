package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Condimentacion {

	@Id
	@GeneratedValue
	private Long condimentacionId;
	private String condimento;
	private double cantidad;

	public Condimentacion() {
	}
	
	public Condimentacion(String unCondimento, double unaCantidad) {
		condimento = unCondimento;
		cantidad = unaCantidad;
	}

	public String getCondimento() {
		return condimento;
	}
	
	public double getCantidad() {
		return cantidad;
	}
	
	public boolean tieneCondimentoUnoDe(Collection<String> unosCondimentos) {
		return unosCondimentos.stream().anyMatch(condimento -> tieneCondimento(condimento));
	}

	public boolean tieneCondimento(String unCondimento) {
		return condimento.equals(unCondimento);
	}

	public boolean mayorCantidadDeMismoCondimentoQue(Condimentacion unaCondimentacion) {
		return tieneCondimento(unaCondimentacion.condimento) && cantidad > unaCondimentacion.cantidad;
	}

}
