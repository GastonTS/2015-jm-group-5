package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class UsuarioAccesoTest {

	private Receta choripanMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	
	private Usuario dueñoDelChori;
	private Usuario dueñoDelPancho;
	
	
	@Before
	public void setUp() {
		Usuario.recetasPublicas(Arrays.asList(ensaladaMock));
		dueñoDelChori = new Usuario(null,null,0,0,null,Arrays.asList(choripanMock),null,null);
		dueñoDelPancho = new Usuario(null,null,0,0,null,Arrays.asList(panchoMock),null,null);
	}

	@Test
	public void UsuarioPuedeAccederARecetaPropia() {
		assertTrue(dueñoDelChori.puedeAcceder(choripanMock));
	}

	@Test
	public void UsuarioNoPuedeAccederAReceta() {
		assertFalse(dueñoDelChori.puedeAcceder(panchoMock));
	}

	@Test
	public void UsuarioPuedeAccederARecetaPublica() {
		assertTrue(dueñoDelPancho.puedeAcceder(ensaladaMock));
	}

}
