package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectUpdater;

public class RepoUsuarios implements ObjectUpdater {
	private Collection<Usuario> usuarios;
	private Collection<Usuario> solicitudesDeIngreso;

	public RepoUsuarios(Collection<Usuario> unosUsuarios) {
		usuarios = unosUsuarios;
		solicitudesDeIngreso = new ArrayList<Usuario>();
	}

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public Collection<Usuario> getSolicitudesDeIngreso() {
		return solicitudesDeIngreso;
	}

	public void add(Usuario unUsuario) {
		usuarios.add(unUsuario);
	}

	public void remove(Usuario unUsuario) {
		existeUsuario(unUsuario);
		usuarios.remove(unUsuario);
	}

	private void existeUsuario(Usuario unUsuario) {
		if (!usuarios.contains(unUsuario)) {
			throw new UsuarioIngresadoNoExisteException("No se encontro usuario en el repositorio de usuarios");
		}
	}

	public void modificarUsuario(Usuario usuarioViejo, Usuario usuarioNuevo) {
		this.existeUsuario(usuarioViejo);
		this.update(usuarioViejo, usuarioNuevo);
	}

	public Optional<Usuario> buscarUnUsuarioConNombre(UsuarioBuscado unUsuario) {
		return usuarios.stream().filter(usuarioPosta -> unUsuario.tieneElNombreDe(usuarioPosta)).findFirst();
	}

	public Collection<Usuario> listarPorNombreYCondiciones(UsuarioBuscado unUsuario) {
		return usuarios.stream().filter(usuarioPosta -> tienenMismoNombreYCondiciones(unUsuario, usuarioPosta)).collect(Collectors.toList());
	}

	private Boolean tienenMismoNombreYCondiciones(UsuarioBuscado usuarioBuscado, Usuario usuarioPosta) {
		return usuarioBuscado.tieneElNombreDe(usuarioPosta) && usuarioBuscado.tieneTodasLasCondicionesDeSaludDe(usuarioPosta);
	}

	public void solicitaIngreso(Usuario unUsuario) {
		solicitudesDeIngreso.add(unUsuario);
	}

	private Boolean existeSolicitudDe(Usuario unUsuario) {
		return solicitudesDeIngreso.contains(unUsuario);
	}

	public void apruebaSolicitud(Usuario unUsuario) {
		if (!existeSolicitudDe(unUsuario)) {
			throw new UsuarioSinSolicitudDeIngresoExeption("No se puede aprobar la solicitud del usuario!");
		}

		add(unUsuario);
		solicitudesDeIngreso.remove(unUsuario);
	}

	public void rechazaSolicitud(Usuario unUsuario) {
		if (!existeSolicitudDe(unUsuario)) {
			throw new UsuarioSinSolicitudDeIngresoExeption("No se puede rechazar la solicitud del usuario!");
		}

		solicitudesDeIngreso.remove(unUsuario);
		//Informa Rechazo
	}
}
