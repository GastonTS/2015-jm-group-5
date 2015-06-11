package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class MasConsultada implements ObservadorConsultas {
	Collection<Receta> recetasConsultadas = new ArrayList<Receta>();

	@Override
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		recetasConsultadas.forEach(unaReceta -> agregarUnaConsulta(unaReceta));
	}

	private void agregarUnaConsulta(Receta unaReceta) {
		recetasConsultadas.add(unaReceta);
	}

	public Optional<Receta> recetaMasConsultada() {
		return recetasConsultadas.stream().max((unNombre, otroNombre) -> cantidadDeConsultas(unNombre) - cantidadDeConsultas(otroNombre));
	}

	private int cantidadDeConsultas(Receta unaReceta) {
		return Collections.frequency(recetasConsultadas, unaReceta);
	}

	public int cantidadDeConsultasDeRecetaMAsConsultada() {
		Optional<Receta> masConsultada = recetaMasConsultada();
		return (masConsultada.isPresent()) ? Collections.frequency(recetasConsultadas, masConsultada.get()) : 0;
	}
}
