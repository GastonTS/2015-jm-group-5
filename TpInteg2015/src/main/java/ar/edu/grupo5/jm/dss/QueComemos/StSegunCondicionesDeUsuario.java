package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class StSegunCondicionesDeUsuario implements IFiltroStrategy {

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> unasRecetas,
			Usuario unUsuario) {
		return unasRecetas
				.stream()
				.filter((unaReceta -> !unUsuario
						.sosRecetaInadecuadaParaMi(unaReceta)))
				.collect(Collectors.toList());
	}

}
