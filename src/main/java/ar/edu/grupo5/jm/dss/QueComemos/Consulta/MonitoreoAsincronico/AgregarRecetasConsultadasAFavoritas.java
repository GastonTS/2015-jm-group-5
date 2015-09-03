package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

public class AgregarRecetasConsultadasAFavoritas extends ProcesoAsincronico {

	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		unaConsulta.getRecetasConsultadas().forEach(unaReceta -> unaConsulta.getUsuario().agregarAFavorita(unaReceta));
	}
}
