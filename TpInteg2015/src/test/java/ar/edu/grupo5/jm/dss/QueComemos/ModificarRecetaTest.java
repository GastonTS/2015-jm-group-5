package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class ModificarRecetaTest {
	private Receta ensalada;
	private Receta pancho;
	private Receta choripan;
	private Usuario ramiro;
	private Usuario juanchi;
	private Usuario gaston;
	
	private Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasRamiro;
	private Collection<Receta> recetasGaston;
	private Collection<Receta> recetasJuanchi;
	
    @Before
	public void setUp() {
    	ensalada = mock(Receta.class);
    	pancho = mock(Receta.class);
    	choripan = mock(Receta.class);

    	recetasPublicas = Arrays.asList(ensalada, pancho); 
    	recetasRamiro = Arrays.asList(choripan);
    	recetasGaston = Arrays.asList(pancho); 
    	recetasJuanchi = new ArrayList<Receta>(); //no tiene recetas propias
    	
    	Usuario.recetasPublicas(recetasPublicas);
    	
    	ramiro = new Usuario(0, 0, null, null, null, null, recetasRamiro, null, null);
    	juanchi = new Usuario(0, 0, null, null, null, null, recetasJuanchi, null, null);
    	gaston = new Usuario(0, 0, null, null, null, null, recetasGaston, null, null);
	}
    
	@Test
	public void JuanchiModificaRecetaPublica() {
		when(juanchi.esRecetaPropia(ensalada)).thenReturn(false);
		//when(ensalada.getNombre()).thenReturn("ensalada");
		
	//	juanchi.modificarReceta(ensalada);
		//assertTrue(juanchi.getRecetasPropias().contains(ensalada));
	}
/*	
	@Test
	public void UsuarioNoPuedeAccederAReceta() {
		when(juanchi.esRecetaPropia(choripan)).thenReturn(false);
		when(gaston.esRecetaPropia(choripan)).thenReturn(false);
		assertFalse(juanchi.puedeAcceder(choripan));
		assertFalse(gaston.puedeAcceder(choripan));
	}

	@Test
	public void UsuarioPuedeAccederARecetaPublica() {
		when(gaston.esRecetaPropia(ensalada)).thenReturn(true);
		when(juanchi.esRecetaPropia(pancho)).thenReturn(true);
		assertTrue(gaston.puedeAcceder(ensalada));
		assertTrue(juanchi.puedeAcceder(pancho));
	}
	
//no se desarrollan test de puedeModificar porque al momento de esta iteración puedeModificar y puedeAcceder hacen lo mismo
*/
}

