package ar.edu.grupo5.jm.dss.QueComemos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioIngresadoNoExisteException;

public class RepoUsuariosTest {

	private Usuario gustavo = mock(Usuario.class);
	private Usuario cerati = mock(Usuario.class);
	private Usuario gaston = mock(Usuario.class);
	private Usuario juanchi = mock(Usuario.class);
	private Usuario franco = mock(Usuario.class);
	
	private Usuario usuarioMock = mock(Usuario.class);
	
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
		repoUsuarios.add(cerati);

		when(juanchi.getNombre()).thenReturn("juanchi");
		when(gustavo.getNombre()).thenReturn("gustavo");
		when(cerati.getNombre()).thenReturn("gustavo");
		when(gaston.getNombre()).thenReturn("gaston");
		when(juanchi.getCondicionesDeSalud()).thenReturn(condicionesJuanchi);
		when(gustavo.getCondicionesDeSalud()).thenReturn(condicionesGustavo);
		when(gaston.getCondicionesDeSalud()).thenReturn(condicionesGaston);
		when(cerati.getCondicionesDeSalud()).thenReturn(Arrays.asList(hippie));
	}

	// Testea que se agrega y se elimina bien

	@Test
	public void eliminoJuanchi() {
		assertTrue(repoUsuarios.getUsuarios().contains(juanchi));
		repoUsuarios.remove(juanchi);
		assertFalse(repoUsuarios.getUsuarios().contains(juanchi));
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
		when(usuarioMock.getNombre()).thenReturn("juanchi");
		assertEquals(repoUsuarios.get(usuarioMock), Optional.of(juanchi));
		verify(usuarioMock, times(1)).getNombre();
	}

	@Test
	public void buscarGustavosHippies() {
		when(usuarioMock.getNombre()).thenReturn("gustavo");
		when(usuarioMock.getCondicionesDeSalud()).thenReturn(condicionesGustavo);
		assertEquals(repoUsuarios.list(usuarioMock), Arrays.asList(gustavo));
		verify(usuarioMock, times(4)).getNombre();
		verify(usuarioMock, times(2)).getCondicionesDeSalud();
	}

	@Test
	public void buscarGustavosSinCondicion() {
		when(usuarioMock.getNombre()).thenReturn("gustavo");
		when(usuarioMock.getCondicionesDeSalud()).thenReturn(new ArrayList<CondicionDeSalud>());
		assertEquals(repoUsuarios.list(usuarioMock), Arrays.asList(gustavo, cerati));
		verify(usuarioMock, times(4)).getNombre();
		verify(usuarioMock, times(2)).getCondicionesDeSalud();
	}

}
