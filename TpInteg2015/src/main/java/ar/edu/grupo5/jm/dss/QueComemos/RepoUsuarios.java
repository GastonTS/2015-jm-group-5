package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuscado;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioIngresadoNoExisteException;

public class RepoUsuarios {
	private Collection<Usuario> usuarios;
	private Collection<Usuario> solicitudesDeIngreso;

	// getter solo para test
	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}
	public Collection<Usuario> getSolicitudesDeIngreso() {
		return solicitudesDeIngreso;
	}

	public RepoUsuarios(Collection<Usuario> unosUsuarios) {
		usuarios = unosUsuarios;
		solicitudesDeIngreso = new ArrayList<Usuario>();
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
		usuarioViejo.update(usuarioNuevo);
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

	public void apruebaSolicitud(Usuario unUsuario) {
		if(!solicitudesDeIngreso.contains(unUsuario)){
			throw new UsuarioSinSolicitudDeIngresoExeption("No se puede aprobar la solicitud del usuario!");
		}
		
		add(unUsuario);
		solicitudesDeIngreso.remove(unUsuario); 
	}

	public void rechazaSolicitud(Usuario unUsuario) {
		if(!solicitudesDeIngreso.contains(unUsuario)){
			throw new UsuarioSinSolicitudDeIngresoExeption("No se puede rechazar la solicitud del usuario!");
		}
		
		solicitudesDeIngreso.remove(unUsuario);
		// informar rechazo, no esta específicado que informar. Así que
		// no hago nada
	}
}
