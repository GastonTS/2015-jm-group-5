package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class RecetaMasConsultada extends GestorCantidadConsultasRecetas implements ObservadorConsultas{
	Collection<Receta> recetasConsultadas = new ArrayList<Receta>();
	
	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> unasRecetasConsultadas) {
		
		recetasConsultadas.addAll(unasRecetasConsultadas);
	}

	public Optional<Receta> recetaMasConsultada() {
		return elementoQueMasSeRepiteEn(recetasConsultadas);
	}

	public int cantidadDeConsultasDeRecetaMasConsultada() {
		return cantidadDeElementoMasRepetidoEn(recetasConsultadas); 
	}
}
