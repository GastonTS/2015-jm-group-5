package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Filtrado {

	private List<IFiltroStrategy> filtros;
	
	public Filtrado(List<IFiltroStrategy> unosFiltros) {
		if(filtros == null) {
			filtros = new ArrayList<IFiltroStrategy>();
		} else {
			filtros = unosFiltros;
		}
	}
	
	public void agregarFiltro(IFiltroStrategy unFiltro) {
		filtros.add(unFiltro);
	}
	
	public void quitarFiltro(IFiltroStrategy unFiltro) {
		filtros.remove(unFiltro);
	}
	
	public Collection<Receta> aplicarFiltros(Collection<Receta> unasRecetas, Usuario unUsuario) {
		Collection<Receta> recetasRestantes = new ArrayList<Receta>();
	
		for (IFiltroStrategy filtro : filtros) {
			recetasRestantes = filtro.filtrarRecetas(recetasRestantes,unUsuario);
		}
		
		return recetasRestantes;
	}
	
	
}
