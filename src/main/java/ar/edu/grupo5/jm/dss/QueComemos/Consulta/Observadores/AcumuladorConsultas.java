package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class AcumuladorConsultas extends ObservadorConsultas {

	public abstract Collection<Receta> getRecetasConsultadas(Usuario unUsuario);

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
		return (masConsultada.isPresent()) ? cantidadDeConsultasDe(masConsultada.get(), recetasConsultadas) : 0;
	}
}
