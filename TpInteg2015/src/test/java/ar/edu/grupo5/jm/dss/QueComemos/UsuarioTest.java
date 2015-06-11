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

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Grupo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioNoValidoException;

;

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
	private Receta choripanMock = mock(Receta.class);

	private Grupo grupoMock = mock(Grupo.class);

	@Before
	public void setUp() {
		when(datosPersonalesMock.sonValidos()).thenReturn(true);

		condiciones.add(hippie);
		condiciones.add(corporativo);
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);

		disgustosGustavo.add("McDonalds");
		preferenciaFruta.add("fruta");
		preferenciasVariadas = Arrays.asList("fruta", "semillas", "campignones");

		gustavo = new Usuario(datosPersonalesMock, complexionMock, new ArrayList<String>(), disgustosGustavo, condiciones,
				Rutina.MEDIANA);
		gaston = new Usuario(datosPersonalesMock, complexionMock, new ArrayList<String>(), new ArrayList<String>(),
				new ArrayList<CondicionDeSalud>(), Rutina.MEDIANA);
		juanchi = new Usuario(datosPersonalesMock, complexionMock, preferenciaFruta, new ArrayList<String>(),
				new ArrayList<CondicionDeSalud>(), Rutina.MEDIANA);

		juanchi.agregarGrupo(grupoMock);
	}

	@Test(expected = UsuarioNoValidoException.class)
	public void noEsValidoSiNoTieneDatosPersonales() {
		new Usuario(null, complexionMock, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<CondicionDeSalud>(),
				Rutina.MEDIANA);
	}

	@Test(expected = UsuarioNoValidoException.class)
	public void noEsValidoSiNoTieneComplexion() {
		new Usuario(datosPersonalesMock, null, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<CondicionDeSalud>(),
				Rutina.MEDIANA);
	}

	@Test(expected = UsuarioNoValidoException.class)
	public void noEsValidoSiSusCondicionesNoLoPermiten() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(false);

		new Usuario(datosPersonalesMock, complexionMock, new ArrayList<String>(), new ArrayList<String>(), condiciones, Rutina.MEDIANA);
	}

	@Test(expected = UsuarioNoValidoException.class)
	public void siNoTieneRutinaEsInvalidos() {
		new Usuario(datosPersonalesMock, complexionMock, new ArrayList<String>(), new ArrayList<String>(),
				new ArrayList<CondicionDeSalud>(), null);
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
		when(corporativo.subsanaCondicion(any(Usuario.class))).thenReturn(false);

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

	// Test de Sugerencias
	@Test
	public void unaRecetaNoDisgusta() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo)).thenReturn(false);

		assertTrue(gustavo.noLeDisgusta(recetaMock));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(disgustosGustavo);
	}

	@Test
	public void unaRecetaDisgusta() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo)).thenReturn(true);

		assertFalse(gustavo.noLeDisgusta(recetaMock));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(disgustosGustavo);
	}

	@Test
	public void seSugiereUnaRecetaSiNoDisgustaYEsAdecauda() {
		when(hippie.esInadecuada(recetaMock)).thenReturn(false);
		when(corporativo.esInadecuada(recetaMock)).thenReturn(false);
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo)).thenReturn(false);
		when(recetaMock.esPublica()).thenReturn(true);

		assertTrue(gustavo.puedeSugerirse(recetaMock));

		verify(hippie, times(1)).esInadecuada(recetaMock);
		verify(corporativo, times(1)).esInadecuada(recetaMock);
		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(disgustosGustavo);
		verify(recetaMock, times(1)).esPublica();
	}

	@Test
	public void noSeSugiereRecetaSiDisgusta() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo)).thenReturn(true);

		assertFalse(gustavo.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(disgustosGustavo);
	}

	@Test
	public void noSeSugiereRecetaSiEsInadecuada() {
		when(hippie.esInadecuada(recetaMock)).thenReturn(true);
		when(recetaMock.tieneAlgunIngredienteDeEstos(disgustosGustavo)).thenReturn(false);

		assertFalse(gustavo.puedeSugerirse(recetaMock));

		verify(hippie, times(1)).esInadecuada(recetaMock);
		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(disgustosGustavo);
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
	public void agregarAFavoritas() {
		assertTrue(gustavo.getRecetasFavoritas().isEmpty());

		gustavo.agregarAFavorita(choripanMock);

		assertEquals(gustavo.getRecetasFavoritas(), Arrays.asList(choripanMock));
	}

	@Test
	public void gustavoEsMasculino() {
		when(datosPersonalesMock.getSexo()).thenReturn("Masculino");
		assertTrue(gustavo.esDeSexo("Masculino"));
		verify(datosPersonalesMock, times(1)).getSexo();

	}
}
