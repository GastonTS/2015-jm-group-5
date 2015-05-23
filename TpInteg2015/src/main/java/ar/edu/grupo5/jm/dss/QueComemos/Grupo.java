package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;

public class Grupo {

	private Collection<String> preferenciasAlimenticias;
	private Collection<Usuario> integrantes;

	public Grupo(Collection<String> unasPreferenciasAlimenticias,
			Collection<Usuario> unosIntegrantes) {
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		integrantes = unosIntegrantes;
	}

	public Collection<Receta> consultarRecetas() {
		Collection<Receta> resultadoConsulta = new ArrayList<Receta>();
		integrantes.forEach(unIntegrante -> resultadoConsulta
				.addAll(unIntegrante.getRecetasPropias()));
		return resultadoConsulta;
	}

	public boolean alguienTiene(Receta unaReceta) {
		return integrantes.stream().anyMatch(
				unUsuario -> unUsuario.getRecetasPropias().contains(unaReceta));
	}

	public boolean puedeSugerirse(Receta unaReceta) {
		return unaReceta.tieneAlgunIngredienteDeEstos(preferenciasAlimenticias)
				&& esApropiadaParaTodosSusIntegrantes(unaReceta);
	}

	private boolean esApropiadaParaTodosSusIntegrantes(Receta unaReceta) {
		return integrantes.stream().allMatch(
				unIntegrante -> !unIntegrante
						.sosRecetaInadecuadaParaMi(unaReceta));
	}
}
