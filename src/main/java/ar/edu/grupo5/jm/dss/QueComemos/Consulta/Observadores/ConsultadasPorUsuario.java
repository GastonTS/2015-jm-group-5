package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ConsultadasPorUsuario extends AcumuladorConsultas {
	
	public static ConsultadasPorUsuario instancia() {
		Optional<ObservadorConsultas> esto = RepoObservadorConsultas.instancia.getObservador("ConsultadasPorUsuario");
		return (esto.isPresent()) ? (ConsultadasPorUsuario) esto.get() : new ConsultadasPorUsuario();
	}
	@OneToMany(cascade = CascadeType.ALL) 
	Collection<HistorialConsultaUsuario> historialesConsultados = new ArrayList<HistorialConsultaUsuario>();

	@Override
	public Collection<Receta> getRecetasConsultadas(Usuario unUsuario) {
		if(!historialesConsultados.stream().anyMatch(historialConsultado -> historialConsultado.perteneceA(unUsuario))) {
			historialesConsultados.add(new HistorialConsultaUsuario(unUsuario));
		}
		return historialesConsultados.stream().findFirst().get().getConsultadas();
	}

	public List<Receta> masConsultadasEnOrden(Usuario unUsuario) {
		return this.recetasMasConsultadasEnOrdenDescendiente(this.getRecetasConsultadas(unUsuario));
	}
	
}