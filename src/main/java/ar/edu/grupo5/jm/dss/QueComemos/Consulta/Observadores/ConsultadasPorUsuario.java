package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class ConsultadasPorUsuario extends AcumuladorConsultas {

	public static ConsultadasPorUsuario instancia() {
		return RepoObservadorConsultas.instancia.getObservador(ConsultadasPorUsuario.class);
	}
	
	@OneToMany(cascade = CascadeType.ALL) 
	Collection<HistorialConsultaUsuario> historialesConsultados = new ArrayList<HistorialConsultaUsuario>();

	private Optional<HistorialConsultaUsuario> getHistorialPara(Usuario unUsuario) {
		return historialesConsultados
				.stream().filter(historialConsultado -> historialConsultado.perteneceA(unUsuario))
				.findFirst();
	}
	
	private List<Receta> getRecetasConsultadas(Usuario unUsuario) {
		Optional<HistorialConsultaUsuario> historial = this.getHistorialPara(unUsuario);
		if(historial.isPresent()) {
			return historial.get().getConsultadas();
		} else {
			return new ArrayList<Receta>();
		}
	}
	
	public List<Receta> masConsultadasEnOrden(Usuario unUsuario) {
		return this.recetasMasConsultadasEnOrdenDescendiente(this.getRecetasConsultadas(unUsuario));
	}

	public void notificarConsulta(Consulta unaConsulta) {
		Usuario usuario = unaConsulta.getUsuario();
		Optional<HistorialConsultaUsuario> historial = this.getHistorialPara(usuario);
		Collection<Receta> recetasConsultadas = unaConsulta.getRecetasConsultadas();
		
		
		if (historial.isPresent()) {
			historial.get().agregarTodasConsultadas(recetasConsultadas);
		} else {
			HistorialConsultaUsuario historialConsultado = new HistorialConsultaUsuario(usuario);
			historialConsultado.agregarTodasConsultadas(recetasConsultadas);
			historialesConsultados.add(historialConsultado);
		}
	}
	
}