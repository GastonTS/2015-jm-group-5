package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;


public class AgregarRecetasAFavoritas implements ProcesoAsincronico {

	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		for (Receta unaReceta : unaConsulta.getRecetasConsultadas()){
			unaConsulta.getUsuario().agregarAFavorita(unaReceta);
		}

	}
}
