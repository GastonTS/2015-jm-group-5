package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ConsultorRecetas {
	
	@Id
	@GeneratedValue
	private Long consultorId;
	
	public abstract Collection<Receta> getRecetas(Usuario unUsuario);

}
