package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

@Entity
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
		List<Receta> recetasParciales = recetas.stream().collect(Collectors.toList());
		
		return recetasParciales.stream().filter(receta -> recetasParciales.indexOf(receta) % 2 == 1).collect(Collectors.toList());
	}

}
