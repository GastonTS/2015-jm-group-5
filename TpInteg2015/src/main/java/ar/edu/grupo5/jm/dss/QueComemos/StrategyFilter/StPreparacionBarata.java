package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.Arrays;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class StPreparacionBarata implements StFiltro {
	private Collection<String> ingredientesCaros = Arrays.asList("lechon", "lomo", "salmon", "alcaparras");

	public boolean filtrar(Receta unaReceta, Usuario unUsuario) {
		return !unaReceta.tieneAlgunIngredienteDeEstos(ingredientesCaros);
	}
}