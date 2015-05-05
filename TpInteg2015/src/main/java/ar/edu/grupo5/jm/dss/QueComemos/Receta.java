package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class Receta {
	private String nombre;
	private Collection<String> ingredientes;
	private Collection<Condimento> condimentos;
	private Collection<Receta> subRecetas;
	private String proceso;
	private String temporada;
	private String dificultad;
	private double cantCalorias;
	
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
		//usar isBetween o algo similar
		return cantCalorias >= minimo && cantCalorias <= maximo;
	}
	
	public boolean contieneSubingrediente(String unSubingrediente) {
		return ingredientes.contains(unSubingrediente);
	}
	
	public Collection <Condimento> getCondimentos() {
		return condimentos;
	}


	public boolean tenesAlgoDe(Collection<Condimento> condimentosProhibidos) {
		return condimentos.stream().anyMatch(condimento -> condimento.sosIgualAUnoDe(condimentosProhibidos));
	}
		//buscar abstraccion para stream().anyMatch() . tenesAlgoDe y tenesMasDe son muy similares

	public boolean tenesMasDe(Condimento unCondimento) {
		return condimentos.stream().anyMatch(condimento -> condimento.mayorCantidadQue(unCondimento));
	}


	public boolean tenesAlgunIngredienteDeEstos(Collection<String> ingredientesProhibidas) {
		//return !ingredientes.retainAll(ingredientesProhibidas).isEmpty(); el metodo no se porque mierda no anda debería hacer
		// la concatenacion de ingredientes con ingredientes prohibidos y si es empty lo niega porque no tiene de esos ingredientes
		return false;
	}

	
}

