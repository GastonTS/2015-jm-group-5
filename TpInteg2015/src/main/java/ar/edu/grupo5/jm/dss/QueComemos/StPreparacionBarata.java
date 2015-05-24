package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class StPreparacionBarata implements IFiltroStrategy {

	private static final Collection<String> ingredientesCaros = Arrays.asList(
			"lechon", "lomo", "salmon", "alcaparras");

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> unasRecetas,
			Usuario unUsuario) {
		return unasRecetas
				.stream()
				.filter((unaReceta -> !unaReceta
						.tieneAlgunIngredienteDeEstos(ingredientesCaros)))
				.collect(Collectors.toList());
	}

}
