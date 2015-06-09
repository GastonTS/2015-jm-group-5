package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class MasConsultada implements ObservadorConsultas {
	Collection<Receta> nombreRecetasConsultadas = new ArrayList<Receta>();

	@Override
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		recetasConsultadas.forEach(unaReceta -> agregarUnaConsulta(unaReceta));
	}

	private void agregarUnaConsulta(Receta unaReceta) {
		nombreRecetasConsultadas.add(unaReceta);
	}

	public Optional<Receta> nombreRecetaMasConsultada() {
		return nombreRecetasConsultadas.stream().max((unNombre, otroNombre) -> cantidadDeConsultas(unNombre) - cantidadDeConsultas(otroNombre));
	}

	private int cantidadDeConsultas(Receta nombreDeReceta) {
		return Collections.frequency(nombreRecetasConsultadas, nombreDeReceta);
	}

	public int cantidadDeConsultasDeRecetaMAsConsultada() {
		return Collections.frequency(nombreRecetasConsultadas, nombreRecetaMasConsultada().get());
	}
}
