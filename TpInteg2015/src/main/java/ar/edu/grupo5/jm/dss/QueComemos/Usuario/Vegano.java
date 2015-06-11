package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.Arrays;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class Vegano extends CondicionDeSalud {

	public static final Collection<String> preferenciasProhibidas = Arrays.asList("pollo", "chori", "carne", "chivito");

	public static Collection<String> getPreferenciasProhibidas() {
		return preferenciasProhibidas;
	}

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.tienePreferencia("fruta");
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return unaReceta.tieneAlgunIngredienteDeEstos(preferenciasProhibidas);
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return !(unUsuario.tieneAlgunaDeEstasPreferencias(preferenciasProhibidas));
	}

	@Override
	public void informarCondicion(Collection<ObservadorConsultas> observadores, Collection<Receta> recetas, Usuario unUsuario) {
		for (ObservadorConsultas observador : observadores) {
			observador.notificarVegano(unUsuario, recetas);
		}
	}

}
