package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

abstract  class GestorCantidadConsultasRecetas {


	public void añadirAColeccion(Usuario unUsuario,
			Collection<Receta> recetasConsultadas,
			Collection<Receta>  coleccion) {
	
		coleccion.addAll(recetasConsultadas); 
	}
	
	
	public Optional<Receta> elementoQueMasSeRepiteEn(ArrayList<Receta> coleccion) {
		return coleccion.stream().max((unNombre, otroNombre) -> cantidadDeConsultas(unNombre, coleccion) - cantidadDeConsultas(otroNombre, coleccion));
	}

	private int cantidadDeConsultas(Receta unaReceta, ArrayList<Receta> coleccion) {
		return Collections.frequency(coleccion, unaReceta);
	}

	public int cantidadDeElementoMasRepetidoEn(ArrayList<Receta> coleccion) {
		Optional<Receta> masConsultada = elementoQueMasSeRepiteEn(coleccion);
		return (masConsultada.isPresent()) ? Collections.frequency(coleccion, masConsultada.get()) : 0;
	}
}
