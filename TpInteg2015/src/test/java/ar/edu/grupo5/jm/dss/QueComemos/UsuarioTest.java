package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {
	
	private Usuario leandro;
	private Usuario gustavo;
	private Usuario ramiro;
	
	@Before
	public void setUp() {
		gustavo = new Usuario(73, 1.83);
		leandro = new Usuario(79, 1.78);
		ramiro = new Usuario(63, 1.75);
	}
	

	@Test
	public void gustavoTieneIMCDe2180(){
		assertEquals(21.80,gustavo.indiceMasaCorporal(),0.01);
		
	}
	@Test 
	public void leandroTieneIMCDe2493(){
		assertEquals(24.93,leandro.indiceMasaCorporal(),0.01);
	}
	@Test
	public void ramiroTieneIMCDe2057(){
		assertEquals(20.57, ramiro.indiceMasaCorporal(), 0.01);
	}
}
