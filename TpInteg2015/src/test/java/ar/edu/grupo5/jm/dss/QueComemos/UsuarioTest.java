package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

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
	
	private Usuario conCondicionMockeada;
	private Collection<CondicionPreexistente> condiciones = new ArrayList<CondicionPreexistente>();
	private CondicionPreexistente condicion1 = mock(CondicionPreexistente.class);;
	private CondicionPreexistente condicion2 = mock(CondicionPreexistente.class);;
	
	private Usuario demasiadoICM;
	private Usuario pocoICM;
	
	private Collection<String> preferenciaLean;
	
	
	
	@Before
	public void setUp() {
		condiciones.add(condicion1);
		condiciones.add(condicion2);
		
		preferenciaLean = new ArrayList<String>();
		preferenciaLean.add("fruta");
		gustavo = new Usuario(73, 1.83, "Gustavo", LocalDate.parse("1994-02-25"), null, null, null, null, "Mediano");
		leandro = new Usuario(79, 1.78, "leandro", null, preferenciaLean, null, null, null, null);
		ramiro = new Usuario(63, 1.75, null, null, null, null, null, null, null);
		gaston = new Usuario(65, 1.66, null, null, null, null, null, null, null);
		juanchi = new Usuario(70, 1.85, null, null, null, null, null, null, null);
		
		sinPeso = new Usuario(0, 1.83, "falta peso", LocalDate.parse("2000-01-01"), null, null, null, null, "Mediano");
		sinEstatura = new Usuario(73, 0, "falta estatura", LocalDate.parse("2000-01-01"), null, null, null, null, "Mediano");
		sinFecha = new Usuario(73, 1.83, "falta fecha", null, null, null, null, null, "Mediano");
		sinNombre = new Usuario(73, 1.83, null, LocalDate.parse("2000-01-01"), null, null, null, null, "Mediano");
		sinRutina = new Usuario(73, 1.83, "falta rutina", LocalDate.parse("2000-01-01"), null, null, null, null, null);
		
		nombreCorto = new Usuario(73, 1.83, "cort", LocalDate.parse("2000-01-01"), null, null, null, null, "Mediano");
		
		nacioHoy = new Usuario(73, 1.83, "Nació hoy", LocalDate.now(), null, null, null, null, "Mediano");
		
		conCondicionMockeada = new Usuario(73, 1.83, "conCondicionMockeada", LocalDate.parse("2000-01-01"), null, null, null, condiciones, "Mediano");
		
		demasiadoICM = new Usuario(101, 1.83, "demasiadoICM", LocalDate.parse("2000-01-01"), null, null, null, condiciones, "Mediano");
		pocoICM = new Usuario(60, 1.83, "pocoICM", LocalDate.parse("2000-01-01"), null, null, null, condiciones, "Mediano");
		
	} 
	
	//Punto 1
	
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
	public void noEsValidoSiSusCondicionesNoLoPermiten() {
		when(condicion1.esUsuarioValido(any(Usuario.class))).thenReturn(false);
		when(condicion2.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		assertFalse(conCondicionMockeada.esUsuarioValido());
	}
	
	
	//Punto 2.a
	
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
	
	
	
	//Punto 2.b
	@Test
	public void noSigueRutinaSaludableSiNoSubsanaUnaCondicion() {
		when(condicion1.subsanaCondicion(any(Usuario.class))).thenReturn(false);
		when(condicion2.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		assertFalse(conCondicionMockeada.sigueRutinaSaludable());
	}
	
	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMenorA18() {
		assertFalse(pocoICM.sigueRutinaSaludable());
	}
	
	@Test 
	public void noSigueRutinaSaludableSiNoTieneICMMayorA30() {
		assertFalse(demasiadoICM.sigueRutinaSaludable());
	}		
	
	
	
	
	
	@Test
	public void leanPrefiereFruta(){
		assert(leandro.tienePreferencia("fruta"));
	}
	
	@Test
	public void juanchiSigueRutinaSaludable(){
		assert(juanchi.sigueRutinaSaludable());
	}
}
