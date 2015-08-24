package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.Calendar;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class PorHoraDelDia implements ObservadorConsultas {
	private int[] consultasPorHoraDelDia = new int[24];

	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		consultasPorHoraDelDia[Calendar.HOUR_OF_DAY]++;
	}

	public int getConsultasPorHoraDelDia(int horaDelDia) {
		return consultasPorHoraDelDia[horaDelDia];
	}
}
