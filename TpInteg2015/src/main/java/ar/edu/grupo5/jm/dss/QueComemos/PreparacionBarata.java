package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class PreparacionBarata extends Filtro {

	private static final Collection<String> ingredientesCaros = Arrays.asList(
			"lechon", "lomo", "salmon", "alcaparras");

	public PreparacionBarata(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas) {
		Collection<Receta> recetasParciales = recetas
				.stream()
				.filter((unaReceta -> !unaReceta
						.tieneAlgunIngredienteDeEstos(ingredientesCaros)))
				.collect(Collectors.toList());

		return subFiltro.filtrarRecetas(recetasParciales);
	}

}