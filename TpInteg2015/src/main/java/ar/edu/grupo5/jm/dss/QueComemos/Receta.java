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
	
	//public Collection<CondicionPreexistente> esInadecuadaPara() {
//		return;
//	}
	
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
		return ingredientes.stream().anyMatch(ingrediente -> ingrediente.contains(unSubingrediente));
	}
	
	public Collection <Condimento> getCondimentos() {
		return condimentos;
	}
}
