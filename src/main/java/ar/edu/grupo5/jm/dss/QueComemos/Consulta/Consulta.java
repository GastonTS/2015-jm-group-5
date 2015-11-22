package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.Filtro;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.RepoObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;

@Entity
public class Consulta {
	
	@Id
	@GeneratedValue
	private Long recetaId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Filtro filtro;
	@ManyToOne(cascade = CascadeType.ALL)
	public Usuario usuario;
	@ManyToMany(cascade = CascadeType.ALL)
	public Collection<Receta> recetasConsultadas;

	public Consulta(ConsultorRecetas unConsultor, Filtro unFiltro, Usuario unUsuario) {
		filtro = unFiltro;
		usuario = unUsuario;

		recetasConsultadas = filtro.filtrarRecetas(unConsultor.getRecetas(usuario), usuario);
		RepoObservadorConsultas.instancia.notificarObservadores(this);
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

	public Sexo getSexo() {
		return usuario.getSexo();
	}
	
	public String getDestinatario() {
		return usuario.getMail();
	}

	public int cantidadConsultadas() {
		return recetasConsultadas.size();
	}

	public String parametrosDeBusquedaToString() {
		Stream<String> nombreParametros = filtro.getNombresFiltros();
		return nombreParametros.reduce("", (texto, nombreParametro) -> texto + "\t-> " + nombreParametro + ".\n");
	}

}
