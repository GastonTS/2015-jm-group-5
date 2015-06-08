package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.function.Predicate;

public class ExcesoDeCalorias extends PreProcesamiento {

	public ExcesoDeCalorias(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected Predicate<Receta> setCriterio() {
		return (unaReceta -> unaReceta.getCantCaloriasTotales() < 500);
	}
}
