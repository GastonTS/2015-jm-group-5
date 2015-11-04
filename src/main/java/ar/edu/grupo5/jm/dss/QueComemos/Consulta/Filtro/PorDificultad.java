package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

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
		return recetas.stream().filter(unaReceta -> unaReceta.getDificultad().name() == dificultad).collect(Collectors.toList());
	}

}
