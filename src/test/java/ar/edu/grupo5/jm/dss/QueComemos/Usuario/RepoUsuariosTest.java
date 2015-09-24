package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;

public class RepoUsuariosTest {

	private Usuario gustavo = mock(Usuario.class);
	private Usuario cerati = mock(Usuario.class);
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

	@Test
	public void buscarJuanchiPorNombre() {
		UsuarioBuscado usuarioBusquedaJuanchi = new UsuarioBuscado("juanchi", new ArrayList<CondicionDeSalud>());
		
		assertEquals(repoUsuarios.buscarUnUsuarioConNombre(usuarioBusquedaJuanchi), Optional.of(juanchi));
	}

	@Test
	public void buscarGustavosHippies() {
		UsuarioBuscado usuarioBusquedaGustavosHippies = new UsuarioBuscado("gustavo", condicionesGustavo);

		assertEquals(repoUsuarios.listarPorNombreYCondiciones(usuarioBusquedaGustavosHippies), Arrays.asList(gustavo));
	}

	@Test
	public void buscarGustavosSinCondicion() {
		UsuarioBuscado usuarioBusquedaGustavosHippies = new UsuarioBuscado("gustavo", new ArrayList<CondicionDeSalud>());
		
		assertEquals(repoUsuarios.listarPorNombreYCondiciones(usuarioBusquedaGustavosHippies), Arrays.asList(gustavo, cerati));
	}
	
	@Test
	public void actualizarUsuario() {
		
		DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);
		Complexion complexionMock = mock(Complexion.class);
		
		Usuario usuarioViejo = new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.setRutina(Rutina.INTENSIVA)
				.construirUsuario();

		Usuario usuarioNuevo = new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.agregarPreferenciaAlimenticia(new Ingrediente("fruta"))
				.setRutina(Rutina.ALTA)
				.construirUsuario();
	
		assertFalse(repoUsuarios.sonObjetosActualizados(usuarioViejo, usuarioNuevo));
		
		repoUsuarios.update(usuarioViejo, usuarioNuevo);
		
		assertTrue(repoUsuarios.sonObjetosActualizados(usuarioViejo, usuarioNuevo));
	
	}
}
