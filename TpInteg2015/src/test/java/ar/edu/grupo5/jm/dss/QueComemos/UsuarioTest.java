package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {
	
	private Usuario leandro;
	private Usuario gustavo;
	
	@Before
	public void setUp() {
		gustavo = new Usuario(73, 1.83);
		leandro = new Usuario(79, 1.78);
	}
	

	@Test
	public void gustavoTieneIMCDe2180(){
		assertEquals(21.80,gustavo.indiceMasaCorporal(),0.01);
		
	}
	@Test 
	public void leandroTieneIMCDe2499(){
		assertEquals(24.93,leandro.indiceMasaCorporal(),0.01);
	}
}
