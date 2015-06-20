package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.ArrayList;
import java.util.Collection;

public class BufferDeConsultas {

	public static BufferDeConsultas instancia = new BufferDeConsultas();
	private Collection<Consulta> consultasRealizadas = new ArrayList<Consulta>();
	private Collection<ProcesoAsincronico> procesosARealizar = new ArrayList<ProcesoAsincronico>();

	public void agregarConsulta(Consulta unaConsulta) {
		consultasRealizadas.add(unaConsulta);
	}

	public void procesarConsultas() {
		for (ProcesoAsincronico proceso : procesosARealizar) {
			consultasRealizadas.forEach(unaConsulta -> proceso.procesarConsulta(unaConsulta));
		}
	}
}
