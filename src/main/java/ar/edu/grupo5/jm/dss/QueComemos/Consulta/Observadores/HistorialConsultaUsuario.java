package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class HistorialConsultaUsuario {

	@Id
	@GeneratedValue
	Long id;
	@ManyToOne
	private Usuario consultor;
	
	@ManyToMany
	@OrderColumn
	private List<Receta> consultadas = new ArrayList<Receta>();
	
	public HistorialConsultaUsuario() {
		
	}
	
	public HistorialConsultaUsuario(Usuario unUsuario) {
		consultor = unUsuario;
	}
	
	public void agregarConsultada(Receta unaReceta) {
		consultadas.add(unaReceta);
	}
	
	public List<Receta> getConsultadas() {
		return consultadas;
	}
	
	public boolean perteneceA(Usuario unUsuario) {
		return unUsuario == consultor;
	}

	public void agregarTodasConsultadas(Collection<Receta> unasRecetas) {
		consultadas.addAll(unasRecetas);
	}
	
}
