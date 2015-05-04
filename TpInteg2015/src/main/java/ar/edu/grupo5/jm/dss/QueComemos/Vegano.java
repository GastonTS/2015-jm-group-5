package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;

public class Vegano implements CondicionPreexistente {
	
	private static final Collection<String> preferenciasProhibidas = 
											Arrays.asList("pollo", "chori", "carne", "chivito"); 
			
			
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
		
		return !(unUsuario.tienePreferencias(preferenciasProhibidas));//me parecio que de esta forma era más lindo
		//return !(unUsuario.tienePreferencia("pollo") || unUsuario.tienePreferencia("carne") || unUsuario.tienePreferencia("chivito")
		//		|| unUsuario.tienePreferencia("chori"));
	}

}
