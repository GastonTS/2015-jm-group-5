package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectUpdater;

public class RepoUsuarios implements ObjectUpdater, WithGlobalEntityManager {

	public static RepoUsuarios instancia = new RepoUsuarios();
	
	public Usuario getUsuario(Long idUsuario){
		return entityManager().find(Usuario.class, idUsuario);
	}

	public Collection<Usuario> getUsuariosTotales(){
		  return entityManager().createQuery("FROM Usuario", Usuario.class).getResultList();
	}
	
	public Collection<Usuario> getUsuariosAceptados() {
		return entityManager().createQuery("FROM Usuario as u WHERE u.aceptado is true", Usuario.class).getResultList();
	}

	public Collection<Usuario> getSolicitudesDeIngreso() {
		return entityManager().createQuery("FROM Usuario as u WHERE u.aceptado is false", Usuario.class).getResultList();
	}

	public void remove(Usuario unUsuario) {
		existeUsuario(unUsuario);
		entityManager().remove(unUsuario);
	}

	private void existeUsuario(Usuario unUsuario) {
		if (entityManager().find(Usuario.class, unUsuario.getId()) == null) {
			throw new UsuarioIngresadoNoExisteException("No se encontro usuario en el repositorio de usuarios");
		}
	}

	public void modificarUsuario(Usuario usuarioViejo, Usuario usuarioNuevo) {
		existeUsuario(usuarioViejo);
		update(usuarioViejo, usuarioNuevo);
	}

	public Optional<Usuario> buscarUnUsuarioConNombre(UsuarioBuscado unUsuario) {
		return entityManager().createQuery("FROM Usuario as u WHERE u.datosPersonales.nombre = :nombreBuscado", Usuario.class)
				.setParameter("nombreBuscado", unUsuario.getNombre())
				.getResultList().stream().findFirst();
	}

	public Collection<Usuario> listarPorNombreYCondiciones(UsuarioBuscado unUsuario) {
				Collection<Usuario> usuariosConNombre =  entityManager()
				.createQuery("FROM Usuario as u WHERE u.datosPersonales.nombre = :nombreBuscado ", Usuario.class)
				.setParameter("nombreBuscado", unUsuario.getNombre())
				.getResultList();
		return usuariosConNombre.stream().
					filter(u -> u.getCondicionesDeSalud().containsAll(unUsuario.getCondiciones())).collect(Collectors.toList());
	}

	public void solicitaIngreso(Usuario unUsuario) {
		entityManager().persist(unUsuario);
	}

	private Boolean existeSolicitudDe(Usuario unUsuario) {
		return !unUsuario.fueAceptado();
	}

	public void apruebaSolicitud(Usuario unUsuario) {
		if (!existeSolicitudDe(unUsuario)) {
			throw new UsuarioSinSolicitudDeIngresoExeption("No se puede aprobar la solicitud del usuario!");
		}
		unUsuario.aceptar();
	}

	public void rechazaSolicitud(Usuario unUsuario) {
		if (!existeSolicitudDe(unUsuario)) {
			throw new UsuarioSinSolicitudDeIngresoExeption("No se puede rechazar la solicitud del usuario!");
		}

		remove(unUsuario);
		//Informa Rechazo
	}
}
