package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ObservadorConsultas {

	@Id
	@GeneratedValue
	private Long observadorID;
	
	public abstract void notificarConsulta(Consulta unaConsulta);

}
