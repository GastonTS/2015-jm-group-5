package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;

public interface CondicionDeSalud {

	Collection<CondicionDeSalud> condicionesExistentes = Arrays.asList(
			new Diabetico(), new Celiaco(), new Hipertenso(), new Vegano());

	public abstract boolean subsanaCondicion(Usuario unUsuario);

	public abstract boolean esInadecuada(Receta unaReceta);

	public abstract boolean esUsuarioValido(Usuario unUsuario);
}
