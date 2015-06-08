package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class StPrimeros10 implements StPostProcesamiento {

	@Override
	public Collection<Receta> procesarRecetas(Collection<Receta> unasRecetas) {
		return unasRecetas.stream().limit(10).collect(Collectors.toList());
	}

}
