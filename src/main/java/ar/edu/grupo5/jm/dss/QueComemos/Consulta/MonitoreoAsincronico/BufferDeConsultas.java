package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import java.util.Collection;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

public class BufferDeConsultas implements WithGlobalEntityManager {

	public static BufferDeConsultas instancia = new BufferDeConsultas();

	public void agregarConsulta(Consulta unaConsulta) {
		entityManager().persist(unaConsulta);
	}
	public Collection<Consulta> getConsultasRealizadas() {
		return entityManager().createQuery("FROM Consulta", Consulta.class).getResultList();
	}

	public void agregarProceso(ProcesoAsincronico unProceso) {
		entityManager().persist(unProceso);
	}
	
	public Collection<ProcesoAsincronico> getProcesosAsincronicos() {
		return entityManager().createQuery("FROM ProcesoAsincronico", ProcesoAsincronico.class).getResultList();
	}

	public void procesarConsultas() {
		Collection<ProcesoAsincronico> procesosARealizar = this.getProcesosAsincronicos();
		Collection<Consulta> consultasRealizadas = this.getConsultasRealizadas();
		procesosARealizar.forEach(proceso -> proceso.procesarConsultas(consultasRealizadas));
		this.limpiarConsultas(consultasRealizadas);
	}
 
	public void limpiarConsultas(Collection<Consulta> consultasRealizadas) {
		consultasRealizadas.forEach(consulta -> entityManager().remove(consulta));
	}
}
