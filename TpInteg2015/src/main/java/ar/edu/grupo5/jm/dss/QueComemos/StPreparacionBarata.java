package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;

public class StPreparacionBarata implements StFiltro {
	private Collection<String> ingredientesCaros = Arrays.asList("lechon",
			"lomo", "salmon", "alcaparras");

	public boolean filtrar(Receta unaReceta, Usuario unUsuario) {
		return !unaReceta.tieneAlgunIngredienteDeEstos(ingredientesCaros);
	}
}