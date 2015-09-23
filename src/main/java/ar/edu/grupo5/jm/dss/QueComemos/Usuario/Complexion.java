package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import javax.persistence.Embeddable;

@Embeddable
public class Complexion {
	private double peso;
	private double estatura;

	public Complexion() {

	}

	public Complexion(double unPeso, double unaEstatura) {
		peso = unPeso;
		estatura = unaEstatura;

		if (!esComplexionValida()) {
			throw new ComplexionNoValidoException("La Complexion no es Valido!!!");
		}
	}

	public double getPeso() {
		return peso;
	}

	public double getEstatura() {
		return estatura;
	}

	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura, 2);
	}

	public boolean esComplexionValida() {
		return peso != 0 && estatura != 0;
	}
}
