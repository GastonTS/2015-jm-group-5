package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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