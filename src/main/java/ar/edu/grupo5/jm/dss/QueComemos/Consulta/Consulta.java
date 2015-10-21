package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.Filtro;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.BufferDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class Consulta {
	
	@Id
	@GeneratedValue
	private Long recetaId;
	
	@ManyToOne
	public Filtro filtro;
	@ManyToOne
	public Usuario usuario;
	@ManyToMany
	public Collection<Receta> recetasConsultadas;
	@ManyToMany
	static private Collection<ObservadorConsultas> observadores = new ArrayList<ObservadorConsultas>();

	public Consulta(ConsultorRecetas unConsultor, Filtro unFiltro, Usuario unUsuario) {
		filtro = unFiltro;
		usuario = unUsuario;

		recetasConsultadas = filtro.filtrarRecetas(unConsultor.getRecetas(usuario), usuario);
		this.notificarObservadores(observadores, usuario, recetasConsultadas);
		BufferDeConsultas.instancia.agregarConsulta(this);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Collection<Receta> getRecetasConsultadas() {
		return recetasConsultadas;
	}

	public String getNombre() {
		return usuario.getNombre();
	}

	public String getDestinatario() {
		return usuario.getMail();
	}

	public static void agregarObservador(ObservadorConsultas unObservador) {
		observadores.add(unObservador);
	}

	public static void quitarObservador(ObservadorConsultas unObservador) {
		observadores.remove(unObservador);
	}

	public void notificarObservadores(Collection<ObservadorConsultas> observadores, Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		observadores.forEach(observador -> observador.notificarConsulta(unUsuario, recetasConsultadas));
	}

	public int cantidadConsultadas() {
		return recetasConsultadas.size();
	}

	public String parametrosDeBusquedaToString() {
		Stream<String> nombreParametros = filtro.getNombresFiltros();
		return nombreParametros.reduce("", (texto, nombreParametro) -> texto + "\t-> " + nombreParametro + ".\n");
	}

}
