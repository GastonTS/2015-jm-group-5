package ar.edu.grupo5.jm.dss.QueComemos;

public class Celiaco implements CondicionPreexistente {

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return false;
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
