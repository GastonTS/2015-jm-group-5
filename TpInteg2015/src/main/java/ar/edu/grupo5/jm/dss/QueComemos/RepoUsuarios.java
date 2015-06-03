package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

public class RepoUsuarios {
	private Collection<Usuario> usuarios;

	public void add(Usuario unUsuario) {
		usuarios.add(unUsuario);
	}

	public void remove(Usuario unUsuario) {
		if (!usuarios.contains(unUsuario)) {
			throw new RecetaNoValidaException(
					"No se Puede agregar una receta no válida!!!");
		}
		
		usuarios.remove(unUsuario);
	}

	public void update(Usuario unUsuario) {
		remove(unUsuario);
		add(unUsuario);
	}
	/*
	 * update(Usuario) get(Usuario): devuelve un usuario haciendo la búsqueda
	 * por el nombre del usuario que se pase como parámetro list(Usuario):
	 * devuelve una lista de usuarios cuyo nombre contenga el nombre del usuario
	 * ingresado como parámetro. En caso de ingresar condiciones preexistentes,
	 * devuelve todos los usuarios que cumplen con la condición del nombre
	 * anteriormente descrita y que además cumplan todas las condiciones
	 * preexistentes, ej: todos los diabéticos.
	 */
}
