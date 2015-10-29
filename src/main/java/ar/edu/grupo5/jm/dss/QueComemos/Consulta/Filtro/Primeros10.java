package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

@Entity
public class Primeros10 extends PostProcesamiento {

	public Primeros10(Filtro unFiltro) {
		super(unFiltro);
	}

	@Override
	public String getNombre() {
		return "Primeros 10 resultados";
	}
	
	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		return recetas.stream().limit(10).collect(Collectors.toList());
	}
	
}