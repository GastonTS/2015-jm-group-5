package ar.edu.grupo5.jm.dss.QueComemos;

public interface CondicionPreexistente {

	public boolean subsanaCondicion(Usuario unUsuario);
	public boolean esInadecuada(Receta unaReceta);
	public boolean esUsuarioValido(Usuario unUsuario);
	
}
