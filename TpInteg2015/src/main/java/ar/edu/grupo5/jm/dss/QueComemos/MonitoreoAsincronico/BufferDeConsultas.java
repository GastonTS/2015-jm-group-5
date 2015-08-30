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

	public void agregarProceso(ProcesoAsincronico unProceso) {
		procesosARealizar.add(unProceso);
	}

	public void procesarConsultas() {
		procesosARealizar.forEach(proceso -> proceso.procesarConsultas(consultasRealizadas));
	}

	public void limpiarConsultas() {
		consultasRealizadas.removeAll(consultasRealizadas);
	}
}
