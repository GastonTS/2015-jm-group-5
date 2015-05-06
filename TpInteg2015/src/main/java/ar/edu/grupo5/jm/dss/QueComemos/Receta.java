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
	
	public boolean contieneSubingrediente(String unSubingrediente) {
		return ingredientes.contains(unSubingrediente);
	}
	
	public Collection <Condimento> getCondimentos() {
		return condimentos;
	}


	public boolean tenesAlgoDe(Collection<String> condimentosProhibidos) {
		return condimentos.stream().anyMatch(condimento -> condimento.sosIgualAUnoDe(condimentosProhibidos));
	}
		//buscar abstraccion para stream().anyMatch() . tenesAlgoDe y tenesMasDe son muy similares

	public boolean tenesMasDe(Condimento unCondimento) {
		return condimentos.stream().anyMatch(condimento -> condimento.mayorCantidadQue(unCondimento));
	}


	public boolean tenesAlgunIngredienteDeEstos(Collection<String> ingredientesProhibidas) {
				
		Collection<String> interseccionIngredientes = ingredientes;
		interseccionIngredientes.retainAll(ingredientesProhibidas);
		
		return !interseccionIngredientes.isEmpty();
	}

	
}

