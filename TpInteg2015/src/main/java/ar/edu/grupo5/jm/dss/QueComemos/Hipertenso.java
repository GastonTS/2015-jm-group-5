package ar.edu.grupo5.jm.dss.QueComemos;

public class Hipertenso implements CondicionPreexistente {

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.tieneRutinaIntensiva();
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return false;
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return unUsuario.tieneAlgunaPreferencia();
	}

}