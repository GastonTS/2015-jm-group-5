package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

abstract  class GestorCantidadConsultasRecetas {


	public Optional<Receta> elementoQueMasSeRepiteEn(Collection<Receta> recetasConsultadas) {
		return recetasConsultadas.stream().max((unaReceta, otraReceta) -> cantidadDeConsultas(unaReceta, recetasConsultadas) - cantidadDeConsultas(otraReceta, recetasConsultadas));
	}

	private int cantidadDeConsultas(Receta unaReceta, Collection<Receta> recetasConsultadas) {
		return Collections.frequency(recetasConsultadas, unaReceta);
	}

	public int cantidadDeElementoMasRepetidoEn(Collection<Receta> recetasConsultadas) {
		Optional<Receta> masConsultada = elementoQueMasSeRepiteEn(recetasConsultadas);
		return (masConsultada.isPresent()) ? cantidadDeConsultas(masConsultada.get(),recetasConsultadas) : 0;
	}
}
