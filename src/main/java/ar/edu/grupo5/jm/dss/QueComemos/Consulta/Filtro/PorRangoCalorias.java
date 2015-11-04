package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class PorRangoCalorias extends PostProcesamiento{
	Double minCalorias, maxCalorias;
	
	public PorRangoCalorias(Filtro unFiltro, String minCalorias, String maxCalorias){
		super(unFiltro);
	    
		if(!Objects.isNull(minCalorias) && !minCalorias.isEmpty())
	    	this.minCalorias = Double.parseDouble(minCalorias);
	    else
	    	this.minCalorias = 0.0;
	    
		if(!Objects.isNull(maxCalorias) && !maxCalorias.isEmpty())
			this.maxCalorias = Double.parseDouble(maxCalorias);
		else
			this.maxCalorias = 999999.9;
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
