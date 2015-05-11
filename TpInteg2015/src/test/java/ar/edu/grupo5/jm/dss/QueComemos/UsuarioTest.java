package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Rutina;

public class UsuarioTest {

	private Usuario leandro;
	private Usuario gustavo;
	private Usuario ramiro;
	private Usuario gaston;
	private Usuario juanchi;
	private Usuario juanchiSinRutina;

	private Usuario sinPeso;
	private Usuario sinEstatura;
	private Usuario nacioHoy;

	private Usuario demasiadoICM;
	private Usuario pocoICM;
	private Usuario usuarioDiabeticoRutinaAlata;

	private DatosPersonales datosPersonalesGenericos;
	private DatosPersonales datosPersonalesNacidoHoy;
	private DatosPersonales datosPersonalesNombreCorto;
	private DatosPersonales datosPersonalesLeandro;
	private DatosPersonales datosPersonalesRamiro;
	private DatosPersonales datosPersonalesGaston;
	private DatosPersonales datosPersonalesGustavo;
	private DatosPersonales datosPersonalesJuanchi;

	private Complexion complexionLeandro;
	private Complexion complexionGustavo;
	private Complexion complexionRamiro;
	private Complexion complexionGaston;
	private Complexion complexionJuanchi;
	private Complexion complexionSinPeso;
	private Complexion complexionSinEstatura;
	private Complexion complexionDemasiadoIMC;
	private Complexion complexionPocoIMC;

	private Vegano condicionVegano = new Vegano();
	private Celiaco condicionCeliaco = new Celiaco();
	private Diabetico condicionDiabetico = new Diabetico();

	private Collection<String> preferenciaFruta;
	private Collection<String> preferenciasVariadas;

	private Collection<CondicionDeSalud> coleccionCondicionVegano;
	private Collection<CondicionDeSalud> coleccionCondicionCeliaco;
	private Collection<CondicionDeSalud> coleccionCondicionDiabetico;
	private Collection<CondicionDeSalud> condiciones = new ArrayList<CondicionDeSalud>();
	private CondicionDeSalud hippie = mock(CondicionDeSalud.class);
	private CondicionDeSalud corporativo = mock(CondicionDeSalud.class);

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta nuevaEnsaladaMock = mock(Receta.class);
	private Receta choripanMock = mock(Receta.class);
	private Receta choriConChimiMock = mock(Receta.class);

	private Collection<Receta> recetasPublicas = new ArrayList<Receta>();
	private Collection<Receta> recetasGaston = new ArrayList<Receta>();
	private Collection<Receta> recetasJuanchi = new ArrayList<Receta>();// Queda
																		// sin
																		// recetas
	private Collection<Receta> recetasGustavo = new ArrayList<Receta>();

