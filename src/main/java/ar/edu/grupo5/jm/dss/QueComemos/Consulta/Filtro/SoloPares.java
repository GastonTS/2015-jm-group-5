package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class SoloPares extends PostProcesamiento {

	public SoloPares(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	public String getNombre() {
		return "Solo resultados pares";
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		return recetas.stream().filter(receta -> ((List<Receta>) recetas).indexOf(receta) % 2 == 1).collect(Collectors.toList());
	}

}
