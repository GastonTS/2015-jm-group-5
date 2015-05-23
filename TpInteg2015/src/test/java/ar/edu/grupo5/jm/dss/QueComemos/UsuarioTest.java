package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Rutina;

public class UsuarioTest {

	private Usuario gustavo;
	private Usuario gaston;
	private Usuario juanchi;

	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);

	private Complexion complexionMock = mock(Complexion.class);

	private Collection<String> disgustosGustavo = new ArrayList<String>();
	private Collection<String> preferenciaFruta = new ArrayList<String>();
	private Collection<String> preferenciasVariadas = new ArrayList<String>();

	private Collection<ICondicionDeSalud> condiciones = new ArrayList<ICondicionDeSalud>();
	private ICondicionDeSalud hippie = mock(ICondicionDeSalud.class);
	private ICondicionDeSalud corporativo = mock(ICondicionDeSalud.class);

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta nuevaEnsaladaMock = mock(Receta.class);
	private Receta choripanMock = mock(Receta.class);
	private Receta choriConChimiMock = mock(Receta.class);

	private Collection<Receta> recetasPublicas = new ArrayList<Receta>();
	private Collection<Receta> recetasGaston = new ArrayList<Receta>();
	private Collection<Receta> recetasGustavo = new ArrayList<Receta>();
	// Juanchi queda sin recetas
	private Collection<Receta> recetasJuanchi = new ArrayList<Receta>();

	private Grupo grupoMock = mock(Grupo.class);

	private SinFiltro sinFiltro = new SinFiltro();

	@Before
	public void setUp() {
		condiciones.add(hippie);
		condiciones.add(corporativo);

		recetasPublicas.add(ensaladaMock);
		recetasGustavo.add(choripanMock);
		recetasGaston.add(panchoMock);

		disgustosGustavo.add("McDonalds");
		preferenciaFruta.add("fruta");
		preferenciasVariadas.add("fruta");
		preferenciasVariadas.add("semillas");
		preferenciasVariadas.add("champignones");

		Usuario.setRecetasPublicas(recetasPublicas);
		gustavo = new Usuario(datosPersonalesMock, complexionMock, null,
				disgustosGustavo, recetasGustavo, condiciones, Rutina.MEDIANA);
		gaston = new Usuario(datosPersonalesMock, complexionMock, null, null,
				recetasGaston, null, null);
		juanchi = new Usuario(datosPersonalesMock, complexionMock,
				preferenciaFruta, null, recetasJuanchi, null, null);
		juanchi.agregarGrupo(grupoMock);
	}

	// Test de Validez de usuario
	@Test
	public void gustavoEsValido() {
		when(complexionMock.esComplexionValida()).thenReturn(true);
		when(datosPersonalesMock.sonValidos()).thenReturn(true);
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);

		assertTrue(gustavo.esUsuarioValido()); // XXX pensar si no podria
												// refactorizarse para que no se
												// puedan crear usuarios
												// invalidos

		verify(complexionMock, times(1)).esComplexionValida();
		verify(hippie, times(1)).esUsuarioValido(any(Usuario.class));
		verify(corporativo, times(1)).esUsuarioValido(any(Usuario.class));
	}

	@Test
	public void noEsValidoSiSusDatosPersonalesNoLoSon() {
		when(datosPersonalesMock.sonValidos()).thenReturn(false);

		assertFalse(gustavo.esUsuarioValido());

		verify(datosPersonalesMock, times(1)).sonValidos();
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
	public void siNoTieneRutinaEsInvalidos() {
		assertFalse(gaston.esUsuarioValido());
	}

	// Test de Rutina saludable
	@Test
	public void sigueRutinaSaludableConCondiciones() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		when(corporativo.subsanaCondicion(any(Usuario.class))).thenReturn(true);

		assertTrue(gustavo.sigueRutinaSaludable());

		verify(hippie, times(1)).subsanaCondicion(any(Usuario.class));
		verify(corporativo, times(1)).subsanaCondicion(any(Usuario.class));
	}

	@Test
	public void sigueRutinaSaludableSinCondiciones() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);

		assertTrue(gaston.sigueRutinaSaludable());
	}

	@Test
	public void noSigueRutinaSaludableSiNoSubsanaUnaCondicion() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(20.0);
		when(hippie.subsanaCondicion(any(Usuario.class))).thenReturn(true);
		when(corporativo.subsanaCondicion(any(Usuario.class)))
				.thenReturn(false);

		assertFalse(gustavo.sigueRutinaSaludable());

		verify(complexionMock, times(2)).indiceMasaCorporal();
		verify(hippie, times(1)).subsanaCondicion(any(Usuario.class));
		verify(corporativo, times(1)).subsanaCondicion(any(Usuario.class));
	}

	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMenorA18() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(15.0);

		assertFalse(gaston.sigueRutinaSaludable());

		verify(complexionMock, times(1)).indiceMasaCorporal();
	}

	@Test
	public void noSigueRutinaSaludableSiNoTieneICMMayorA30() {
		when(complexionMock.indiceMasaCorporal()).thenReturn(35.0);

		assertFalse(gaston.sigueRutinaSaludable());

		verify(complexionMock, times(2)).indiceMasaCorporal();
	}

	public void tieneAlgunaDeEstasPreferenciasTest() {
		assertTrue(juanchi.tieneAlgunaDeEstasPreferencias(preferenciasVariadas));
	}

	@Test
	public void usuarioVeganoPrefiereFruta() {
		assertTrue(juanchi.tienePreferencia("fruta"));
	}

	// Tests Creacion de Recetas
	@Test
	public void gustavoCreaRecetaExitosa() {
		when(recetaMock.esValida()).thenReturn(true);

		gustavo.crearReceta(recetaMock);

		verify(recetaMock, times(1)).esValida();
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void noPuedeCrearRecetaConSubRecetasSinAccesoAEllas() {

		gustavo.crearRecetaConSubRecetas(recetaMock,
				Arrays.asList(panchoMock, ensaladaMock));

		assertFalse(gustavo.esRecetaPropia(recetaMock));
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

	@Test
	public void accesoARecetasPorgrupo() {
		when(grupoMock.alguienTiene(choripanMock)).thenReturn(true);
		when(grupoMock.alguienTiene(panchoMock)).thenReturn(false);

		assertTrue(juanchi.puedeAcceder(choripanMock));
		assertFalse(juanchi.puedeAcceder(panchoMock));

		verify(grupoMock, times(1)).alguienTiene(choripanMock);
		verify(grupoMock, times(1)).alguienTiene(panchoMock);
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

	// Test de Sugerencias
	@Test
	public void seSugiereUnaRecetaSiNoDisgustaYEsAdecauda() {
		when(hippie.esInadecuada(recetaMock)).thenReturn(false);
		when(corporativo.esInadecuada(recetaMock)).thenReturn(false);
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo))
				.thenReturn(false);

		assertTrue(gustavo.puedeSugerirse(recetaMock));

		verify(hippie, times(1)).esInadecuada(recetaMock);
		verify(corporativo, times(1)).esInadecuada(recetaMock);
		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				disgustosGustavo);
	}

	@Test
	public void noSeSugiereRecetaSiDisgusta() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo))
				.thenReturn(true);

		assertFalse(gustavo.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				disgustosGustavo);
	}

	@Test
	public void noSeSugiereRecetaSiEsInadecuada() {
		when(hippie.esInadecuada(recetaMock)).thenReturn(true);
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo))
				.thenReturn(false);

		assertFalse(gustavo.puedeSugerirse(recetaMock));

		verify(hippie, times(1)).esInadecuada(recetaMock);
		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				disgustosGustavo);
	}

	@Test
	public void consultaRecetasPublicasYPrivadas() {
		Collection<Receta> resultadoConsulta = new ArrayList<Receta>();
		resultadoConsulta.add(panchoMock);
		resultadoConsulta.add(ensaladaMock);

		assertEquals(gaston.consultarRecetas(sinFiltro), resultadoConsulta);
	}

	@Test
	public void consultaRecetasPublicas() {
		Collection<Receta> resultadoConsulta = new ArrayList<Receta>();
		resultadoConsulta.add(ensaladaMock);
		when(grupoMock.consultarRecetas()).thenReturn(new ArrayList<Receta>());

		assertEquals(juanchi.consultarRecetas(sinFiltro), resultadoConsulta);

		verify(grupoMock, times(1)).consultarRecetas();
	}

	@Test
	public void consultaRecetasPublicasYDelGrupo() {
		Collection<Receta> resultadoConsulta = new ArrayList<Receta>();
		resultadoConsulta.add(panchoMock);
		resultadoConsulta.add(ensaladaMock);
		when(grupoMock.consultarRecetas())
				.thenReturn(Arrays.asList(panchoMock));

		assertEquals(juanchi.consultarRecetas(sinFiltro), resultadoConsulta);

		verify(grupoMock, times(1)).consultarRecetas();
	}

	@Test
	public void agregarAFavoritas() {
		assertTrue(gustavo.getRecetasFavoritas().isEmpty());

		gustavo.agregarAFavorita(choripanMock);

		assertEquals(gustavo.getRecetasFavoritas(), Arrays.asList(choripanMock));
	}
}
