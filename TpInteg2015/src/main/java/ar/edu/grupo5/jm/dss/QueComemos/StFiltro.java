package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StFiltro {

	private List<IFiltroStrategy> filtros;
	private List<IFiltroStrategy> postProcesamientos;

	public StFiltro(List<IFiltroStrategy> unosFiltros,
			List<IFiltroStrategy> unosPostProcesamientos) {
		filtros = (unosFiltros != null) ? unosFiltros
				: new ArrayList<IFiltroStrategy>();
		postProcesamientos = (unosPostProcesamientos != null) ? unosPostProcesamientos
				: new ArrayList<IFiltroStrategy>();
	}

	public Collection<Receta> aplicarFiltros(Collection<Receta> unasRecetas,
			Usuario unUsuario) {
		Collection<Receta> recetasRestantes = unasRecetas;

		for (IFiltroStrategy filtro : filtros) {
			recetasRestantes = filtro.filtrarRecetas(recetasRestantes,
					unUsuario);
		}

		for (IFiltroStrategy procesamiento : postProcesamientos) {
			recetasRestantes = procesamiento.filtrarRecetas(recetasRestantes,
					unUsuario);
		}

		return recetasRestantes;
	}

}
