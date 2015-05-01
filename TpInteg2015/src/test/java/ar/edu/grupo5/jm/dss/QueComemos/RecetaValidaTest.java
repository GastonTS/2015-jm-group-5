package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RecetaValidaTest {

	private Receta receta;
	Collection<String> ingredientes = new ArrayList<String>();

	@Before
	public void setUp() {
		ingredientes.add("pimienta");
		ingredientes.add("sal");
		
		receta = new Receta(null, ingredientes, null, null, null, null, null, 1000);
	}
	
	@Test
	public void recetaValida() {
		assertTrue(receta.esValida());
	}

}
