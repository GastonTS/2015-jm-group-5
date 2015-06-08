package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class StPrimeros10 implements StPostProcesamiento {

	@Override
	public Collection<Receta> procesarRecetas(Collection<Receta> unasRecetas) {
		return unasRecetas.stream().limit(10).collect(Collectors.toList());
	}

}
