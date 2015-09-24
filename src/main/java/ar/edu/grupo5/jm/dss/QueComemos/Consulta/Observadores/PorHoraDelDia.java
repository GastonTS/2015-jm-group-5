package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

import java.util.HashMap;
import java.util.Map;

public class PorHoraDelDia implements ObservadorConsultas {
	Clock reloj;
	private Map<Integer, Integer> consultasPorHoraDelDia = new HashMap<Integer, Integer>();

	public PorHoraDelDia(Clock relojAsignado) {
		reloj = relojAsignado;

		for (int hora = 0; hora < 24; hora++)
			consultasPorHoraDelDia.put(hora, 0);
	}

	public void setReloj(Clock reloj) {
		this.reloj = reloj;
	}

	public int getConsultasPorHoraDelDia(int unaHora) {
		if (unaHora < 0 || unaHora > 23)
			throw new HoraIngresadaNoValidaException("Hora ingresada incorrecta. Debe ser entero entre 0 y 23");

		return consultasPorHoraDelDia.get(unaHora);
	}

	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		int horaDeConsulta = LocalTime.now(reloj).getHour();

		consultasPorHoraDelDia.put(horaDeConsulta, getConsultasPorHoraDelDia(horaDeConsulta) + 1);
	}

}
