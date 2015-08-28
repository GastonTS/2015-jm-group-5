package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;

public class ConsultasSegunSexo extends GestorCantidadConsultasRecetas implements ObservadorConsultas{
	
	Collection<Receta> consultasHombres = new ArrayList<Receta>();
	Collection<Receta> consultasMujeres = new ArrayList<Receta>();

	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		if (unUsuario.esDeSexo(Sexo.MASCULINO)) {
			 consultasHombres.addAll(recetasConsultadas);
		} else if (unUsuario.esDeSexo(Sexo.FEMENINO)) {
			consultasMujeres.addAll(recetasConsultadas);
		}
	}

	public  int cantidadRecetaMasConsultadaPara(Usuario unUsuario) {
		
		if (unUsuario.esDeSexo(Sexo.MASCULINO)) {
			return cantidadDeElementoMasRepetidoEn(consultasHombres);
		} else return cantidadDeElementoMasRepetidoEn(consultasMujeres);
		
	}

	public Optional<Receta> recetaMasConsultadaPara(Usuario unUsuario) {
		if (unUsuario.esDeSexo(Sexo.MASCULINO)) {
			 return elementoQueMasSeRepiteEn(consultasHombres);
		} else return elementoQueMasSeRepiteEn(consultasMujeres);
		
	}
	
}
