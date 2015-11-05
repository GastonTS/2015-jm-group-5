package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import javax.persistence.Entity;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

@Entity
public class ConsultadasAFavoritas extends ProcesoAsincronico {
	

	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		unaConsulta.getRecetasConsultadas().forEach(unaReceta -> unaConsulta.getUsuario().agregarAFavorita(unaReceta));
	}
}
