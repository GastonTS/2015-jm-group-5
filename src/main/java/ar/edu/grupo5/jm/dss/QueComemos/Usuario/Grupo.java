package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class Grupo {

	private Collection<String> preferenciasAlimenticias;
	private Collection<Usuario> integrantes;

	public Grupo(Collection<String> unasPreferenciasAlimenticias, Collection<Usuario> unosIntegrantes) {
		preferenciasAlimenticias = unasPreferenciasAlimenticias;
		integrantes = unosIntegrantes;
	}

	public void añadirIntegrante(Usuario unUsuario) {
		integrantes.add(unUsuario);
	}

	public boolean alguienTiene(Receta unaReceta) {
		return integrantes.stream().anyMatch(unUsuario -> unaReceta.esElDueño(unUsuario));
	}

	public boolean puedeSugerirse(Receta unaReceta) {
		return (alguienTiene(unaReceta) 
				|| unaReceta.esPublica()) 
				&& unaReceta.tenesAlgunIngredienteDeEstos(preferenciasAlimenticias)
				&& esApropiadaParaTodosSusIntegrantes(unaReceta);
	}

	private boolean esApropiadaParaTodosSusIntegrantes(Receta unaReceta) {
		return integrantes.stream().allMatch(unIntegrante -> !unIntegrante.sosRecetaInadecuadaParaMi(unaReceta));
	}
}
