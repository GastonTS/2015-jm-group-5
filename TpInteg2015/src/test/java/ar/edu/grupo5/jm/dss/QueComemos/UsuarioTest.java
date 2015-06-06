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

	private Collection<CondicionDeSalud> condiciones = new ArrayList<CondicionDeSalud>();
	private CondicionDeSalud hippie = mock(CondicionDeSalud.class);
	private CondicionDeSalud corporativo = mock(CondicionDeSalud.class);

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta nuevaEnsaladaMock = mock(Receta.class);
	private Receta choripanMock = mock(Receta.class);
	private Receta choriConChimiMock = mock(Receta.class);

	private Recetario repositorioMock = mock(Recetario.class);

	private Grupo grupoMock = mock(Grupo.class);

	private IFiltro filtroMock = mock(IFiltro.class);
	private GestorDeConsultas filtroStMock = mock(GestorDeConsultas.class);

	@Before
	public void setUp() {
		condiciones.add(hippie);
		condiciones.add(corporativo);

		disgustosGustavo.add("McDonalds");
		preferenciaFruta.add("fruta");
		preferenciasVariadas.add("fruta");
		preferenciasVariadas.add("semillas");
		preferenciasVariadas.add("champignones");

		Usuario.setRepositorio(repositorioMock);

		gustavo = new Usuario(datosPersonalesMock, complexionMock,
				new ArrayList<String>(), disgustosGustavo, condiciones,
				Rutina.MEDIANA);
		gaston = new Usuario(datosPersonalesMock, complexionMock,
				new ArrayList<String>(), new ArrayList<String>(),
				new ArrayList<CondicionDeSalud>(), null);
		juanchi = new Usuario(datosPersonalesMock, complexionMock,
				preferenciaFruta, new ArrayList<String>(),
				new ArrayList<CondicionDeSalud>(), null);

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
		verify(repositorioMock, times(1)).agregarReceta(recetaMock);
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void noPuedeCrearRecetaConSubRecetasSinAccesoAEllas() {
		when(panchoMock.esElDueño(gustavo)).thenReturn(false);
		when(ensaladaMock.esElDueño(gustavo)).thenReturn(true);

		gustavo.crearRecetaConSubRecetas(recetaMock,
				Arrays.asList(panchoMock, ensaladaMock));

		verify(panchoMock, times(1)).esElDueño(gustavo);
		verify(ensaladaMock, times(1)).esElDueño(gustavo);
		verify(repositorioMock, times(0)).agregarReceta(recetaMock);
	}

	@Test
	public void gustavoCreaRecetaConSubrecetas() {
		when(recetaMock.esValida()).thenReturn(true);
		when(choripanMock.esElDueño(gustavo)).thenReturn(true);
		when(ensaladaMock.esElDueño(gustavo)).thenReturn(true);

		gustavo.crearRecetaConSubRecetas(recetaMock,
				Arrays.asList(choripanMock, ensaladaMock));

		verify(recetaMock, times(1)).esValida();
		verify(choripanMock, times(1)).esElDueño(gustavo);
		verify(ensaladaMock, times(1)).esElDueño(gustavo);
		verify(repositorioMock, times(1)).agregarReceta(recetaMock);
	}

	@Test(expected = RecetaNoValidaException.class)
	public void usuarioCreaRecetaFallida() {
		when(recetaMock.esValida()).thenReturn(false);

		gustavo.crearReceta(recetaMock);

		verify(recetaMock, times(1)).esValida();
		verify(repositorioMock, times(0)).agregarReceta(recetaMock);
	}

	@Test
	public void accesoARecetas() {
		when(choripanMock.esElDueño(gustavo)).thenReturn(true);
		when(choripanMock.esElDueño(gaston)).thenReturn(false);
		when(choripanMock.esPublica()).thenReturn(false);
		when(ensaladaMock.esPublica()).thenReturn(true);

		assertTrue(gustavo.puedeAccederA(choripanMock));
		assertTrue(gustavo.puedeAccederA(ensaladaMock));
		assertFalse(gaston.puedeAccederA(choripanMock));

		verify(choripanMock, times(1)).esElDueño(gustavo);
		verify(choripanMock, times(1)).esElDueño(gaston);
		verify(choripanMock, times(2)).esPublica();
		verify(ensaladaMock, times(1)).esPublica();
	}

	@Test
	public void accesoARecetasPorgrupo() {
		when(grupoMock.alguienTiene(choripanMock)).thenReturn(true);
		when(grupoMock.alguienTiene(panchoMock)).thenReturn(false);
		when(choripanMock.esElDueño(juanchi)).thenReturn(false);
		when(choripanMock.esPublica()).thenReturn(false);
		when(panchoMock.esElDueño(juanchi)).thenReturn(false);
		when(panchoMock.esPublica()).thenReturn(false);

		assertTrue(juanchi.puedeAccederA(choripanMock));
		assertFalse(juanchi.puedeAccederA(panchoMock));

		verify(grupoMock, times(1)).alguienTiene(choripanMock);
		verify(grupoMock, times(1)).alguienTiene(panchoMock);
		verify(choripanMock, times(1)).esElDueño(juanchi);
		verify(choripanMock, times(1)).esPublica();
		verify(panchoMock, times(1)).esElDueño(juanchi);
		verify(panchoMock, times(1)).esPublica();
	}

	// Tests Modificacion de Recetas

	@Test
	public void juanchiModificaRecetaPublica() {
		when(ensaladaMock.esPublica()).thenReturn(true);
		when(nuevaEnsaladaMock.esValida()).thenReturn(true);
		when(ensaladaMock.esElDueño(juanchi)).thenReturn(false);

		juanchi.modificarReceta(ensaladaMock, nuevaEnsaladaMock);

		verify(ensaladaMock, times(1)).esPublica();
		verify(nuevaEnsaladaMock, times(1)).esValida();
		verify(ensaladaMock, times(1)).esElDueño(juanchi);
		verify(repositorioMock, times(1)).agregarReceta(nuevaEnsaladaMock);
		verify(nuevaEnsaladaMock, times(1)).setDueño(juanchi);
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void gastonNoPuedeModificarUnaRecetaDeOtro() {
		when(choripanMock.esElDueño(gaston)).thenReturn(false);
		when(choripanMock.esPublica()).thenReturn(false);

		gaston.modificarReceta(choripanMock, choriConChimiMock);

		verify(choripanMock, times(1)).esPublica();
		verify(choripanMock, times(1)).esElDueño(gaston);
	}

	@Test
	public void gustavoModificaRecetaPropia() {
		when(choripanMock.esElDueño(gustavo)).thenReturn(true);
		when(choripanMock.esPublica()).thenReturn(false);
		when(choriConChimiMock.esValida()).thenReturn(true);

		gustavo.modificarReceta(choripanMock, choriConChimiMock);

		verify(choriConChimiMock, times(1)).esValida();
		verify(repositorioMock, times(1)).agregarReceta(choriConChimiMock);
		verify(repositorioMock, times(1)).quitarReceta(choripanMock);
		verify(choripanMock, times(3)).esElDueño(gustavo);
		verify(choripanMock, times(1)).esPublica();
	}

	// Test Eliminar Recetas Propias
	@Test
	public void eliminarUnaRecetaPrivada() {
		when(choripanMock.esElDueño(gustavo)).thenReturn(true);

		gustavo.eliminarReceta(choripanMock);

		verify(choripanMock, times(1)).esElDueño(gustavo);
		verify(repositorioMock, times(1)).quitarReceta(choripanMock);
	}

	// Test de Sugerencias
	@Test
	public void unaRecetaNoDisgusta() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo))
				.thenReturn(false);

		assertTrue(gustavo.noLeDisgusta(recetaMock));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				disgustosGustavo);
	}

	@Test
	public void unaRecetaDisgusta() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo))
				.thenReturn(true);

		assertFalse(gustavo.noLeDisgusta(recetaMock));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				disgustosGustavo);
	}

	@Test
	public void seSugiereUnaRecetaSiNoDisgustaYEsAdecauda() {
		when(hippie.esInadecuada(recetaMock)).thenReturn(false);
		when(corporativo.esInadecuada(recetaMock)).thenReturn(false);
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo))
				.thenReturn(false);
		when(recetaMock.esPublica()).thenReturn(true);

		assertTrue(gustavo.puedeSugerirse(recetaMock));

		verify(hippie, times(1)).esInadecuada(recetaMock);
		verify(corporativo, times(1)).esInadecuada(recetaMock);
		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				disgustosGustavo);
		verify(recetaMock, times(1)).esPublica();
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
	public void noSeSugiereRecetaSiNoPuedeAcceder() {
		when(recetaMock.esPublica()).thenReturn(false);
		when(recetaMock.esElDueño(gustavo)).thenReturn(false);

		assertFalse(gustavo.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).esPublica();
		verify(recetaMock, times(1)).esElDueño(gustavo);
	}

	@Test
	public void consultaRecetasDecorador() {
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock,
				ensaladaMock);
		when(repositorioMock.listarTodasPuedeAcceder(gaston)).thenReturn(
				resultadoConsulta);
		when(filtroMock.filtrarRecetas(resultadoConsulta, gaston)).thenReturn(
				resultadoConsulta);

		assertEquals(gaston.consultarRecetas(filtroMock), resultadoConsulta);

		verify(repositorioMock, times(1)).listarTodasPuedeAcceder(gaston);
		verify(filtroMock, times(1)).filtrarRecetas(resultadoConsulta, gaston);
	}

	@Test
	public void consultaRecetasStrategy() {
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock,
				ensaladaMock);
		when(repositorioMock.listarTodasPuedeAcceder(gaston)).thenReturn(
				resultadoConsulta);
		when(filtroStMock.aplicarFiltros(resultadoConsulta, gaston))
				.thenReturn(resultadoConsulta);

		assertEquals(gaston.consultarRecetasSt(filtroStMock), resultadoConsulta);

		verify(repositorioMock, times(1)).listarTodasPuedeAcceder(gaston);
		verify(filtroStMock, times(1))
				.aplicarFiltros(resultadoConsulta, gaston);
	}

	@Test
	public void agregarAFavoritas() {
		assertTrue(gustavo.getRecetasFavoritas().isEmpty());

		gustavo.agregarAFavorita(choripanMock);

		assertEquals(gustavo.getRecetasFavoritas(), Arrays.asList(choripanMock));
	}
}
