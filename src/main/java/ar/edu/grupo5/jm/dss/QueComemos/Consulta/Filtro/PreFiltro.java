package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PreFiltro extends Filtro {

	@ManyToOne(cascade = CascadeType.ALL)
	protected Filtro subFiltro;

	public PreFiltro(Filtro unFiltro) {
		subFiltro = unFiltro;
	}

	public abstract String getNombre();

	@Override
	public Stream<String> getNombresFiltros() {
		return Stream.concat(Stream.of(this.getNombre()), subFiltro.getNombresFiltros());
	}

	protected abstract boolean cumpleCriterio(Receta unaReceta, Usuario unUsuario);

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		Collection<Receta> recetasFiltradas = recetas.stream().filter(receta -> this.cumpleCriterio(receta, unUsuario))
				.collect(Collectors.toList());
		return subFiltro.filtrarRecetas(recetasFiltradas, unUsuario);
	}

}
