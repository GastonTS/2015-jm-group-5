package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class SolicitudDeAprobacionTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	private Usuario poe;
	private Usuario borges;
	private Usuario byron;
	private Usuario verne;
	private Usuario darwin;

	private RepoUsuarios repoUsuarios;

	@Before
	public void setUp() {
		
		poe = new UsuarioBuilder()
				.setNombre("Edgar Allan Poe")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1809-01-19"))
				.setEstatura(1.73)
				.setPeso(75)
				.setRutina(Rutina.LEVE)
				.construirUsuario();
		
		borges = new UsuarioBuilder()
				.setNombre("Jorge Luis Borges")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1899-08-24"))
				.setEstatura(1.79)
				.setPeso(80)
				.setRutina(Rutina.NADA)
				.construirUsuario();
		
		verne = new UsuarioBuilder()
				.setNombre("Julio Berne")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1828-02-08"))
				.setEstatura(1.67)
				.setPeso(80)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();

		byron = new UsuarioBuilder()
				.setNombre("Augusta Ada Byron")
				.setSexo(Sexo.FEMENINO)
				.setFechaDeNacimiento(LocalDate.parse("1825-12-10"))
				.setEstatura(1.65)
				.setPeso(70)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
		
		darwin = new UsuarioBuilder()
				.setNombre("Charles Darwin")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1809-02-12"))
				.setEstatura(1.67)
				.setPeso(83)
				.setRutina(Rutina.INTENSIVA)
				.construirUsuario();
		
		repoUsuarios = RepoUsuarios.instancia;
		repoUsuarios.solicitaIngreso(poe);
		repoUsuarios.solicitaIngreso(borges);
		repoUsuarios.solicitaIngreso(verne);
		repoUsuarios.solicitaIngreso(byron);
	}

	@Test
	public void agregarCuatroSolicitudesDeUsuario() {
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(poe));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(verne));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(byron));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(borges));
		assertEquals(repoUsuarios.getSolicitudesDeIngreso().size(), 4);
	}

	@Test
	public void alRechazarUnUsuarioLoEliminaDeLasSolicitudes() {
		repoUsuarios.rechazaSolicitud(verne);
		
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(borges));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(poe));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(byron));
		assertEquals(repoUsuarios.getSolicitudesDeIngreso().size(), 3);
	}

	@Test
	public void alAceptarUnUsuarioLoEliminaDeLasSolicitudes() {
		assertEquals(repoUsuarios.getSolicitudesDeIngreso().size(), 4);

		repoUsuarios.apruebaSolicitud(poe);

		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(borges));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(verne));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(byron));
		assertEquals(repoUsuarios.getSolicitudesDeIngreso().size(), 3);

		assertTrue(repoUsuarios.getUsuariosAceptados().contains(poe));
		assertEquals(repoUsuarios.getUsuariosAceptados().size(), 1);
	}

	@Test(expected = UsuarioSinSolicitudDeIngresoExeption.class)
	public void aprobarSolicitudInexistenteLanzaExcepcion() {
		darwin.aceptar();
		repoUsuarios.apruebaSolicitud(darwin);
	}

	@Test(expected = UsuarioSinSolicitudDeIngresoExeption.class)
	public void rechazarSolicitudInexistenteLanzaExcepcion() {
		darwin.aceptar();
		repoUsuarios.rechazaSolicitud(darwin);
	}
}
