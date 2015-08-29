package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class ConsultasTotales extends AcumuladorConsultas {
	Collection<Receta> recetasConsultadas = new ArrayList<Receta>();
	
	@Override
	public Collection<Receta> getRecetasConsultadas(Usuario unUsuario) {
		return recetasConsultadas;
	}
	
	public Optional<Receta> recetaMasConsultada() {
		return elementoQueMasSeRepiteEn(recetasConsultadas);
	}

	public int cantidadDeConsultasDeRecetaMasConsultada() {
		return cantidadDeElementoMasRepetidoEn(recetasConsultadas); 
	}
}
