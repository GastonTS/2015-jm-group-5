package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

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

	@Override
	public boolean deboNotificar() {
		return false;
	}
}
