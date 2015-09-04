package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;

public class Condimentacion {

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

	@Override
	public boolean equals(Object otraCondimentacion) {
		return otraCondimentacion != null && otraCondimentacion.getClass() == Condimentacion.class
				&& (this.mismaCondimentacion((Condimentacion) otraCondimentacion) || this == otraCondimentacion);
	}

	private boolean mismaCondimentacion(Condimentacion otraCondimentacion) {
		return condimento != null && condimento.equals(otraCondimentacion.getCondimento()) && cantidad == (otraCondimentacion.getCantidad());
	}
}
