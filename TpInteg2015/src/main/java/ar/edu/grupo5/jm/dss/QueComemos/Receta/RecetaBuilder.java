package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioNoValidoException;

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
		if(!esValidaReceta()) {
			throw new UsuarioNoValidoException("La Receta No es Válida!!!");
		}
		
		return new Receta(nombre, ingredientes, condimentaciones, subRecetas, cantCalorias, dificultad);
	}

	private boolean esValidaReceta() {
		// TODO Auto-generated method stub
		return true;
	}

}
