package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class PorRangoCalorias extends PostProcesamiento{
	Double minCalorias, maxCalorias;
	
	public PorRangoCalorias(Filtro unFiltro, Double minCalorias, Double maxCalorias){
		super(unFiltro);
		this.minCalorias = minCalorias;
		this.maxCalorias = maxCalorias;
	}

	@Override
	public String getNombre() {
		return "Consulta por calorías entre " + minCalorias + " y " + maxCalorias;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		return recetas.stream().filter(unaReceta -> unaReceta.getCantCaloriasTotales() < maxCalorias && 
				unaReceta.getCantCaloriasTotales() > minCalorias).collect(Collectors.toList());
	}
}
