package ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Celiaco extends CondicionDeSalud {

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return true;
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return false;
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return true;
	}
	
}
