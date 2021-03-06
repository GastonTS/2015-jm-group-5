package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ComplexionTest {
	private Complexion complexionGaston;
	private Complexion complexionGustavo;
	private Complexion complexionJuanchi;
	private Complexion complexionLean;
	private Complexion complexionRamiro;

	@Before
	public void setUp() {
		complexionGaston = new Complexion(65, 1.66);
		complexionGustavo = new Complexion(73, 1.83);
		complexionJuanchi = new Complexion(70, 1.85);
		complexionLean = new Complexion(79, 1.78);
		complexionRamiro = new Complexion(63, 1.75);
	}

	@Test
	public void juanchiTieneIMCDe2045() {
		assertEquals(20.45, complexionJuanchi.indiceMasaCorporal(), 0.01);
	}

	@Test
	public void gustavoTieneIMCDe2180() {
		assertEquals(21.80, complexionGustavo.indiceMasaCorporal(), 0.01);

	}

	@Test
	public void leandroTieneIMCDe2493() {
		assertEquals(24.93, complexionLean.indiceMasaCorporal(), 0.01);
	}

	@Test
	public void ramiroTieneIMCDe2057() {
		assertEquals(20.57, complexionRamiro.indiceMasaCorporal(), 0.01);
	}

	@Test
	public void gastonTieneIMCDe2358() {
		assertEquals(23.58, complexionGaston.indiceMasaCorporal(), 0.01);
	}

	@Test
	public void esComplexionValida() {
		assertTrue(complexionGaston.esComplexionValida());
	}

	@Test(expected = ComplexionNoValidoException.class)
	public void noEsComplexionValidaSinPeso() {
		new Complexion(0, 1.83);
	}

	@Test(expected = ComplexionNoValidoException.class)
	public void noEsComplexionValidaSinEstatura() {
		new Complexion(73, 0);
	}

}
