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
	private Receta choripan2;
	private Usuario ramiro;
	private Usuario juanchi;
	
	private Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasRamiro;
	private Collection<Receta> recetasJuanchi;
	
    @Before
	public void setUp() {
    	ensalada = mock(Receta.class);
    	pancho = mock(Receta.class);
    	choripan = mock(Receta.class);
    	choripan2 = mock(Receta.class);
    	
    	recetasPublicas = Arrays.asList(ensalada, pancho); 
    	recetasRamiro = Arrays.asList(choripan); 
    	recetasJuanchi = new ArrayList<Receta>(); //no tiene recetas propias
    	
    	Usuario.recetasPublicas(recetasPublicas);
    	
    	ramiro = new Usuario(0, 0, null, null, null, null, recetasRamiro, null, null);
    	juanchi = new Usuario(0, 0, null, null, null, null, recetasJuanchi, null, null);
	}
    
	@Test
	public void JuanchiModificaRecetaPublica() {
		assertTrue(juanchi.getRecetasPropias().isEmpty());
		when(ensalada.esValida()).thenReturn(true);
		juanchi.modificarReceta(ensalada);
		assertTrue(juanchi.getRecetasPropias().contains(ensalada));
	}

	/* hay problemas para configurar test con efecto en los cuales hay que medir el efecto, sobre todo a la hora de remover un objeto de la lista
	@Test
	public void eliminarUnaRecetaPrivada(){
		assertTrue(ramiro.getRecetasPropias().contains(choripan));
		assertTrue(ramiro.getRecetasPropias().size() == 1);
		when(choripan.getNombre()).thenReturn("choripan");
		assertTrue(ramiro.getRecetasPropias().stream().filter(unaReceta -> unaReceta.getNombre().equals("choripan")).findAny().get().equals(choripan));
		//hasta aca anda
		//ramiro.eliminarRecetaPropia("choripan");
		
		ramiro.getRecetasPropias().add(ensalada);
		assertTrue(ramiro.getRecetasPropias().size() == 2);
		
	//	ramiro.getRecetasPropias().remove(choripan);
		//assertTrue(ramiro.getRecetasPropias().isEmpty());
	}
	/* hay problemas para configurar test con efecto en los cuales hay que medir el efecto
	@Test
	public void RamiroModificaRecetaPrivada(){
		assertTrue(ramiro.getRecetasPropias().contains(choripan));
		assertFalse(ramiro.getRecetasPropias().contains(choripan2));
		when(choripan.getNombre()).thenReturn("choripan");
		when(choripan2.getNombre()).thenReturn("choripan");
		when(choripan2.esValida()).thenReturn(true);
		when(ramiro.esRecetaPropia(choripan2)).thenReturn(true);
		assertTrue(ramiro.esRecetaPropia(choripan2));
		
		//ramiro.modificarReceta(choripan2);
		//assertTrue(ramiro.getRecetasPropias().contains(choripan2));
		//assertFalse(ramiro.getRecetasPropias().contains(choripan));
	}
	*/
	
}

