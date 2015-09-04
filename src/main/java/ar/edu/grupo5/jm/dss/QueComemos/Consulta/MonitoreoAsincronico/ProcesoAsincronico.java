package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

public abstract class ProcesoAsincronico {

	public abstract void procesarConsulta(Consulta unaConsulta);
	
	public void procesarConsultas(Collection<Consulta> unasConsultas) {
		unasConsultas.forEach(unaConsulta -> this.procesarConsulta(unaConsulta));
	}

}