package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RepoUsuarios {
	private Collection<Usuario> usuarios;

	// getter solo para test
	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public RepoUsuarios(Collection<Usuario> unosUsuarios) {
		usuarios = unosUsuarios;
	}

	public void add(Usuario unUsuario) {
		usuarios.add(unUsuario);
	}

	public void remove(Usuario unUsuario) {
		if (!usuarios.contains(unUsuario)) {
			throw new UsuarioIngresadoNoExisteException(
					"No se encontro usuario en el repositorio de usuarios");
		}

		usuarios.remove(unUsuario);
	}

	public void update(Usuario unUsuario) {// FIXME Segun que key se
											// actualiza??? porque se puede
											// tener 2 usuarios con nombres
											// iguales
		remove(unUsuario);
		add(unUsuario);
	}

	public Usuario get(Usuario unUsuario) {
		return list(unUsuario).stream().findFirst().get();
	}

	public Collection<Usuario> list(Usuario unUsuario) {
		return usuarios.stream()
				.filter(usuarioPosta -> searchByName(unUsuario, usuarioPosta))
				.collect(Collectors.toList());
	}

	private Boolean searchByName(Usuario usuarioBuscado, Usuario usuarioPosta) {
		return tienenMismoNombre(usuarioBuscado, usuarioPosta)
				^ tienenMismasCondicionesDeSalud(usuarioBuscado, usuarioPosta);
	}

	private Boolean tienenMismoNombre(Usuario usuario1, Usuario usuario2) {
		return usuario1.getNombre() == usuario2.getNombre();
	}

	private Boolean tienenMismasCondicionesDeSalud(Usuario usuario1,
			Usuario usuario2) {
		if (usuario1.getCondicionesDeSalud() == null) {
			return false;
		}
		return usuario2.getCondicionesDeSalud().containsAll(
				usuario1.getCondicionesDeSalud());
	}

}
