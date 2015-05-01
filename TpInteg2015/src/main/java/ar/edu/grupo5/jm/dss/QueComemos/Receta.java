package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class Receta {
	private String nombre;
	private Collection<String> ingredientes;
	private Collection<String> condimentos;
	private Collection<Receta> subRecetas;
	private String proceso;
	private String temporada;
	private String dificultad;
	private double cantCalorias;
	
	public Receta(String unNombre,Collection<String> unosIngredientes, Collection<String> unosCondimentos,
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
		return calorias() >= minimo && calorias() <= maximo;
	}
	
	//Esto en realidad se calcula de alguna forma. No se como hacerlo por ahora
	public double calorias(){
		return this.cantCalorias;
	}
	public void calorias(double unasCalorias){
		this.cantCalorias = unasCalorias;
	}
}
