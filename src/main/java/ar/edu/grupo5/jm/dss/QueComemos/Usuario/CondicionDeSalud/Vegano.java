package ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class Vegano extends CondicionDeSalud {

	public static final Collection<Ingrediente> preferenciasProhibidas = Arrays.asList(new Ingrediente("pollo"), new Ingrediente("chori"),
			new Ingrediente("carne"), new Ingrediente("chivito"));

	public static Collection<Ingrediente> getPreferenciasProhibidas() {
		return preferenciasProhibidas;
	}

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.tienePreferencia(new Ingrediente("fruta"));
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return unaReceta.tenesAlgunIngredienteDeEstos(preferenciasProhibidas);
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return !(unUsuario.tieneAlgunaDeEstasPreferencias(preferenciasProhibidas));
	}

	@Override
	public boolean esCondicionVegana() {
		return true;
	}

}
