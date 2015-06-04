package ar.edu.grupo5.jm.dss.QueComemos;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RepoUsuariosTest {

	private Usuario gustavo;
	private Usuario gaston;
	private Usuario juanchi;
	private Usuario franco;

	private RepoUsuarios repoUsuarios;

	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);

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

		gustavo = new Usuario(datosPersonalesMock, null, null, null,
				condicionesGustavo, null);
		gaston = new Usuario(datosPersonalesMock, null, null, null,
				condicionesGaston, null);
		juanchi = new Usuario(datosPersonalesMock, null, null, null,
				condicionesJuanchi, null);
		franco = new Usuario(null, null, null, null, null, null);

		repoUsuarios = new RepoUsuarios(new ArrayList<Usuario>());

		repoUsuarios.add(juanchi);
		repoUsuarios.add(gaston);
		repoUsuarios.add(gustavo);

	}

	// Testea que se agrega y se elimina bien

	@Test
	public void eliminoJuanchi() {
		assertEquals(repoUsuarios.getUsuarios().size(), 3);
		repoUsuarios.remove(juanchi);
		assertEquals(repoUsuarios.getUsuarios().size(), 2);
	}

	// Testea que se agrega y se elimina bien
	@Test(expected = UsuarioIngresadoNoExisteException.class)
	public void eliminoAFrancoYTiraException() {
		repoUsuarios.remove(franco);
	}

	@Ignore
	@Test
	public void actualizoAJuanchi() {
		// segun que key lo actulizo??
	}

	@Test(expected = UsuarioIngresadoNoExisteException.class)
	public void intentoActualizarUsuarioInexistente() {
		repoUsuarios.update(franco);
	}

	@Ignore
	// comportamiento erratico en metodo :O
	@Test
	public void buscarJuanchiPorNombre() {
		when(juanchi.getNombre()).thenReturn("juanchi");
		when(gustavo.getNombre()).thenReturn("gustavo");
		when(gaston.getNombre()).thenReturn("gaston");
		DatosPersonales datosPersonales = new DatosPersonales("juanchi", null,
				null);
		Usuario juanchiConNombre = new Usuario(datosPersonales, null, null,
				null, null, null);
		assertEquals(repoUsuarios.get(juanchiConNombre), juanchi);
	}

	@Ignore
	// comportamiento erratico en metodo :O
	@Test
	public void buscarGusPorCondicionesDeSalud() {
		when(gustavo.getNombre()).thenReturn("gustavo");
		when(juanchi.getNombre()).thenReturn("juanchi");
		when(gaston.getNombre()).thenReturn("gaston");
		DatosPersonales datosPersonalesGus = new DatosPersonales(null, null,
				null);
		Collection<CondicionDeSalud> condiciones = new ArrayList<CondicionDeSalud>();
		condiciones.add(corporativo);
		condiciones.add(hippie);
		Usuario gustavoConCondiciones = new Usuario(datosPersonalesGus, null,
				null, null, condiciones, null);
		assertEquals(repoUsuarios.get(gustavoConCondiciones), gustavo);
	}

}
