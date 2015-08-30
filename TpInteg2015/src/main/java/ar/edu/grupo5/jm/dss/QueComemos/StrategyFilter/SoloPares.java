package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class StSoloPares implements StPostProcesamiento {

	@Override
	public Collection<Receta> procesarRecetas(Collection<Receta> unasRecetas) {
		return unasRecetas.stream().filter(receta -> ((List<Receta>) unasRecetas).indexOf(receta) % 2 == 1).collect(Collectors.toList());
	}

}
