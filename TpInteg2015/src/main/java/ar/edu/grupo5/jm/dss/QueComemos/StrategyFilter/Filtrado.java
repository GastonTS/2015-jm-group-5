package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Filtrado {
	private List<Filtro> filtros;
	private List<PostProcesamiento> postProcesamientos;

	public Filtrado(List<Filtro> unosFiltros, List<PostProcesamiento> unosPostProcesamientos) {
		filtros = unosFiltros;
		postProcesamientos = unosPostProcesamientos;
	}

	public Collection<Receta> aplicar(Collection<Receta> unasRecetas, Usuario unUsuario) {
		return aplicarPostProcesamiento(aplicarFiltros(unasRecetas, unUsuario));
	}

	public Collection<Receta> aplicarFiltros(Collection<Receta> unasRecetas, Usuario unUsuario) {
		return unasRecetas.stream().filter(unaReceta -> cumpleTodosLosCriterios(unaReceta, unUsuario)).collect(Collectors.toList());
	}
	
	public boolean cumpleTodosLosCriterios(Receta unaReceta, Usuario unUsuario) {
		return filtros.stream().allMatch(filtro -> filtro.filtrar(unaReceta, unUsuario));
	}

	public Collection<Receta> aplicarPostProcesamiento(Collection<Receta> unasRecetas) {
		Collection<Receta> recetasRestantes = unasRecetas;

		for (PostProcesamiento procesamiento : postProcesamientos) {
			recetasRestantes = procesamiento.procesarRecetas(recetasRestantes);
		}

		return recetasRestantes;
	}
}
