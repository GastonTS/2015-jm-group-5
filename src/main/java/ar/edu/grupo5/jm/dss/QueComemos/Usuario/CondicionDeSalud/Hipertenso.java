package ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class Hipertenso extends CondicionDeSalud {

	public static final Collection<String> condimentosProhibidos = Arrays.asList("sal", "caldo");

	public static Collection<String> getCondimentosProhibidos() {
		return condimentosProhibidos;
	}

	@Override
	public boolean subsanaCondicion(Usuario unUsuario) {
		return unUsuario.tieneRutinaIntensiva();
	}

	@Override
	public boolean esInadecuada(Receta unaReceta) {
		return unaReceta.tenesAlgoDe(condimentosProhibidos);
	}

	@Override
	public boolean esUsuarioValido(Usuario unUsuario) {
		return unUsuario.tieneAlgunaPreferencia();
	}

	@Override
	public String getNombre() {
		return "Hipertenso";
	}
}
