package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;

public class PorDificultad extends PostProcesamiento{
	String dificultad;
	public PorDificultad(Filtro unFiltro, String unaDificultad){
		super(unFiltro);
		dificultad = unaDificultad;
	}

	@Override
	public String getNombre() {
		return "Consulta por la dificultad: " + dificultad;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
	    if (!Objects.isNull(dificultad) && !dificultad.isEmpty())
	    	return recetas.stream().filter(unaReceta -> unaReceta.getDificultad() == Dificultad.valueOf(dificultad)).collect(Collectors.toList());
	    else
	    	return recetas;
	}

}
