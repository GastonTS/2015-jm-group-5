package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ingrediente {

	@Id
	@GeneratedValue
	private Long ingredienteID;

	private String nombre;

	public Ingrediente(String unNombre) {
		nombre = unNombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	@Override
	public boolean equals(Object unIngrediente) {
		return nombre.equals(((Ingrediente) unIngrediente).getNombre());
	}

	@Override
	public int hashCode() {
		return nombre.hashCode();
	}

}
