package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class MasConsultada implements ObservadorConsultas {
	Collection<String> nombreRecetasConsultadas = new ArrayList<String>();

	@Override
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		recetasConsultadas.forEach(unaReceta -> agregarUnaConsulta(unaReceta));
	}

	// VER: Qué utilizo para identificar a una Receta?? Su nombre, todo el
	// objeto en sí, otras?
	private void agregarUnaConsulta(Receta unaReceta) {
		nombreRecetasConsultadas.add(unaReceta.getNombre());
	}

	// debería agregar un getter de recetaMap para saber que
	// funciona correctamente, lo que luego se probaría en un test
	public Optional<String> nombreRecetaMasConsultada() {
		return nombreRecetasConsultadas.stream().max((unNombre, otroNombre) -> cantidadDeConsultas(unNombre) - cantidadDeConsultas(otroNombre));
	}

	private int cantidadDeConsultas(String nombreDeReceta) {
		return Collections.frequency(nombreRecetasConsultadas, nombreDeReceta);
	}

	public int cantidadDeConsultasDeRecetaMAsConsultada() {
		return Collections.frequency(nombreRecetasConsultadas, nombreRecetaMasConsultada().get());
	}
}
