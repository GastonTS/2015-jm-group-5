package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Calendar;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class PorHoraDelDia implements ObservadorConsultas {
	private int[] consultasPorHoraDelDia = new int[24];

	@Override
	public void notificar(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		consultasPorHoraDelDia[Calendar.HOUR_OF_DAY]++;
	}

	// debería agregar un getter de consultasPorHoraDelDia para saber que
	// funciona correctamente, lo que luego se probaría en un test
	public int getConsultasPorHoraDelDia(int horaDelDia) {
		return consultasPorHoraDelDia[horaDelDia];
	}
}
