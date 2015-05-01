package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class RecetaValidaTest {

	private Receta recetaValida;
	private Receta recetaSinIngredientes;
	private Receta recetaConMuchasCalorias;
	Collection<String> ingredientes = new ArrayList<String>();
	Collection<String> ingredientesVacia = new ArrayList<String>();;
	@Before
	public void setUp() {
		ingredientes.add("pimienta");
		ingredientes.add("sal");
		
		recetaValida = new Receta(null, ingredientes, null, null, null, null, null, 1000);
		recetaConMuchasCalorias = new Receta(null, ingredientes, null, null, null, null, null, 99999);
		recetaSinIngredientes = new Receta(null, ingredientesVacia, null, null, null, null, null, 1000);
	}
	
	@Test
	public void recetaValida() {
		assertTrue(recetaValida.esValida());
	}
	
	@Test
	public void recetasNoValida() {
		assertFalse(recetaSinIngredientes.esValida());
		assertFalse(recetaConMuchasCalorias.esValida());
	}
}
