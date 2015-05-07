package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
	private Usuario nacioHoy;
	
	private Usuario demasiadoICM;
	private Usuario pocoICM;
	private Usuario usuarioDiabeticoRutinaAlata;
	
	private Vegano condicionVegano;
	private Celiaco condicionCeliaco ;
	private Diabetico condicionDiabetico;
	
	private Collection<String> preferenciaFruta;
	private Collection<String> preferenciasVariadas;
	
	private Collection<CondicionPreexistente> coleccionCondicionVegano;
	private Collection<CondicionPreexistente> coleccionCondicionCeliaco;
	private Collection<CondicionPreexistente> coleccionCondicionDiabetico;
	private Collection<CondicionPreexistente> condiciones = new ArrayList<CondicionPreexistente>();
	private CondicionPreexistente hippie = mock(CondicionPreexistente.class);
	private CondicionPreexistente corporativo = mock(CondicionPreexistente.class);
	
	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta choripanMock = mock(Receta.class);
	private Receta choripanModificadoMock = mock(Receta.class);
	
	private Collection<Receta> recetasPublicas = new ArrayList<Receta>();
	private Collection<Receta> recetasGaston = new ArrayList<Receta>();
	private Collection<Receta> recetasJuanchi = new ArrayList<Receta>();//Queda sin recetas
	private Collection<Receta> recetasGustavo = new ArrayList<Receta>();	
	
	@Before
	public void setUp() {
		condiciones.add(hippie);
		condiciones.add(corporativo);
		
		recetasPublicas.add(ensaladaMock);
		recetasPublicas.add(panchoMock);
		recetasGustavo.add(choripanMock);
		recetasGaston.add(panchoMock);
		
		
		coleccionCondicionVegano = new ArrayList<CondicionPreexistente>();
		coleccionCondicionVegano.add(condicionVegano);
		coleccionCondicionCeliaco = new ArrayList<CondicionPreexistente>();
		coleccionCondicionCeliaco.add(condicionCeliaco);
		coleccionCondicionDiabetico = new ArrayList<CondicionPreexistente>();
		coleccionCondicionDiabetico.add(condicionDiabetico);
		preferenciaFruta = new ArrayList<String>();
		preferenciaFruta.add("fruta");
		preferenciasVariadas = new ArrayList<String>();
		preferenciasVariadas.add("fruta");
		preferenciasVariadas.add("semillas");
		preferenciasVariadas.add("champignones");
		
		Usuario.recetasPublicas(recetasPublicas);
		gustavo = new Usuario(73, 1.83, "Gustavo", LocalDate.parse("1994-02-25"), null, null, recetasGustavo, condiciones, "Mediano");
		leandro = new Usuario(79, 1.78, "leandro", null, preferenciaFruta, null, null, coleccionCondicionVegano, "Mediano"); //No tiene fecha y es vegano (con preferencia fruta)
		ramiro = new Usuario(63, 1.75, null, LocalDate.parse("2000-01-01"), null, null, null, coleccionCondicionCeliaco, "Mediano"); //No tiene nombre
		gaston = new Usuario(65, 1.66, "gast", null, null, null, recetasGaston, null, null); //Tiene Nombre corto
		juanchi = new Usuario(70, 1.85, "juanchi", LocalDate.parse("2000-01-01"), null, null, recetasJuanchi, coleccionCondicionDiabetico, null); //No tiene rutina y es diabetico
		
		sinPeso = new Usuario(0, 1.83, "falta peso", LocalDate.parse("2000-01-01"), null, null, null, condiciones, "Mediano");
		sinEstatura = new Usuario(73, 0, "falta estatura", LocalDate.parse("2000-01-01"), null, null, null, condiciones, "Mediano");
		nacioHoy = new Usuario(73, 1.83, "Nació hoy", LocalDate.now(), null, null, null, condiciones, "Mediano");
		usuarioDiabeticoRutinaAlata = new Usuario(71, 1.70, null, null, null, null, null, coleccionCondicionDiabetico, "Alta");
		demasiadoICM = new Usuario(101, 1.83, "demasiadoICM", LocalDate.parse("2000-01-01"), null, null, null, condiciones, "Mediano");
		pocoICM = new Usuario(60, 1.83, "pocoICM", LocalDate.parse("2000-01-01"), null, null, null, condiciones, "Mediano");
				
	} 
	
	//Test de Validez de usuario
	 
	@Test
	public void gustavoEsValido() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		assertTrue(gustavo.esUsuarioValido());
	}
	
	@Test
	public void faltaUnCampoObligatorio() {
		assertFalse(sinPeso.tieneCamposObligatorios());
		assertFalse(sinEstatura.tieneCamposObligatorios());
		assertFalse(leandro.tieneCamposObligatorios());
		assertFalse(ramiro.tieneCamposObligatorios());
		assertFalse(juanchi.tieneCamposObligatorios());
	}
	
	@Test
	public void elNombreEsCorto() {
		assertFalse(gaston.esNombreCorto());
	}
	
	@Test
	public void fechaNoEsMenorAHoy() {
		assertFalse(nacioHoy.fechaDeNacimientoAnteriorAHoy());
	}
	
	
	@Test
	public void noEsValidoSiSusCondicionesNoLoPermiten() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(false);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		assertFalse(gustavo.esUsuarioValidoParaSusCondiciones());
	}
	
	@Test
	public void siNoCumplenAlgunaCondicionSonInvalidos() {
			when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(false);
			when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);

			assertFalse(ramiro.esUsuarioValido());
			assertFalse(sinPeso.esUsuarioValido());
			assertFalse(sinEstatura.esUsuarioValido());
			assertFalse(leandro.esUsuarioValido());
			assertFalse(juanchi.esUsuarioValido());
			assertFalse(gaston.esUsuarioValido());
			assertFalse(nacioHoy.esUsuarioValido());
			assertFalse(gustavo.esUsuarioValido());
	}
	
	
	//Test de IMCD
	
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
	
	
	
	//Test de Rutina saludable
	@Test
	public void sigueRutinaSaludableConCondiciones() {
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		when(corporativo.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		assertTrue(gustavo.sigueRutinaSaludable());
	}
	
	@Test
	public void sigueRutinaSaludableSinCondiciones() {
		assertTrue(gaston.sigueRutinaSaludable());
	}
	
	@Test
	public void noSigueRutinaSaludableSiNoSubsanaUnaCondicion() {
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(false);
		when(corporativo.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		assertFalse(gustavo.sigueRutinaSaludable());
	}
	
	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMenorA18() {
		assertFalse(pocoICM.sigueRutinaSaludable());
	}
	
	@Test 
	public void noSigueRutinaSaludableSiNoTieneICMMayorA30() {
		assertFalse(demasiadoICM.sigueRutinaSaludable());
	}		
	
	public void tieneAlgunaDeEstasPreferenciasTest(){
		assertTrue(leandro.tieneAlgunaDeEstasPreferencias(preferenciasVariadas));
	}
	
	@Test
	public void usuarioVeganoPrefiereFruta(){
		assertTrue(leandro.tienePreferencia("fruta"));
	}
	
	@Test
	public void veganoQueLeGustanLAsFrutas(){
		assert(leandro.sigueRutinaSaludable());
	}
	
	@Test
	public void celiacoCumpleSiCumpleIMCD(){
		assert(ramiro.sigueRutinaSaludable());
	}
	
	@Test
	public void diabeticoCumpleIMCPesaMenosDe70(){
		assert(juanchi.sigueRutinaSaludable());
	}
	
	@Test
	public void diabeticoCumpleIMCConRutinaAlta(){
		assert(usuarioDiabeticoRutinaAlata.sigueRutinaSaludable());
	}
	
	//Tests Creacion de Recetas
	@Test 
	public void usuarioCreaRecetaExitosa() {
		when(recetaMock.esValida()).thenReturn(true);
		gustavo.crearReceta(recetaMock);
	}		
	
	@Test(expected=RecetaNoValidaException.class)
	public void usuarioCreaRecetaFallida() {
		when(recetaMock.esValida()).thenReturn(false);
		gustavo.crearReceta(recetaMock);
	}		
	
	//Test de Acceso a recetas
	
	@Test
	public void UsuarioPuedeAccederARecetaPropia() {
		when(gustavo.esRecetaPropia(choripanMock)).thenReturn(true);
		when(gaston.esRecetaPropia(panchoMock)).thenReturn(true);
		assertTrue(gustavo.puedeAcceder(choripanMock));
		assertTrue(gaston.puedeAcceder(panchoMock));
	}
	
	@Test
	public void UsuarioNoPuedeAccederAReceta() {
		when(juanchi.esRecetaPropia(choripanMock)).thenReturn(false);
		when(gaston.esRecetaPropia(choripanMock)).thenReturn(false);
		assertFalse(juanchi.puedeAcceder(choripanMock));
		assertFalse(gaston.puedeAcceder(choripanMock));
	}

	@Test
	public void UsuarioPuedeAccederARecetaPublica() {
		when(gaston.esRecetaPropia(ensaladaMock)).thenReturn(true);
		when(juanchi.esRecetaPropia(panchoMock)).thenReturn(true);
		assertTrue(gaston.puedeAcceder(ensaladaMock));
		assertTrue(juanchi.puedeAcceder(panchoMock));
	}
	//no se desarrollan test de puedeModificar porque al momento de esta iteración puedeModificar y puedeAcceder hacen lo mismo
	
	//Tests Modificacion de Recetas
	
	@Test
	public void JuanchiModificaRecetaPublica() {
		assertTrue(juanchi.getRecetasPropias().isEmpty());
		when(ensaladaMock.esValida()).thenReturn(true);
		juanchi.modificarReceta(ensaladaMock);
		assertTrue(juanchi.getRecetasPropias().contains(ensaladaMock));
	}
	

}
