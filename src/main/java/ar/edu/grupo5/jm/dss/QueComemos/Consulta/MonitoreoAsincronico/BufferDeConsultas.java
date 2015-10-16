package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import java.util.ArrayList;
import java.util.Collection;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;

public class BufferDeConsultas implements WithGlobalEntityManager {

	public static BufferDeConsultas instancia = new BufferDeConsultas();
//	private Collection<Consulta> consultasRealizadas = new ArrayList<Consulta>();
//	private Collection<ProcesoAsincronico> procesosARealizar = new ArrayList<ProcesoAsincronico>();

	public void agregarConsulta(Consulta unaConsulta) {
//		consultasRealizadas.add(unaConsulta);//Posiblemente tenga que sacarla
		entityManager().persist(unaConsulta);
	}
	private Collection<Consulta> getConsultasRealizadas() {
		return entityManager().createQuery("FROM Consulta", Consulta.class).getResultList();
	}

	public void agregarProceso(ProcesoAsincronico unProceso) {
	//	procesosARealizar.add(unProceso);
		entityManager().persist(unProceso);
	}
	
	private Collection<ProcesoAsincronico> getProcesosAsincronicos() {
		return entityManager().createQuery("FROM ProcesoAsincronico", ProcesoAsincronico.class).getResultList();
	}

	public void procesarConsultas() {
		Collection<ProcesoAsincronico> procesosARealizar = this.getProcesosAsincronicos();
		Collection<Consulta> consultasRealizadas = this.getConsultasRealizadas();
		procesosARealizar.forEach(proceso -> proceso.procesarConsultas(consultasRealizadas));
		this.limpiarConsultas();
	}

	public void limpiarConsultas() {
//		consultasRealizadas.removeAll(this.getConsultasRealizadas());//Posiblemente tenga que sacarla
		entityManager().createQuery("DELETE Consulta");
	}
}
