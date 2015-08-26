package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

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

import ar.edu.grupo5.jm.dss.QueComemos.Grupo;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class UsuarioTest {

	private Usuario gustavo;
	private Usuario gaston;
	private Usuario juanchi;

	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);

	private Complexion complexionMock = mock(Complexion.class);

	private Collection<String> disgustosGustavo = new ArrayList<String>();
	private CondicionDeSalud hippie = mock(CondicionDeSalud.class);
	private CondicionDeSalud corporativo = mock(CondicionDeSalud.class);
	private CondicionDeSalud vegano = mock(CondicionDeSalud.class);

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta choripanMock = mock(Receta.class);

	private Grupo grupoMock = mock(Grupo.class);

	@Before
	public void setUp() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(vegano.esUsuarioValido(any(Usuario.class))).thenReturn(true);

		disgustosGustavo.add("McDonalds");

		gustavo = new UsuarioBuilder().setDatosPersonales(datosPersonalesMock).setComplexion(complexionMock)
				.agregarDisgustoAlimenticio("McDonalds").agregarCondicionesDeSalud(hippie).agregarCondicionesDeSalud(corporativo)
				.setRutina(Rutina.MEDIANA).construirUsuario();

		gaston = new UsuarioBuilder().setDatosPersonales(datosPersonalesMock).setComplexion(complexionMock).setRutina(Rutina.INTENSIVA)
				.construirUsuario();

		juanchi = new UsuarioBuilder().setDatosPersonales(datosPersonalesMock).setComplexion(complexionMock)
				.agregarPreferenciaAlimenticia("fruta").agregarCondicionesDeSalud(vegano)
				.setRutina(Rutina.ALTA).construirUsuario();

		juanchi.agregarGrupo(grupoMock);
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

	@Test
	public void tieneAlgunaDeEstasPreferenciasTest() {
		Collection<String> preferenciasVariadas = Arrays.asList("fruta", "semillas", "campignones");
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
	public void quitarAFavoritas(){
		gaston.agregarAFavorita(ensaladaMock);
		gaston.agregarAFavorita(choripanMock);
		
		gaston.quitarRecetaFavorita(choripanMock);
		
		assertEquals(gaston.getRecetasFavoritas(), Arrays.asList(ensaladaMock));
	}

	@Test
	public void gustavoEsMasculino() {
		when(datosPersonalesMock.esDeSexo(Sexo.MASCULINO)).thenReturn(true);
		assertTrue(gustavo.esDeSexo(Sexo.MASCULINO));
		verify(datosPersonalesMock, times(1)).esDeSexo(Sexo.MASCULINO);

	}
	
	@Test
	public void juanchiEsVegano(){
		when(vegano.esCondicionVegana()).thenReturn(true);
		assertTrue(juanchi.esVegano());
		verify(vegano, times(1)).esCondicionVegana();
	}
	
	//Test de Rutinas
	@Test
	public void usuarioTieneRutinaActivaSiTieneIntensiva(){
		assertTrue(gaston.tieneRutinaActiva());
	}
	
	@Test
	public void usuarioTieneRutinaActivaSiTieneRutinaAlta(){
		assertTrue(juanchi.tieneRutinaActiva());
	}
}
