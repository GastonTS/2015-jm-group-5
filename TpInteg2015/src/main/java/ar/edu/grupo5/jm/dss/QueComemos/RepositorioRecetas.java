package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class RepositorioRecetas {

	static private Collection<Receta> recetasTotales = new ArrayList<Receta>();

	static public void setRecetasTotales(Collection<Receta> unasRecetas) {
		recetasTotales = unasRecetas;
	}

	public void agregarReceta(Receta unaReceta) {
		recetasTotales.add(unaReceta);
	}

	public void quitarReceta(Receta unaReceta) {
		recetasTotales.remove(unaReceta);
	}

	public Collection<Receta> listarTodasPuedeAcceder(Usuario unUsuario) {
		return recetasTotales.stream()
				.filter(receta -> unUsuario.puedeAccederA(receta))
				.collect(Collectors.toSet());
	}
}