	@Before
	public void setUp() {
		condiciones.add(hippie);
		condiciones.add(corporativo);

		recetasPublicas.add(ensaladaMock);
		recetasPublicas.add(panchoMock);
		recetasGustavo.add(choripanMock);
		recetasGaston.add(panchoMock);

		coleccionCondicionVegano = new ArrayList<CondicionDeSalud>();
		coleccionCondicionVegano.add(condicionVegano);
		coleccionCondicionCeliaco = new ArrayList<CondicionDeSalud>();
		coleccionCondicionCeliaco.add(condicionCeliaco);
		coleccionCondicionDiabetico = new ArrayList<CondicionDeSalud>();
		coleccionCondicionDiabetico.add(condicionDiabetico);
		preferenciaFruta = new ArrayList<String>();
		preferenciaFruta.add("fruta");
		preferenciasVariadas = new ArrayList<String>();
		preferenciasVariadas.add("fruta");
		preferenciasVariadas.add("semillas");
		preferenciasVariadas.add("champignones");

		// Datos Personales
		datosPersonalesGenericos = new DatosPersonales("PersonaGenerica",
				"Indefinido", LocalDate.parse("2000-01-01"));
		datosPersonalesNacidoHoy = new DatosPersonales("PersonaGenerica",
				"Indefinido", LocalDate.now());
		datosPersonalesNombreCorto = new DatosPersonales("asd", "Indefinido",
				LocalDate.parse("2000-01-01"));
		datosPersonalesRamiro = new DatosPersonales("Ramiro", "masculino",
				LocalDate.parse("1993-07-19"));
		datosPersonalesGaston = new DatosPersonales("Gaston", "masculino",
				LocalDate.parse("2000-01-01"));
		datosPersonalesGustavo = new DatosPersonales("Gustavo", "masculino",
				LocalDate.parse("1994-02-25"));
		datosPersonalesLeandro = new DatosPersonales("Leandro", "masculino",
				LocalDate.parse("2000-01-01"));
		datosPersonalesJuanchi = new DatosPersonales("Juanchi", "masculino",
				LocalDate.parse("2000-01-01"));

		// Complexiones
		complexionRamiro = new Complexion(63, 1.75);
		complexionGaston = new Complexion(65, 1.66);
		complexionGustavo = new Complexion(73, 1.83);
		complexionJuanchi = new Complexion(70, 1.85);
		complexionLeandro = new Complexion(79, 1.78);
		complexionSinPeso = new Complexion(0, 1.83);
		complexionSinEstatura = new Complexion(73, 0);
		complexionDemasiadoIMC = new Complexion(101, 1.83);
		complexionPocoIMC = new Complexion(60, 1.83);

		Usuario.setRecetasPublicas(recetasPublicas);
		gustavo = new Usuario(datosPersonalesGustavo, complexionGustavo, null,
				recetasGustavo, condiciones, Rutina.MEDIANA);
		leandro = new Usuario(datosPersonalesLeandro, complexionLeandro,
				preferenciaFruta, null, coleccionCondicionVegano,
				Rutina.MEDIANA); // No tiene
									// fecha y
		// es vegano (con
		// preferencia
		// fruta)
		ramiro = new Usuario(datosPersonalesRamiro, complexionRamiro, null,
				null, coleccionCondicionCeliaco, Rutina.MEDIANA); // No
																	// tiene
		// nombre
		gaston = new Usuario(datosPersonalesGaston, complexionGaston, null,
				recetasGaston, null, null); // Tiene Nombre corto
		juanchi = new Usuario(datosPersonalesJuanchi, complexionJuanchi, null,
				recetasJuanchi, coleccionCondicionDiabetico, Rutina.ALTA); // tiene
																			// rutina
																			// y
																			// es
																			// diabetico
		juanchiSinRutina = new Usuario(datosPersonalesJuanchi,
				complexionLeandro, null, recetasJuanchi,
				coleccionCondicionDiabetico, null); // No tiene
													// rutina y
													// es
													// diabetico

		sinPeso = new Usuario(datosPersonalesGenericos, complexionSinPeso,
				null, null, new ArrayList<CondicionDeSalud>(), Rutina.MEDIANA);
		sinEstatura = new Usuario(datosPersonalesGenericos,
				complexionSinEstatura, null, null,
				new ArrayList<CondicionDeSalud>(), Rutina.MEDIANA);
		nacioHoy = new Usuario(datosPersonalesNacidoHoy, complexionGustavo,
				null, null, new ArrayList<CondicionDeSalud>(), Rutina.MEDIANA);
		usuarioDiabeticoRutinaAlata = new Usuario(datosPersonalesGenericos,
				complexionGustavo, null, null, coleccionCondicionDiabetico,
				Rutina.ALTA);
		demasiadoICM = new Usuario(datosPersonalesGenericos,
				complexionDemasiadoIMC, null, null, condiciones, Rutina.MEDIANA);
		pocoICM = new Usuario(datosPersonalesGenericos, complexionPocoIMC,
				null, null, condiciones, Rutina.MEDIANA);

	}

	// Test de Validez de usuario

