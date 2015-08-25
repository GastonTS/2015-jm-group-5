package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

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

import ar.edu.grupo5.jm.dss.QueComemos.RepoUsuarios;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioIngresadoNoExisteException;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;

public class SolicitudDeAprobacionTest {
	
	private Usuario franco = mock(Usuario.class);
	private Usuario alf = mock(Usuario.class);
	private Usuario zaffa = mock(Usuario.class);
	private Usuario quique = mock(Usuario.class);
	
	private RepoUsuarios repoUsuarios;
	
	@Before
	public void setUp() {
		repoUsuarios = new RepoUsuarios(new ArrayList<Usuario>());
		
		repoUsuarios.solicitaIngreso(alf);
		repoUsuarios.solicitaIngreso(zaffa);
		repoUsuarios.solicitaIngreso(quique);
		repoUsuarios.solicitaIngreso(franco);
	}

	@Test
	public void agregarCuatroSolicitudesDeUsuario() {
	
		//uso contains en vez de containsAll ya que este ultimo considera el orden, cosa que no me da mucha confianza
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(alf));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(zaffa));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(quique));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(franco));
		assertEquals(repoUsuarios.getSolicitudesDeIngreso().size(),4);
	}
	
	@Test
	public void alEliminarUnUsuarioLoEliminaDeLasSolicitudes() {
		repoUsuarios.rechazaSolicitud(quique);
		
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(alf));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(zaffa));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(franco));
		assertEquals(repoUsuarios.getSolicitudesDeIngreso().size(),3);
	}
		
	@Test
	public void alAceptarUnUsuarioLoEliminaDeLasSolicitudes() {
		assertTrue(repoUsuarios.getUsuarios().isEmpty());
		
		repoUsuarios.apruebaSolicitud(alf);
		
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(franco));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(zaffa));
		assertTrue(repoUsuarios.getSolicitudesDeIngreso().contains(quique));
		assertEquals(repoUsuarios.getSolicitudesDeIngreso().size(),3);
		
		assertTrue(repoUsuarios.getUsuarios().contains(alf));
		assertEquals(repoUsuarios.getUsuarios().size(),1);
	}
}
