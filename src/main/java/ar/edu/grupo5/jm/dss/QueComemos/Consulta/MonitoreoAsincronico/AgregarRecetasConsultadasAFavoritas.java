package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AgregarRecetasConsultadasAFavoritas extends ProcesoAsincronico {
	

	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		unaConsulta.getRecetasConsultadas().forEach(unaReceta -> unaConsulta.getUsuario().agregarAFavorita(unaReceta));
	}
}