	@Test
	public void gustavoEsValido() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);

		assertTrue(gustavo.esUsuarioValido()); // XXX pensar si no podria
												// refactorizarse para que no se
												// puedan crear usuarios
												// invalidos

		verify(hippie, times(1)).esUsuarioValido(any(Usuario.class));
		verify(corporativo, times(1)).esUsuarioValido(any(Usuario.class));
	}

	@Test
	public void faltaUnCampoObligatorio() {
		assertFalse(sinPeso.tieneCamposObligatorios());
		assertFalse(sinEstatura.tieneCamposObligatorios());
		assertFalse(juanchiSinRutina.tieneCamposObligatorios());
	}

	@Test
	public void elNombreEsCorto() {
		assertTrue(datosPersonalesNombreCorto.esNombreCorto());
	}

	@Test
	public void fechaNoEsMenorAHoy() {
		assertFalse(datosPersonalesNacidoHoy.fechaDeNacimientoAnteriorAHoy());
	}

	@Test
	public void noEsValidoSiSusCondicionesNoLoPermiten() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(false);

		assertFalse(gustavo.esUsuarioValidoParaSusCondiciones());

		verify(hippie, times(1)).esUsuarioValido(any(Usuario.class));
		verify(corporativo, times(1)).esUsuarioValido(any(Usuario.class));
	}

	@Test
	public void siNoCumplenAlgunaOtraCondicionSonInvalidos() {
		assertFalse(sinPeso.esUsuarioValido());
		assertFalse(sinEstatura.esUsuarioValido());
		assertFalse(nacioHoy.esUsuarioValido());
	}

	// Test de IMCD

	@Test
	public void juanchiTieneIMCDe2045() {
		assertEquals(20.45, juanchi.indiceMasaCorporal(), 0.01);
	}

	@Test
	public void gustavoTieneIMCDe2180() {
		assertEquals(21.80, gustavo.indiceMasaCorporal(), 0.01);

	}

	@Test
	public void leandroTieneIMCDe2493() {
		assertEquals(24.93, leandro.indiceMasaCorporal(), 0.01);
	}

	@Test
	public void ramiroTieneIMCDe2057() {
		assertEquals(20.57, ramiro.indiceMasaCorporal(), 0.01);
	}

	@Test
	public void gastonTieneIMCDe2358() {
		assertEquals(23.58, gaston.indiceMasaCorporal(), 0.01);
	}

	// Test de Rutina saludable
	@Test
	public void sigueRutinaSaludableConCondiciones() {
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		when(corporativo.subsanaCondicion(any(Usuario.class))).thenReturn(true);

		assertTrue(gustavo.sigueRutinaSaludable());

		verify(hippie, times(1)).subsanaCondicion(any(Usuario.class));
		verify(corporativo, times(1)).subsanaCondicion(any(Usuario.class));
	}

	@Test
	public void sigueRutinaSaludableSinCondiciones() {
		assertTrue(gaston.sigueRutinaSaludable());
	}

	@Test
	public void noSigueRutinaSaludableSiNoSubsanaUnaCondicion() {
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		when(corporativo.subsanaCondicion(any(Usuario.class)))
				.thenReturn(false);
		assertFalse(gustavo.sigueRutinaSaludable());

		verify(hippie, times(1)).subsanaCondicion(any(Usuario.class));
		verify(corporativo, times(1)).subsanaCondicion(any(Usuario.class));
	}

	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMenorA18() {
		assertFalse(pocoICM.sigueRutinaSaludable());
	}

	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMayorA30() {
		assertFalse(demasiadoICM.sigueRutinaSaludable());
	}

	public void tieneAlgunaDeEstasPreferenciasTest() {
		assertTrue(leandro.tieneAlgunaDeEstasPreferencias(preferenciasVariadas));
	}

	@Test
	public void usuarioVeganoPrefiereFruta() {
		assertTrue(leandro.tienePreferencia("fruta"));
	}

	@Test
	public void veganoQueLeGustanLAsFrutas() {
		assertTrue(leandro.sigueRutinaSaludable());
	}

	@Test
	public void celiacoCumpleSiCumpleIMCD() {
		assertTrue(ramiro.sigueRutinaSaludable());
	}

	@Test
	public void diabeticoCumpleIMCPesaMenosDe70() {
		assertTrue(juanchi.sigueRutinaSaludable());
	}

	@Test
	public void diabeticoCumpleIMCConRutinaAlta() {
		assertTrue(usuarioDiabeticoRutinaAlata.sigueRutinaSaludable());
	}

	// Tests Creacion de Recetas
	@Test
	public void gustavoCreaRecetaExitosa() {
		when(recetaMock.esValida()).thenReturn(true);

		gustavo.crearReceta(recetaMock);

		verify(recetaMock, times(1)).esValida();
	}

	@Test
	public void gustavoCreaRecetaConSubrecetas() {
		when(recetaMock.esValida()).thenReturn(true);

		gustavo.crearRecetaConSubRecetas(recetaMock,
				Arrays.asList(choripanMock, ensaladaMock));

		assertTrue(gustavo.esRecetaPropia(recetaMock));
		verify(recetaMock, times(1)).esValida();
	}

	@Test(expected = RecetaNoValidaException.class)
	public void usuarioCreaRecetaFallida() {
		when(recetaMock.esValida()).thenReturn(false);

		gustavo.crearReceta(recetaMock);

		verify(recetaMock, times(1)).esValida();
	}

	@Test
	public void accesoARecetas() {
		assertTrue(gustavo.puedeAcceder(choripanMock));
		assertTrue(gustavo.puedeAcceder(ensaladaMock));
		assertFalse(gaston.puedeAcceder(choripanMock));
	}

	// Tests Modificacion de Recetas

	@Test
	public void juanchiModificaRecetaPublica() {
		assertTrue(juanchi.getRecetasPropias().isEmpty());
		when(nuevaEnsaladaMock.esValida()).thenReturn(true);

		juanchi.modificarReceta(ensaladaMock, nuevaEnsaladaMock);

		assertTrue(juanchi.getRecetasPropias().contains(nuevaEnsaladaMock));
		verify(nuevaEnsaladaMock, times(1)).esValida();
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void gastonNoPuedeModificarUnaRecetaDeOtro() {
		gaston.modificarReceta(choripanMock, choriConChimiMock);

	}

	@Test
	public void gustavoModificaRecetaPropia() {
		when(choriConChimiMock.esValida()).thenReturn(true);

		gustavo.modificarReceta(choripanMock, choriConChimiMock);

		assertTrue(gustavo.getRecetasPropias().contains(choriConChimiMock));
		assertFalse(gustavo.getRecetasPropias().contains(choripanMock));
		verify(choriConChimiMock, times(1)).esValida();
	}

	// Test Eliminar Recetas Propias
	@Test
	public void eliminarUnaRecetaPrivada() {
		assertFalse(gustavo.getRecetasPropias().isEmpty());

		gustavo.eliminarRecetaPropia(choripanMock);

		assertTrue(gustavo.getRecetasPropias().isEmpty());
	}

}
