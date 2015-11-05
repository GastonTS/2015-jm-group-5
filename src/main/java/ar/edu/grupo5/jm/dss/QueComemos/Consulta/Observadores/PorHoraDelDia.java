package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

@Entity
public class PorHoraDelDia extends ObservadorConsultas {
	@Transient
	private Clock reloj = Clock.system(ZoneId.of("America/Argentina/Buenos_Aires"));
	 @ElementCollection
	private Map<Integer, Integer> consultasPorHoraDelDia = new HashMap<Integer, Integer>();

	public PorHoraDelDia() {

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
	public void notificarConsulta(Consulta unaConsulta) {
		int horaDeConsulta = LocalTime.now(reloj).getHour();

		consultasPorHoraDelDia.put(horaDeConsulta, getConsultasPorHoraDelDia(horaDeConsulta) + 1);
	}

}
