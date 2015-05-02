package ar.edu.grupo5.jm.dss.QueComemos;

public class Vegano implements CondicionPreexistente {

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.tienePreferencia("fruta");
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return false;
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return !(unUsuario.tienePreferencia("pollo") || unUsuario.tienePreferencia("carne") || unUsuario.tienePreferencia("chivito")
				|| unUsuario.tienePreferencia("chori"));
	}

}
