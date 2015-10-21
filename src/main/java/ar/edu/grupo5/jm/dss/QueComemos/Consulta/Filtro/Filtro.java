package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
@Entity
public abstract class Filtro {
	public abstract Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario);

	public abstract Stream<String> getNombresFiltros();

}
