package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public interface StPostProcesamiento {

	public Collection<Receta> procesarRecetas(Collection<Receta> unasRecetas);
}
