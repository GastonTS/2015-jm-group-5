package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GestorCondicionesTest {

	private Celiaco celiaco = mock(Celiaco.class);
	private Diabetico diabetico = mock(Diabetico.class);
	private Hipertenso hipertenso = mock(Hipertenso.class);
	private Vegano vegano = mock(Vegano.class);
	
	private Receta receta = mock(Receta.class);
	
	private GestorCondiciones gestor;
	
	private Collection<CondicionPreexistente> condicionesAdecuadas;
	
    @Before
	public void setUp() {
    	gestor = new GestorCondiciones(Arrays.asList(celiaco,diabetico,hipertenso,vegano));
    } 
	
    @Test
    public void lasCondicionesQueSonValidasSonLasNoInadecuadas() {
    	when(celiaco.esInadecuada(any(Receta.class))).thenReturn(false);
    	when(hipertenso.esInadecuada(any(Receta.class))).thenReturn(false);
    	when(diabetico.esInadecuada(any(Receta.class))).thenReturn(true);
    	when(vegano.esInadecuada(any(Receta.class))).thenReturn(true);
    	
    	condicionesAdecuadas=gestor.condicionesALasQueEsInadecuada(receta).collect(Collectors.toList());
    	
    	assertFalse(condicionesAdecuadas.contains(celiaco));
    	assertFalse(condicionesAdecuadas.contains(hipertenso));
    	assertTrue(condicionesAdecuadas.contains(diabetico));
    	assertTrue(condicionesAdecuadas.contains(vegano));
    	
    	verify(celiaco,times(1)).esInadecuada(any(Receta.class));
    	verify(hipertenso,times(1)).esInadecuada(any(Receta.class));
    	verify(diabetico,times(1)).esInadecuada(any(Receta.class));
    	verify(vegano,times(1)).esInadecuada(any(Receta.class));
    }
    
}
