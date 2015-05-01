package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Stream;

public class GestorCondiciones {
	private Collection<CondicionPreexistente> condiciones;

	public Stream<CondicionPreexistente> condicionesALasQueEsInadecuada(Receta unaReceta) {
		return condiciones.stream().filter(condicion -> condicion.esInadecuada(unaReceta));
	}
	 
}
