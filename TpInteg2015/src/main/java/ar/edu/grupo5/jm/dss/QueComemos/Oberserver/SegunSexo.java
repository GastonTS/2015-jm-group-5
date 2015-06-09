package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.Collection;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class SegunSexo implements ObservadorConsultas {

	private MasConsultada consultasHombres = new MasConsultada();
	private MasConsultada consultasMujeres = new MasConsultada();

	@Override
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		if (unUsuario.esDeSexo("Masculino")) {

			consultasHombres.notificar(unUsuario, recetasConsultadas);
		} else if (unUsuario.esDeSexo("Femenino")) {

			consultasMujeres.notificar(unUsuario, recetasConsultadas);
		}
	}

	public int cantidadRecetaMasConsultadaHombre() {
		return consultasHombres.cantidadDeConsultasDeRecetaMAsConsultada();
	}

	public int cantidadRecetaMasConsultadaMujer() {
		return consultasMujeres.cantidadDeConsultasDeRecetaMAsConsultada();
	}

	public Optional<Receta> nombreRecetaHombre() {
		return consultasHombres.nombreRecetaMasConsultada();
	}

	public Optional<Receta> nombreRecetaMujer() {
		return consultasMujeres.nombreRecetaMasConsultada();
	}
}
