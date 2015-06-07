package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class SegunSexo implements ObservadorConsultas {
	
	private int consultasMasculinas =0;
	private int consultasFemeninas = 0;
	

	@Override
	public void notificar(Usuario unUsuario,
			Collection<Receta> recetasConsultadas) {

		if (unUsuario.esDeSexo("Masculino")){
           consultasMasculinas = consultasMasculinas + recetasConsultadas.size();
		} else if (unUsuario.esDeSexo("Femenino")){
           consultasFemeninas = consultasFemeninas + recetasConsultadas.size();
		}
	}
	
	public int getConsultasMasculinas (){
		return consultasMasculinas;
	}

	public int getConsultasFemeninas (){
		return consultasFemeninas;
	}
}
