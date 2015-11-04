package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class PorNombre extends PostProcesamiento{
	String nombre;
	
	public PorNombre(Filtro unFiltro, String unNombre) {
		super(unFiltro);
		nombre = unNombre;
	}

	@Override
	public String getNombre() {
		return "Consulta por nombre " + nombre;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		return recetas.stream().filter(unaReceta -> unaReceta.getNombre() == nombre).collect(Collectors.toList());
	}

	
}
