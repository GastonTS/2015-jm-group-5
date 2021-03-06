package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;

public class RecetaBuilder {

	private String nombre;
	private Collection<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	private Collection<Condimentacion> condimentaciones = new ArrayList<Condimentacion>();
	private double cantCalorias;
	private Collection<Receta> subRecetas = new ArrayList<Receta>();
	private Dificultad dificultad;
	private Temporada temporada = Temporada.TODO_EL_AÑO;
	private String preparacion;
	private String urlImagen="";

	public RecetaBuilder setPropiedadesDe(Receta unaReceta) {
		return this.setNombre(unaReceta.getNombre())
			.setCantCalorias(unaReceta.getCantCalorias())
			.setDificultad(unaReceta.getDificultad())
			.setPreparacion(unaReceta.getPreparacion())
			.setTemporada(unaReceta.getTemporada())
			.setUrlImagen(unaReceta.getUrlImagen())
			.agregarTodosLosIngredientes(unaReceta.getIngredientes())
			.agregarTodasLasCondimentaciones(unaReceta.getCondimentaciones())
			.agregarTodasLasSubRecetas(unaReceta.getSubRecetas());
	}
	
	public RecetaBuilder setNombre(String unNombre) {
		nombre = unNombre;
		return this;
	}

	public RecetaBuilder setPreparacion(String unaPreparacion) {
		preparacion = unaPreparacion;
		return this;
	}

	public RecetaBuilder setUrlImagen(String unaUrlImagen) {
		urlImagen = unaUrlImagen;
		return this;
	}

	public RecetaBuilder agregarIngrediente(Ingrediente unIngrediente) {
		ingredientes.add(unIngrediente);
		return this;
	}

	public RecetaBuilder agregarTodosLosIngredientes(
			Collection<Ingrediente> unosIngredientes) {
		ingredientes.addAll(unosIngredientes);
		return this;
	}

	public RecetaBuilder agregarCondimentaciones(
			Condimentacion unaCondimentacion) {
		condimentaciones.add(unaCondimentacion);
		return this;
	}
	
	public RecetaBuilder agregarTodasLasCondimentaciones(
			Collection<Condimentacion> unasCondimentaciones) {
		condimentaciones.addAll(unasCondimentaciones);
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

	public RecetaBuilder agregarTodasLasSubRecetas(Collection<Receta> unasSubRecetas) {
		subRecetas.addAll(unasSubRecetas);
		return this;
	}
	
	public RecetaBuilder setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
		return this;
	}

	public RecetaBuilder setTemporada(Temporada temporada) {
		this.temporada = temporada;
		return this;
	}
	
	public RecetaBuilder removerIngredienteDeId(Long idIngrediente) {
		ingredientes.removeIf(ingrediente -> idIngrediente.equals(ingrediente.getId()));
		return this;
	}

	public RecetaBuilder removerCondimentacionDeId(Long idCondimentacion) {
		condimentaciones.removeIf(condimentacion -> idCondimentacion.equals(condimentacion.getId()));
		return this;
	}
	
	public RecetaBuilder removerSubReceta(Receta subReceta) {
		subRecetas.remove(subReceta);
		return this;
	}
	
	public Receta construirReceta() {
		if (!esRecetaValida()) {
			throw new RecetaNoValidaException("La Receta No es Válida!!!");
		}

		return new Receta(nombre, ingredientes, condimentaciones, subRecetas,
				cantCalorias, dificultad, temporada, preparacion, urlImagen);
	}

	private boolean esRecetaValida() {
		return tieneAlMenosUnIngrediente() && caloriasEntre(10, 5000);
	}

	private boolean tieneAlMenosUnIngrediente() {
		return !ingredientes.isEmpty();
	}

	private boolean caloriasEntre(int minimo, int maximo) {
		return cantCalorias >= minimo && cantCalorias <= maximo;
	}
}
