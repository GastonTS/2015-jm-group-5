package ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public abstract class CondicionDeSalud {

	@Id
	@GeneratedValue
	private Long condicionDeSaludId;

	static Collection<CondicionDeSalud> condicionesExistentes = Arrays.asList(new Diabetico(), new Celiaco(), new Hipertenso(), new Vegano());

	public abstract boolean subsanaCondicion(Usuario unUsuario);

	public abstract boolean esInadecuada(Receta unaReceta);

	public abstract boolean esUsuarioValido(Usuario unUsuario);

	static public Collection<CondicionDeSalud> condicionesALasQueEsInadecuada(Receta unaReceta) {
		return CondicionDeSalud.condicionesExistentes.stream().filter(condicion -> condicion.esInadecuada(unaReceta))
				.collect(Collectors.toList());
	}

	public boolean esCondicionVegana() {
		return false;
	}
}
