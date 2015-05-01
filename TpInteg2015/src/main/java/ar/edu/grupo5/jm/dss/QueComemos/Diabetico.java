package ar.edu.grupo5.jm.dss.QueComemos;

public class Diabetico implements CondicionPreexistente {

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.getPeso()<70 || unUsuario.tieneRutinaActiva();
	}
	
	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return unaReceta.getCondimentos().stream().anyMatch(condimento -> condimento.esCondimentoConMasDe("Azucar",100,"gr"));
	}
	
	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return unUsuario.indicaSexo() && unUsuario.tieneAlgunaPreferencia();
	}
 
}
