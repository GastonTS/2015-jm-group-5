package ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class GestorDeConsultas {
	private List<StFiltro> filtros;
	private List<StPostProcesamiento> postProcesamientos;

	public GestorDeConsultas(List<StFiltro> unosFiltros, List<StPostProcesamiento> unosPostProcesamientos) {
		filtros = unosFiltros;
		postProcesamientos = unosPostProcesamientos;
	}

	public Collection<Receta> aplicar(Collection<Receta> unasRecetas, Usuario unUsuario) {
		return aplicarPostProcesamiento(aplicarFiltros(unasRecetas, unUsuario));
	}

	public Collection<Receta> aplicarFiltros(Collection<Receta> unasRecetas, Usuario unUsuario) {
		Collection<Receta> recetasRestantes = unasRecetas;

		for (StFiltro filtro : filtros) {
			recetasRestantes = recetasRestantes.stream().filter(unaReceta -> filtro.filtrar(unaReceta, unUsuario))
					.collect(Collectors.toList());
		}

		return recetasRestantes;
	}

	public Collection<Receta> aplicarPostProcesamiento(Collection<Receta> unasRecetas) {
		Collection<Receta> recetasRestantes = unasRecetas;

		for (StPostProcesamiento procesamiento : postProcesamientos) {
			recetasRestantes = procesamiento.procesarRecetas(recetasRestantes);
		}

		return recetasRestantes;
	}
}
