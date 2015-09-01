package ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class NoExcedeCalorias extends PreFiltro {

	public NoExcedeCalorias(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	protected boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario) {
		return unaReceta.getCantCaloriasTotales() < 500;
	}

	@Override
	public String getNombre() {
		return "No se excede en calorías";
	}
}
