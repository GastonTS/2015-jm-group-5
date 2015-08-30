package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public interface Filtro {

	public boolean filtrar(Receta unaReceta, Usuario unUsuario);
}
