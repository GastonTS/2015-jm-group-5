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
	
	@Override
	public boolean equals(Object unIngrediente){
		return nombre == ((Ingrediente) unIngrediente).getNombre();
	}
	
	public String getNombre(){
		return nombre;
	}

}
