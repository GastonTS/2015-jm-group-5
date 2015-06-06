package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MasConsultada implements ObservadorConsultas {
	Map<Receta, Integer> recetaMap = new HashMap<Receta, Integer>();

	@Override
	public void notificar(Usuario unUsuario,
			Collection<Receta> recetasConsultadas) {
		recetasConsultadas.forEach(unaReceta -> agregarUnaConsultaAlMapReceta(unaReceta));
	}

	//VER: Qué utilizo para identificar a una Receta?? Su nombre, todo el objeto en sí, otras?
	private void agregarUnaConsultaAlMapReceta(Receta unaReceta){
		recetaMap.put(unaReceta, recetaMap.get(unaReceta) + 1);
	}
	
	// debería agregar un getter de recetaMap para saber que
	// funciona correctamente, lo que luego se probaría en un test	
}
