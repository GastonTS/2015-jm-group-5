package ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CondicionDeSalud {

	@Id
	@GeneratedValue
	private Long condicionDeSaludId;
	@Transient
	static final Collection<CondicionDeSalud> condicionesExistentes = Arrays.asList(new Diabetico(), new Celiaco(), new Hipertenso(), new Vegano());

	public abstract boolean subsanaCondicion(Usuario unUsuario);

	public abstract boolean esInadecuada(Receta unaReceta);

	public abstract boolean esUsuarioValido(Usuario unUsuario);
	
	public abstract String getNombre();

	static public Collection<CondicionDeSalud> condicionesALasQueEsInadecuada(Receta unaReceta) {
		return CondicionDeSalud.condicionesExistentes.stream().filter(condicion -> condicion.esInadecuada(unaReceta))
				.collect(Collectors.toList());
	}

	public boolean esCondicionVegana() {
		return false;
	}
}
