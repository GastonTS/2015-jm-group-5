package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SinFiltro extends Filtro {

	public Stream<String> getNombresFiltros() {
		return Stream.empty();
	}

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		return recetas;
	}

}
