package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Filtro {
	
	@Id
	@GeneratedValue
	private Long filtroId;
	
	public abstract Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario);

	public abstract Stream<String> getNombresFiltros();

}
