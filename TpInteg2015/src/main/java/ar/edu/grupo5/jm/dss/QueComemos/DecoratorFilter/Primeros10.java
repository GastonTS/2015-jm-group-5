package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class Primeros10 extends PostProcesamiento {

	public Primeros10(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		return recetas.stream().limit(10).collect(Collectors.toList());
	}

}