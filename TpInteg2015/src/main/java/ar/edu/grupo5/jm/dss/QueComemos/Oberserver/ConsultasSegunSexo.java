package ar.edu.grupo5.jm.dss.QueComemos.Oberserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;

public class ConsultasSegunSexo extends GestorCantidadConsultasRecetas implements ObservadorConsultas{
	
	ArrayList<Receta> consultasHombres = new ArrayList<Receta>();
	ArrayList<Receta> consultasMujeres = new ArrayList<Receta>();

	@Override
	public void notificarConsulta(Usuario unUsuario, Collection<Receta> recetasConsultadas) {
		if (unUsuario.esDeSexo(Sexo.MASCULINO)) {
			añadirAColeccion(unUsuario, recetasConsultadas, consultasHombres);
		} else if (unUsuario.esDeSexo(Sexo.FEMENINO)) {
			añadirAColeccion(unUsuario, recetasConsultadas, consultasMujeres);
		}
	}

	public  int recetaMasConsultadaPara(Usuario unUsuario) {
		
		if (unUsuario.esDeSexo(Sexo.MASCULINO)) {
			return cantidadDeElementoMasRepetidoEn(consultasHombres);
		} else return cantidadDeElementoMasRepetidoEn(consultasMujeres);
		
	}

	public Optional<Receta> cantidadRecetaMasConsultadaPara(Usuario unUsuario) {
		if (unUsuario.esDeSexo(Sexo.MASCULINO)) {
			 return elementoQueMasSeRepiteEn(consultasHombres);
		} else return elementoQueMasSeRepiteEn(consultasMujeres);
		
	}
	
}
