package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OrdenadosPorCriterio extends PostProcesamiento {
	@Transient
	private Comparator<Receta> criterio;//TODO persistir bien
	private String nombreCriterio;

	public OrdenadosPorCriterio(Filtro unFiltro, Comparator<Receta> unComparador, String unNombreCriterio) {
		super(unFiltro);
		criterio = unComparador;
		nombreCriterio = unNombreCriterio;
	}

	@Override
	public String getNombre() {
		return "Ordenadas por " + nombreCriterio;
	}

	@Override
	protected Collection<Receta> procesar(Collection<Receta> recetas) {
		Collections.sort((List<Receta>) recetas, criterio);
		return recetas;
	}

}