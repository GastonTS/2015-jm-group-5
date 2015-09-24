package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public abstract class PostProcesamiento implements Filtro {

	protected Filtro subFiltro;

	public PostProcesamiento(Filtro unFiltro) {
		subFiltro = unFiltro;
	}

	public abstract String getNombre();

	@Override
	public Stream<String> getNombresFiltros() {
		return Stream.concat(subFiltro.getNombresFiltros(), Stream.of(this.getNombre()));
	}

	protected abstract Collection<Receta> procesar(Collection<Receta> recetas);

	@Override
	public Collection<Receta> filtrarRecetas(Collection<Receta> recetas, Usuario unUsuario) {
		List<Receta> recetasParciales = (List<Receta>) subFiltro.filtrarRecetas(recetas, unUsuario);

		return procesar(recetasParciales);
	}

}
