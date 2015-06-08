package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Vegano;

public class ConsultaVeganoRecetasDificles implements ObservadorConsultas {

	private int cantidadConsultas = 0;

	@Override
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas) {

		if (unUsuario.tieneCondicionDeSalud(new Vegano())) {

			Collection<Receta> consultasFiltradas = this.filtrarRecetasDificiles(recetasConsultadas);
			cantidadConsultas = cantidadConsultas + consultasFiltradas.size();
		}
	}

	public Collection<Receta> filtrarRecetasDificiles(Collection<Receta> unasRecetas) {

		// Collection<Receta> recetasParciales = unasRecetas
		// .stream().filter((unaReceta -> unaReceta.esRecetaDificl()))
		// .collect(Collectors.toList());

		return unasRecetas;
	}

}
