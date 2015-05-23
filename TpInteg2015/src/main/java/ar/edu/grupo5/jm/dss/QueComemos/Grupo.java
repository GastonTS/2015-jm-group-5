package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class Grupo {

	private String nombre;
	private Collection<String> preferenciasAlimenticias;
	private Collection<Usuario> integrantes;

	public Grupo(String unNombre,
			Collection<String> unasPreferenciasAlimenticias,
			Collection<Usuario> unosIntegrantes) {
		nombre = unNombre;
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		integrantes = unosIntegrantes;
	}

	public boolean puedeSugerirse(Receta unaReceta) {
		return unaReceta.tieneAlgunIngredienteDeEstos(preferenciasAlimenticias)
				&& esApropiadaParaTodosSusIntegrantes(unaReceta);
	}

	private boolean esApropiadaParaTodosSusIntegrantes(Receta unaReceta) {
		return integrantes.stream().allMatch(
				unIntegrante -> unIntegrante
						.sosRecetaInadecuadaParaMi(unaReceta));
	}
}
