package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

public class Complexion {
	private double peso;
	private double estatura;

	public Complexion(double unPeso, double unaEstatura) {
		peso = unPeso;
		estatura = unaEstatura;
	}

	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura, 2);
	}

	public double getPeso() {
		return peso;
	}

	public boolean esComplexionValida() {
		return peso != 0 && estatura != 0;
	}
}
