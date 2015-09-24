package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Arrays;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class PreparacionBarata extends PreFiltro {

	private static final Collection<Ingrediente> ingredientesCaros = Arrays.asList(new Ingrediente("lechon"), new Ingrediente("lomo"),
			new Ingrediente("salmon"), new Ingrediente("alcaparras"));

	public PreparacionBarata(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	public String getNombre() {
		return "Tiene preparación barata";
	}

	@Override
	protected boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario) {
		return !unaReceta.tenesAlgunIngredienteDeEstos(ingredientesCaros);
	}

}