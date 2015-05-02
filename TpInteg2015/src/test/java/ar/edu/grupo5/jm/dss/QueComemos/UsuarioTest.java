package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {
	
	private Usuario leandro;
	private Usuario gustavo;
	private Usuario ramiro;
	private Usuario gaston;
	private Usuario juanchi;
	
	private Usuario sinPeso;
	private Usuario sinEstatura;
	private Usuario sinFecha;
	private Usuario sinNombre;
	private Usuario sinRutina;
	
	private Usuario nombreCorto;
	private Usuario nacioHoy;
	private Collection<String> preferenciaLean;
	@Before
	public void setUp() {
		preferenciaLean = new ArrayList<String>();
		preferenciaLean.add("fruta");
		gustavo = new Usuario(73, 1.83, "Gustavo", LocalDate.parse("1994-02-25"), null, null, null, null, null, "Mediano");
		leandro = new Usuario(79, 1.78, "leandro", null, preferenciaLean, null, null, null, null, null);
		ramiro = new Usuario(63, 1.75, null, null, null, null, null, null, null, null);
		gaston = new Usuario(65, 1.66, null, null, null, null, null, null, null, null);
		juanchi = new Usuario(70, 1.85, null, null, null, null, null, null, null, null);
		
		sinPeso = new Usuario(0, 1.83, "falta peso", LocalDate.parse("2000-01-01"), null, null, null, null, null, "Mediano");
		sinEstatura = new Usuario(73, 0, "falta estatura", LocalDate.parse("2000-01-01"), null, null, null, null, null, "Mediano");
		sinFecha = new Usuario(73, 1.83, "falta fecha", null, null, null, null, null, null, "Mediano");
		sinNombre = new Usuario(73, 1.83, null, LocalDate.parse("2000-01-01"), null, null, null, null, null, "Mediano");
		sinRutina = new Usuario(73, 1.83, "falta rutina", LocalDate.parse("2000-01-01"), null, null, null, null, null, null);
		
		nombreCorto = new Usuario(73, 1.83, "cort", LocalDate.parse("2000-01-01"), null, null, null, null, null, "Mediano");
		
		nacioHoy = new Usuario(73, 1.83, "Nació hoy", LocalDate.now(), null, null, null, null, null, "Mediano");
	} 
	
	@Test
	public void gustavoEsValido() {
		assert(gustavo.esUsuarioValido());
	}
	
	@Test
	public void noEsValidoSiFaltaUnCampoObligatorio() {
		assertFalse(sinPeso.esUsuarioValido());
		assertFalse(sinEstatura.esUsuarioValido());
		assertFalse(sinFecha.esUsuarioValido());
		assertFalse(sinNombre.esUsuarioValido());
		assertFalse(sinRutina.esUsuarioValido());
	}
	
	@Test
	public void noEsValidoSiElNombreEsCorto() {
		assertFalse(nombreCorto.esUsuarioValido());
	}
	
	@Test
	public void noEsValidoSiFechaNoEsMenorAHoy() {
		assertFalse(nacioHoy.esUsuarioValido());
	}
	
	
	
	
	@Test
	public void juanchiTieneIMCDe2045(){
		assertEquals(20.45,juanchi.indiceMasaCorporal(),0.01);
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
	
	@Test
	public void gastonTieneIMCDe2358(){
		assertEquals(23.58, gaston.indiceMasaCorporal(), 0.01);
	}
	
	@Test
	public void leanPrefiereFruta(){
		assert(leandro.tienePreferencia("fruta"));
	}
}
