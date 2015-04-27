package ar.edu.grupo5.jm.dss.QueComemos;

public class Usuario 
{
	private double peso;
	private double estatura;
	
	public Usuario(double unPeso, double unaEstatura) {
		peso = unPeso;
		estatura = unaEstatura;
	}
	
	public double indiceMasaCorporal() {
		return peso / Math.pow(estatura,2);
	}
	
}
