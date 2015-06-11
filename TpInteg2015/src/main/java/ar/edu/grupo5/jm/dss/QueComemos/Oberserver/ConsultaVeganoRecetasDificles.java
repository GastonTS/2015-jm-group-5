package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class ConsultaVeganoRecetasDificles implements ObservadorConsultas {

	private Collection<Usuario> veganosQueConsultaronDificiles = new ArrayList<Usuario>();

	@Override
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		if (recetasConsultadas.stream().anyMatch(receta -> receta.esDificil())) {
			unUsuario.notificaA(this);
		}
	}

	public void sumame(Usuario unUsuario) {
		if (!veganosQueConsultaronDificiles.contains(unUsuario)) {
			veganosQueConsultaronDificiles.add(unUsuario);
		}
	}

	public int cantidadDeVeganos() {
		return veganosQueConsultaronDificiles.size();
	}

}
