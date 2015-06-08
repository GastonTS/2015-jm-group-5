package ar.edu.grupo5.jm.dss.QueComemos;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class RepoUsuariosTest {

	private Usuario gustavo = mock(Usuario.class);
	private Usuario gaston = mock(Usuario.class);
	private Usuario juanchi = mock(Usuario.class);
	private Usuario franco = mock(Usuario.class);

	private RepoUsuarios repoUsuarios;

	private Collection<CondicionDeSalud> condicionesGustavo = new ArrayList<CondicionDeSalud>();
	private Collection<CondicionDeSalud> condicionesGaston = new ArrayList<CondicionDeSalud>();
	private Collection<CondicionDeSalud> condicionesJuanchi = new ArrayList<CondicionDeSalud>();
	private CondicionDeSalud hippie = mock(CondicionDeSalud.class);
	private CondicionDeSalud corporativo = mock(CondicionDeSalud.class);

	@Before
	public void setUp() {
		condicionesGustavo.add(hippie);
		condicionesGustavo.add(corporativo);
		condicionesGaston.add(corporativo);
		condicionesJuanchi.add(hippie);

		repoUsuarios = new RepoUsuarios(new ArrayList<Usuario>());

		repoUsuarios.add(juanchi);
		repoUsuarios.add(gaston);
		repoUsuarios.add(gustavo);

		when(juanchi.getNombre()).thenReturn("juanchi");
		when(gustavo.getNombre()).thenReturn("gustavo");
		when(gaston.getNombre()).thenReturn("gaston");
		when(juanchi.getCondicionesDeSalud()).thenReturn(condicionesJuanchi);
		when(gustavo.getCondicionesDeSalud()).thenReturn(condicionesGustavo);
		when(gaston.getCondicionesDeSalud()).thenReturn(condicionesGaston);

	}

	// Testea que se agrega y se elimina bien

	@Test
	public void eliminoJuanchi() {
		assertEquals(repoUsuarios.getUsuarios().size(), 3);
		repoUsuarios.remove(juanchi);
		assertEquals(repoUsuarios.getUsuarios().size(), 2);
	}

	@Test(expected = UsuarioIngresadoNoExisteException.class)
	public void eliminoAFrancoYTiraException() {
		repoUsuarios.remove(franco);
	}

	@Test(expected = UsuarioIngresadoNoExisteException.class)
	public void intentoActualizarUsuarioInexistente() {
		repoUsuarios.update(franco, gaston);
	}

	@Test
	public void buscarJuanchiPorNombre() {
		DatosPersonales datosPersonales = new DatosPersonales("juanchi", null, null);
		Usuario juanchiConNombre = new Usuario(datosPersonales, null, null, null, null, null);
		assertEquals(repoUsuarios.get(juanchiConNombre), juanchi);
	}

	@Test
	public void buscarGusPorCondicionesDeSalud() {
		DatosPersonales datosPersonalesGus = new DatosPersonales(null, null, null);
		Usuario gustavoConCondiciones = new Usuario(datosPersonalesGus, null, null, null, condicionesGustavo, null);
		assertEquals(repoUsuarios.list(gustavoConCondiciones).size(), 1);
		assertEquals(repoUsuarios.get(gustavoConCondiciones), gustavo);
	}

	@Test
	public void buscarHippiesGusYJuanchi() {
		DatosPersonales datosPersonalesHippies = new DatosPersonales(null, null, null);
		Usuario usuarioHippies = new Usuario(datosPersonalesHippies, null, null, null, condicionesJuanchi, null);
		Collection<Usuario> usuariosHippies = new ArrayList<Usuario>();
		usuariosHippies = repoUsuarios.list(usuarioHippies);
		assertTrue(usuariosHippies.contains(juanchi));
		assertTrue(usuariosHippies.contains(gustavo));
		assertEquals(usuariosHippies.size(), 2);
	}

	@Test
	public void buscarCorporativosGusYGaston() {
		DatosPersonales datosPersonalesHippies = new DatosPersonales(null, null, null);
		Usuario usuarioHippies = new Usuario(datosPersonalesHippies, null, null, null, condicionesGaston, null);
		Collection<Usuario> usuariosCorporativos = new ArrayList<Usuario>();
		usuariosCorporativos = repoUsuarios.list(usuarioHippies);
		assertTrue(usuariosCorporativos.contains(gaston));
		assertTrue(usuariosCorporativos.contains(gustavo));
		assertEquals(usuariosCorporativos.size(), 2);
	}

}
