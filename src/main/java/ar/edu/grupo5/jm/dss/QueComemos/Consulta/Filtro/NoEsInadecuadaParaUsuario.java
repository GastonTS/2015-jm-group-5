package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class NoEsInadecuadaParaUsuario extends PreFiltro {

	public NoEsInadecuadaParaUsuario(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	public String getNombre() {
		return "No es inadecuada para mi";
	}

	@Override
	protected boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario) {
		return !unUsuario.sosRecetaInadecuadaParaMi(unaReceta);
	}

}
