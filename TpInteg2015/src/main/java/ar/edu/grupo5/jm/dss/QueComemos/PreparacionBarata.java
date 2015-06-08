package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class PreparacionBarata extends PreProcesamiento {

	private static final Collection<String> ingredientesCaros = Arrays.asList("lechon", "lomo", "salmon", "alcaparras");

	public PreparacionBarata(IFiltro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected Predicate<Receta> setCriterio() {
		return (unaReceta -> !unaReceta.tieneAlgunIngredienteDeEstos(ingredientesCaros));
	}
}