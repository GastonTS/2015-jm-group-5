package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
abstract class AcumuladorConsultas extends ObservadorConsultas {

	public abstract Collection<Receta> getRecetasConsultadas(Usuario unUsuario);

	public void notificarConsulta(Consulta unaConsulta) {
		this.getRecetasConsultadas(unaConsulta.getUsuario()).addAll(unaConsulta.getRecetasConsultadas());
	}

	public Optional<Receta> elementoQueMasSeRepiteEn(
			Collection<Receta> recetasConsultadas) {
		return recetasConsultadas.stream().max(
				(unaReceta, otraReceta) -> Integer.compare(
						cantidadDeConsultasDe(unaReceta, recetasConsultadas),
						cantidadDeConsultasDe(otraReceta, recetasConsultadas)));
	}

	protected int cantidadDeConsultasDe(Receta unaReceta,
			Collection<Receta> recetasConsultadas) {
		return Collections.frequency(recetasConsultadas, unaReceta);
	}

	protected List<Receta> recetasMasConsultadasEnOrdenDescendiente(
			Collection<Receta> recetasConsultadas) {
		Set<Receta> recetasSet = recetasConsultadas.stream().collect(
				Collectors.toSet());

		return recetasSet
				.stream()
				.sorted((r1, r2) -> Integer.compare(
						this.cantidadDeConsultasDe(r2, recetasConsultadas),
						this.cantidadDeConsultasDe(r1, recetasConsultadas)))
				.collect(Collectors.toList());
	}

	public int cantidadDeElementoMasRepetidoEn(
			Collection<Receta> recetasConsultadas) {
		Optional<Receta> masConsultada = elementoQueMasSeRepiteEn(recetasConsultadas);
		return (masConsultada.isPresent()) ? cantidadDeConsultasDe(
				masConsultada.get(), recetasConsultadas) : 0;
	}
}
