package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class PorTemporada extends PostProcesamiento{
	String temporada;
	
	public PorTemporada(Filtro unFiltro, String unaTemporada){
		super(unFiltro);
		temporada = unaTemporada;
	}

	@Override
	public String getNombre() {
		return "Consulta por la temporada: " + temporada;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		return recetas.stream().filter(unaReceta -> unaReceta.getTemporada().name() == temporada).collect(Collectors.toList());
	}
	
}
