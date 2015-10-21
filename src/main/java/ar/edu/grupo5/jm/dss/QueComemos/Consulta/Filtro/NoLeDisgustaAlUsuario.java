package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class NoLeDisgustaAlUsuario extends PreFiltro {

	public NoLeDisgustaAlUsuario(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	public String getNombre() {
		return "No me disgusta";
	}

	@Override
	protected boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario) {
		return unUsuario.noLeDisgusta(unaReceta);
	}

}