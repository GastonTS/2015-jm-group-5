package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class Receta {
	private String nombre;
	private Collection<String> ingredientes;
	private Collection<Condimento> condimentos;
	private String proceso;
	private double cantCalorias;
	private String dificultad;
	private String temporada;
	private Collection<Receta> subRecetas;
	
	public Receta(String unNombre,Collection<String> unosIngredientes, Collection<Condimento> unosCondimentos,
			Collection<Receta> unassubRecetas, String unProceso, String unaTemporada, String unaDificultad,
			double unasCantCalorias) {
		nombre = unNombre;
		ingredientes = unosIngredientes;
		condimentos = unosCondimentos;
		subRecetas = unassubRecetas;
		proceso = unProceso;
		temporada = unaTemporada;
		dificultad = unaDificultad;
		cantCalorias = unasCantCalorias;
		
	}
	
	public boolean esValida(){
		return tieneAlMenosUnIngrediente() && totalCaloriasEntre(10, 5000);
	}
	
	private boolean tieneAlMenosUnIngrediente(){
		return !ingredientes.isEmpty();
	}
	
	private boolean totalCaloriasEntre(int minimo, int maximo){
		return cantCalorias >= minimo && cantCalorias <= maximo;
	}
	
	
	public Collection <Condimento> getCondimentos() {
		return condimentos;
	}


	public boolean tenesAlgoDe(Collection<String> condimentosProhibidos) {
		return condimentos.stream().anyMatch(condimento -> condimento.sosIgualAUnoDe(condimentosProhibidos));
	}

	public boolean tenesMasDe(Condimento unCondimento) {
		return condimentos.stream().anyMatch(condimento -> condimento.mayorCantidadQue(unCondimento));
	}


	public boolean tenesAlgunIngredienteDeEstos(Collection<String> ingredientesProhibidas) {
				
		Collection<String> interseccionIngredientes = ingredientes;
		interseccionIngredientes.retainAll(ingredientesProhibidas);
		
		return !interseccionIngredientes.isEmpty();
	}
	
	public boolean estasEnEstasRecetas(Collection<Receta> unasRecetas) {
		return unasRecetas.stream().anyMatch(unaReceta -> unaReceta.getNombre().equals(this.getNombre()));
	}

	public String getNombre() {
		return nombre;
	}

	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void agregarSubRecetas(Collection<Receta> unasSubRecetas){
		subRecetas.addAll(unasSubRecetas);
	}
	
	public boolean subrecetasIncluye(Receta unaReceta) {
		return subRecetas.contains(unaReceta);
	}
}

