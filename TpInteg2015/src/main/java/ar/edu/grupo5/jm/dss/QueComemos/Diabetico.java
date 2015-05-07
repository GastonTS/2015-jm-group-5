package ar.edu.grupo5.jm.dss.QueComemos;

public class Diabetico implements CondicionDeSalud {
	private static Condimento condimentoProhibido = new Condimento("Azucar",100);
	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.getPeso()<70 || unUsuario.tieneRutinaActiva();
	}
	
	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return unaReceta.tenesMasDe(condimentoProhibido);
	}
	
	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return unUsuario.indicaSexo() && unUsuario.tieneAlgunaPreferencia();
	}

	public static Condimento GetCondimentosProhibidos() {
		return condimentoProhibido;
	}
 
}
