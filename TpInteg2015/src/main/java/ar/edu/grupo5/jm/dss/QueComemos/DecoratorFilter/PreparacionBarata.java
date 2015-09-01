package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import java.util.Arrays;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class PreparacionBarata extends PreFiltro {

	private static final Collection<String> ingredientesCaros = Arrays.asList("lechon", "lomo", "salmon", "alcaparras");

	public PreparacionBarata(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario) {
		return !unaReceta.tenesAlgunIngredienteDeEstos(ingredientesCaros);
	}
	
	@Override
	public String getNombre() {
		return "Tiene preparación barata";
	}
}