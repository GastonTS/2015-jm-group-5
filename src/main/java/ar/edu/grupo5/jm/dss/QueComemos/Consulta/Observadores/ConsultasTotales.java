package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class ConsultasTotales extends AcumuladorConsultas {
	
	public static ConsultasTotales instancia() {
		Optional<ObservadorConsultas> esto = RepoObservadorConsultas.instancia.getObservador("ConsultasTotales");
		return (esto.isPresent()) ? (ConsultasTotales) esto.get() : new ConsultasTotales();
	}
	
	@ManyToMany @JoinTable(name = "RecetasTotalesXAcumulador")
	Collection<Receta> recetasConsultadas = new ArrayList<Receta>();

	@Override
	public Collection<Receta> getRecetasConsultadas(Usuario unUsuario) {
		return recetasConsultadas;
	}

	public Optional<Receta> recetaMasConsultada() {
		return elementoQueMasSeRepiteEn(recetasConsultadas);
	}

	public int cantidadDeConsultasDeRecetaMasConsultada() {
		return cantidadDeElementoMasRepetidoEn(recetasConsultadas);
	}
	
	public List<Receta> masConsultadasEnOrden() {
		return this.recetasMasConsultadasEnOrdenDescendiente(recetasConsultadas);
	}
	
}
