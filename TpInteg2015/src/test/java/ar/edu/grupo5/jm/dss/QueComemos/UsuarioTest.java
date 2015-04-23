package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UsuarioTest {

	@Test
	public void gustavoTieneIMCDe2180(){
		
		Usuario gustavo = new Usuario(73, 1.83);
		
		assertEquals(21.80,gustavo.indiceMasaCorporal(),0.01);
	}
}
