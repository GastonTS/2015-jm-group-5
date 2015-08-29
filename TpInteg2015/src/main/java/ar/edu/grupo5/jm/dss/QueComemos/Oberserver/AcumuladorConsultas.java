package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

abstract  class AcumuladorConsultas implements ObservadorConsultas {

	public abstract Collection<Receta> getRecetasConsultadas(Usuario unUsuario);
	
	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> nuevasRecetasConsultadas) {
		this.getRecetasConsultadas(unUsuario).addAll(nuevasRecetasConsultadas);
	}
	
	
	public Optional<Receta> elementoQueMasSeRepiteEn(Collection<Receta> recetasConsultadas) {
		return recetasConsultadas.stream().max(
				(unaReceta, otraReceta) -> Integer.compare(cantidadDeConsultasDe(unaReceta, recetasConsultadas),
						cantidadDeConsultasDe(otraReceta, recetasConsultadas)));
	}

	protected int cantidadDeConsultasDe(Receta unaReceta, Collection<Receta> recetasConsultadas) {
		return Collections.frequency(recetasConsultadas, unaReceta);
	}

	public int cantidadDeElementoMasRepetidoEn(Collection<Receta> recetasConsultadas) {
		Optional<Receta> masConsultada = elementoQueMasSeRepiteEn(recetasConsultadas);
		return (masConsultada.isPresent()) ? cantidadDeConsultasDe(masConsultada.get(),recetasConsultadas) : 0;
	}
}
