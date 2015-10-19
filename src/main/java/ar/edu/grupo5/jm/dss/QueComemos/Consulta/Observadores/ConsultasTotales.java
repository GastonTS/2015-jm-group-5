package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ConsultasTotales extends AcumuladorConsultas {
	
	@ManyToMany
	Collection<Receta> recetasConsultadas = new ArrayList<Receta>();

	@Override
	public Collection<Receta> getRecetasConsultadas(Usuario unUsuario) {
		return recetasConsultadas;
	}

	public Optional<Receta> recetaMasConsultada() {
		return elementoQueMasSeRepiteEn(recetasConsultadas);
	}

	public int cantidadDeConsultasDeRecetaMasConsultada() {
		return cantidadDeElementoMasRepetidoEn(recetasConsultadas);
	}
	
}
