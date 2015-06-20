package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.Collection;

public class BufferDeConsultas {

	public static BufferDeConsultas instancia = new BufferDeConsultas();
	private Collection<Consulta> consultasRealizadas;
	private Collection<ProcesoAsincronico> procesosARealizar;

	public void agregarConsulta(Consulta unaConsulta) {
		consultasRealizadas.add(unaConsulta);
	}

	public void procesarConsultas() {
		for (ProcesoAsincronico proceso : procesosARealizar) {
			consultasRealizadas.forEach(unaConsulta -> proceso.procesarConsulta(unaConsulta));
		}
	}
}
