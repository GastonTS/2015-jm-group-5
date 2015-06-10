package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioIngresadoNoExisteException;

public class RepoUsuarios {
	private Collection<Usuario> usuarios;
	private Collection<Usuario> solicitudesDeIngreso;

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

	// pregunto por null para no modificar el tipo de condiciones de salud, al
	// no poder ser null el containsAll si lo recibe como null => BOOM!
	private Boolean tienenMismasCondicionesDeSalud(Usuario usuarioBuscado, Usuario usuarioPosta) {
		if (usuarioBuscado.getCondicionesDeSalud() == null) {
			return false;
		}
		return usuarioPosta.getCondicionesDeSalud().containsAll(usuarioBuscado.getCondicionesDeSalud());
	}

	private void solicitaIngreso(Usuario unUsuario) {
		solicitudesDeIngreso.add(unUsuario);
	}

	private void apruebaSolicitud(Usuario unUsuario) {
		add(unUsuario);
	}
	
	private void rechazaSolicitud(Usuario unUsuario) {
		solicitudesDeIngreso.remove(unUsuario);
		//informar rechazo, no esta específicado que carajos informar. Así que no hago nada
	}
}
