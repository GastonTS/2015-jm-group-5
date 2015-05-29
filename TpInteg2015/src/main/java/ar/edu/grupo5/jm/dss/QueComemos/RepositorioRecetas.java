package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class RepositorioRecetas { //FIXME renombrar a recetario
	
	public static RepositorioRecetas instancia = new RepositorioRecetas();

	//FIXME deberia ser de instancia
	private static Collection<Receta> recetasTotales = new ArrayList<Receta>();

	public static void setRecetasTotales(Collection<Receta> unasRecetas) {
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
