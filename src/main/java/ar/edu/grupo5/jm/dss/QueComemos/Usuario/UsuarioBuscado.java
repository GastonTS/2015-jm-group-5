package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;

public class UsuarioBuscado {

	private String nombre;
	private Collection<CondicionDeSalud> condicionesDeSalud;

	public UsuarioBuscado(String unNombre, Collection<CondicionDeSalud> unasCondicionesDeSalud) {
		nombre = unNombre;
		condicionesDeSalud = unasCondicionesDeSalud;
	}

	public String getNombre(){
		return nombre;
	}
	
	public Collection<CondicionDeSalud> getCondiciones(){
		return condicionesDeSalud;
	}
	
	//ESTO TAMBIEN SE IRIA
	/*
	public Boolean tieneElNombreDe(Usuario unUsuario) {
		return nombre.equals(unUsuario.getNombre());
	}
	
	public Boolean tieneTodasLasCondicionesDeSaludDe(Usuario unUsuario) {
		return unUsuario.getCondicionesDeSalud().containsAll(condicionesDeSalud);
	}*/

}
