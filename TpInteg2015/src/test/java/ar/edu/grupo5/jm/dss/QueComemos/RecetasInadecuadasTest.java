package ar.edu.grupo5.jm.dss.QueComemos;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class RecetasInadecuadasTest {
	
	private Receta recetaFalsa;
    private Vegano vegano;
    private Hipertenso hipertenso;
    private Diabetico diabetico;
    private Usuario arthasElSano;
    private Usuario archimondeElAsmatico;

	
    @Before
	public void setUp() {	
	recetaFalsa = mock(Receta.class);
    }
    
    
	
	
	@Test
	public void RecetaValidaHipertenso(){
		when(recetaFalsa.tenesAlgoDe(Hipertenso.getCondimentosProhibidos())).thenReturn(false);
		assert (hipertenso.esInadecuada(recetaFalsa));
	}
	
	@Test
	public void RecetaValidaDiabetico(){
		when(recetaFalsa.tenesMasDe(Diabetico.GetCondimentosProhibidos())).thenReturn(false);
		assert (diabetico.esInadecuada(recetaFalsa));
	}
	
	@Test
	public void RecetaValidaVegano(){
		when(recetaFalsa.tenesAlgunIngredienteDeEstos(Vegano.getPreferenciasprohibidas())).thenReturn(false);
		assert (vegano.esInadecuada(recetaFalsa));
	}
/*	
	@Test
	public void RecetaInadecuadaParaArchimondeElAsmatico(){
		when(archimondeElAsmatico.getCondicionesPreexistentes().stream().anyMatch(condicion -> condicion.esInadecuada(recetaFalsa))).thenReturn(true);
		assert (archimondeElAsmatico.sosRecetaInadecuadaParaMi(recetaFalsa));
	}
	@Test
	public void RecetaAdecuadaParaArthasElSano(){
		when(arthasElSano.getCondicionesPreexistentes().stream().anyMatch(condicion -> condicion.esInadecuada(recetaFalsa))).thenReturn(false);
		assertFalse(arthasElSano.sosRecetaInadecuadaParaMi(recetaFalsa));
	}*/
}
