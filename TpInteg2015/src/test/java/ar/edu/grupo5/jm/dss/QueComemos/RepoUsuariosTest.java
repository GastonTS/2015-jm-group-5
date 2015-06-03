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
	private CondicionDeSalud hippie = mock(CondicionDeSalud.class);
	private CondicionDeSalud corporativo = mock(CondicionDeSalud.class);
	
	@Before
	public void setUp() {
		condicionesGustavo.add(hippie);
		condicionesGustavo.add(corporativo);
		condicionesGaston.add(corporativo);
		

		gustavo = new Usuario(datosPersonalesMock, null, null,
				null, condicionesGustavo, null);
		gaston = new Usuario(datosPersonalesMock, null,null, 
				null, condicionesGaston, null);
		juanchi = new Usuario(datosPersonalesMock, null, null,
				null, new ArrayList<CondicionDeSalud>(), null);
		franco = new Usuario(null, null, null,
				null, null, null);
		
		repoUsuarios = new RepoUsuarios(new ArrayList<Usuario>());
		
		repoUsuarios.add(juanchi);
		repoUsuarios.add(gaston);
		repoUsuarios.add(gustavo);
		
	}
	
	//Testea que se agrega y se elimina bien
	@Test
	public void eliminoJuanchi() {
		assertEquals(repoUsuarios.getUsuarios().size(),3);
		repoUsuarios.remove(juanchi);
		assertEquals(repoUsuarios.getUsuarios().size(),2);
	}
	
	//Testea que se agrega y se elimina bien
	@Test (expected = UsuarioIngresadoNoExisteException.class)
	public void eliminoAFrancoYTiraException() {
		repoUsuarios.remove(franco);
	}



}
