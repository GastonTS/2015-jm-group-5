package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

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

	public Optional<Usuario> get(Usuario unUsuario) {
		return list(unUsuario).findFirst();
	}

	public Stream<Usuario> list(Usuario unUsuario) {
		return usuarios.stream().filter(
				usuarioPosta -> searchByName(unUsuario, usuarioPosta));
	}

	private Boolean searchByName(Usuario usuarioBuscado, Usuario usuarioPosta) {
		return tienenMismoNombre(usuarioBuscado, usuarioPosta)
				|| tienenMismasCondicionesDeSalud(usuarioBuscado, usuarioPosta);
	}

	private Boolean tienenMismoNombre(Usuario usuario1, Usuario usuario2) {
		return sonIgualesONull(usuario1.getNombre(), usuario2.getNombre());
	}

	private Boolean tienenMismasCondicionesDeSalud(Usuario usuario1,
			Usuario usuario2) {
		return sonIgualesONull(usuario1.getCondicionesDeSalud(),
				usuario2.getCondicionesDeSalud());
	}

	private Boolean sonIgualesONull(Object objeto1, Object objeto2) {
		return objeto1 == null || objeto1 == null || objeto1 == objeto2;
	}

}
