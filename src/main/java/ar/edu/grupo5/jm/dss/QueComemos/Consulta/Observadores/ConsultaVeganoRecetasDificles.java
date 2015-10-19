package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ConsultaVeganoRecetasDificles extends ObservadorConsultas {

	@ManyToMany
	private Collection<Usuario> veganosQueConsultaronDificiles = new ArrayList<Usuario>();

	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		if(unUsuario.esVegano()) {
			this.notificarVegano(unUsuario, recetasConsultadas); 
		}
	}
	
	private void notificarVegano(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		if ((!veganosQueConsultaronDificiles.contains(unUsuario)) && (recetasConsultadas.stream().anyMatch(receta -> receta.esDificil()))) {
			veganosQueConsultaronDificiles.add(unUsuario);
		}
	}

	public int cantidadDeVeganos() {
		return veganosQueConsultaronDificiles.size();
	}

}
