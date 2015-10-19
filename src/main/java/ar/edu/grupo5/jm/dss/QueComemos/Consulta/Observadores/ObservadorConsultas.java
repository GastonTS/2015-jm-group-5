package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
@Entity
public abstract class ObservadorConsultas {

	@Id
	@GeneratedValue
	private Long observadorID;
	
	public abstract void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas);

}
