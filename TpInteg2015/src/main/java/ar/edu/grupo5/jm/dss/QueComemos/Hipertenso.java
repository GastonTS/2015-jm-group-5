package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;

public class Hipertenso implements CondicionPreexistente {
	
	static Condimento sal = new Condimento("Sal");
	static Condimento caldo = new Condimento("Caldo");
	private static final Collection<Condimento> condimentosProhibidos = Arrays.asList(sal, caldo);
	
	public static Collection<Condimento> getCondimentosProhibidos() {
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

}
