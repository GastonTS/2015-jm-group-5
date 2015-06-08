package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class Condimentacion {

	private String condimento;
	private double cantidad;

	public Condimentacion(String unCondimento, double unaCantidad) {
		condimento = unCondimento;
		cantidad = unaCantidad;
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
