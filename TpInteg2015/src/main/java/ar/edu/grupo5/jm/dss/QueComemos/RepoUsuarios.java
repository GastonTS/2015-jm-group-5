package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

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
			throw new UsuarioIngresadoNoExisteException("No se encontro usuario en el repositorio de usuarios");
		}

		usuarios.remove(unUsuario);
	}

	public void update(Usuario usuarioViejo, Usuario usuarioNuevo) {
		remove(usuarioViejo);
		add(usuarioNuevo);
	}

	public Usuario get(Usuario unUsuario) {
		return list(unUsuario).stream().findFirst().get();
	}

	public Collection<Usuario> list(Usuario unUsuario) {
		return usuarios.stream().filter(usuarioPosta -> searchByName(unUsuario, usuarioPosta)).collect(Collectors.toList());
	}

	private Boolean searchByName(Usuario usuarioBuscado, Usuario usuarioPosta) {
		return tienenMismoNombre(usuarioBuscado, usuarioPosta) ^ tienenMismasCondicionesDeSalud(usuarioBuscado, usuarioPosta);
	}

	private Boolean tienenMismoNombre(Usuario usuarioBuscado, Usuario usuarioPosta) {
		return usuarioPosta.getNombre().equals(usuarioBuscado.getNombre());
	}

	private Boolean tienenMismasCondicionesDeSalud(Usuario usuarioBuscado, Usuario usuarioPosta) {
		if (usuarioBuscado.getCondicionesDeSalud() == null) {
			return false;
		}
		return usuarioPosta.getCondicionesDeSalud().containsAll(usuarioBuscado.getCondicionesDeSalud());
	}

}
