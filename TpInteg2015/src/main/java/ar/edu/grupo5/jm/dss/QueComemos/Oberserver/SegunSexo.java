package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.Collection;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class SegunSexo implements ObservadorConsultas {

	private MasConsultada consultasHombres = new MasConsultada();
	private MasConsultada consultasMujeres = new MasConsultada();

	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		if (unUsuario.esDeSexo(Sexo.MASCULINO)) {

			consultasHombres.notificarConsulta(unUsuario, recetasConsultadas);
		} else if (unUsuario.esDeSexo(Sexo.FEMENINO)) {

			consultasMujeres.notificarConsulta(unUsuario, recetasConsultadas);
		}
	}

	public int cantidadRecetaMasConsultadaHombre() {
		return consultasHombres.cantidadDeConsultasDeRecetaMAsConsultada();
	}

	public int cantidadRecetaMasConsultadaMujer() {
		return consultasMujeres.cantidadDeConsultasDeRecetaMAsConsultada();
	}

	public Optional<Receta> recetaHombre() {
		return consultasHombres.recetaMasConsultada();
	}

	public Optional<Receta> recetaMujer() {
		return consultasMujeres.recetaMasConsultada();
	}
}
