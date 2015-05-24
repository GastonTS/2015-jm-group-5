package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class StPrimeros10 implements IFiltroStrategy {

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> unasRecetas,
			Usuario unUsuario) {
		return unasRecetas.stream().limit(10).collect(Collectors.toList());
	}

}
