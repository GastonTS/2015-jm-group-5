package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Celiaco;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;

public class RepoUsuariosTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	private Usuario gustavo;
	private Usuario cerati;
	private Usuario gaston;
	private Usuario juanchi;
	private Usuario franco = mock(Usuario.class);
	
	private RepoUsuarios repoUsuarios;

	private Collection<CondicionDeSalud> condicionesGustavo = new ArrayList<CondicionDeSalud>();
	private CondicionDeSalud hippie;
	private CondicionDeSalud corporativo;

	@Before
	public void setUp() {
		hippie = new Celiaco();
		corporativo = new Celiaco();
		
		condicionesGustavo.add(hippie);
		condicionesGustavo.add(corporativo);
		
		juanchi = new UsuarioBuilder().setNombre("juanchi")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1000-01-01"))
				.setPeso(80)
				.setEstatura(2.00)
				.agregarCondicionesDeSalud(hippie)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
		gustavo = new UsuarioBuilder().setNombre("gustavo")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1000-01-01"))
				.setPeso(80)
				.setEstatura(2.00)
				.agregarCondicionesDeSalud(hippie)
				.agregarCondicionesDeSalud(corporativo)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
		gaston = new UsuarioBuilder().setNombre("gaston")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1000-01-01"))
				.setPeso(80)
				.setEstatura(2.00)
				.agregarCondicionesDeSalud(corporativo)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
		cerati = new UsuarioBuilder().setNombre("gustavo")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1000-01-01"))
				.setPeso(80)
				.setEstatura(2.00)
				.agregarCondicionesDeSalud(hippie)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
		
		repoUsuarios = new RepoUsuarios(new ArrayList<Usuario>());

		repoUsuarios.solicitaIngreso(juanchi);
		repoUsuarios.solicitaIngreso(gaston);
		repoUsuarios.solicitaIngreso(gustavo);
		repoUsuarios.solicitaIngreso(cerati);
	}

	// Testea que se agrega y se elimina bien

	@Ignore
	@Test
	public void eliminoJuanchi() {
		repoUsuarios.apruebaSolicitud(juanchi);
		assertTrue(repoUsuarios.getUsuariosAceptados().contains(juanchi));
		
		repoUsuarios.remove(juanchi);
		repoUsuarios.rechazaSolicitud(juanchi);
		assertFalse(repoUsuarios.getUsuariosAceptados().contains(juanchi));
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

	@Ignore
	@Test
	public void buscarGustavosHippies() {
		UsuarioBuscado usuarioBusquedaGustavosHippies = new UsuarioBuscado("gustavo", condicionesGustavo);

		assertEquals(repoUsuarios.listarPorNombreYCondiciones(usuarioBusquedaGustavosHippies), Arrays.asList(gustavo));
	}

	@Ignore
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
