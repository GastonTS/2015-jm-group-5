package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;

public class RecetaBuilder {

	private String nombre;
	private Collection<String> ingredientes = new ArrayList<String>();
	private Collection<Condimentacion> condimentaciones = new ArrayList<Condimentacion>();
	private double cantCalorias;
	private Collection<Receta> subRecetas = new ArrayList<Receta>();
	private Dificultad dificultad;

	public RecetaBuilder setNombre(String unNombre) {
		nombre = unNombre;
		return this;
	}

	public RecetaBuilder agregarIngrediente(String unIngrediente) {
		ingredientes.add(unIngrediente);
		return this;
	}
	
	public RecetaBuilder agregarTodosLosIngredientes(Collection<String> unosIngredientes) {
		ingredientes.addAll(unosIngredientes);
		return this;
	}

	public RecetaBuilder agregarCondimentaciones(Condimentacion unaCondimentacion) {
		condimentaciones.add(unaCondimentacion);
		return this;
	}

	public RecetaBuilder setCantCalorias(double unaCantCalorias) {
		cantCalorias = unaCantCalorias;
		return this;
	}

	public RecetaBuilder agregarSubReceta(Receta unaSubReceta) {
		subRecetas.add(unaSubReceta);
		return this;
	}

	public RecetaBuilder setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
		return this;
	}

	public Receta construirReceta() {
		if(!esRecetaValida()) {
			throw new RecetaNoValidaException("La Receta No es Válida!!!");
		}
		
		return new Receta(nombre, ingredientes, condimentaciones, subRecetas, cantCalorias, dificultad);
	}

	private boolean esRecetaValida() {
		return tieneAlMenosUnIngrediente() && 
				caloriasEntre(10, 5000);
	}
	
	private boolean tieneAlMenosUnIngrediente() {
		return !ingredientes.isEmpty();
	}

	private boolean caloriasEntre(int minimo, int maximo) {
		return cantCalorias >= minimo && cantCalorias <= maximo;
	}
	
}
