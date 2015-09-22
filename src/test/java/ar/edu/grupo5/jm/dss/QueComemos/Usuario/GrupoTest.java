package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;

public class GrupoTest {

	private Usuario gaston = mock(Usuario.class);
	private Usuario juanchi = mock(Usuario.class);

	private Receta recetaMock = mock(Receta.class);

	private Grupo grupoConPreferenciasVariadas;
	private Collection<Ingrediente> preferenciasVariadas = Arrays.asList(new Ingrediente("fruta"), new Ingrediente("semillas"), new Ingrediente("queso"));

	@Before
	public void setUp() {
		grupoConPreferenciasVariadas = new Grupo(preferenciasVariadas, Arrays.asList(gaston, juanchi));
	}

	@Test
	public void alguienTieneUnaRecetaSiUnMiembroEsElDueño() {
		when(recetaMock.esElDueño(gaston)).thenReturn(false);
		when(recetaMock.esElDueño(juanchi)).thenReturn(true);

		assertTrue(grupoConPreferenciasVariadas.alguienTiene(recetaMock));

		verify(recetaMock, times(1)).esElDueño(gaston);
		verify(recetaMock, times(1)).esElDueño(juanchi);
	}

	@Test
	public void puedeSugerirseSiNoEsInadecuadaParaNingunIntegranteYTieneIngredientesPreferentesYAlguienEsDueño() {
		when(recetaMock.esElDueño(gaston)).thenReturn(true);
		when(juanchi.sosRecetaInadecuadaParaMi(recetaMock)).thenReturn(false);
		when(gaston.sosRecetaInadecuadaParaMi(recetaMock)).thenReturn(false);
		when(recetaMock.tenesAlgunIngredienteDeEstos(preferenciasVariadas)).thenReturn(true);

		assertTrue(grupoConPreferenciasVariadas.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).esElDueño(gaston);
		verify(juanchi, times(1)).sosRecetaInadecuadaParaMi(recetaMock);
		verify(gaston, times(1)).sosRecetaInadecuadaParaMi(recetaMock);
		verify(recetaMock, times(1)).tenesAlgunIngredienteDeEstos(preferenciasVariadas);
	}

	@Test
	public void puedeSugerirseSiNoEsInadecuadaParaNingunIntegranteYTieneIngredientesPreferentesYEsPublica() {
		when(recetaMock.esElDueño(gaston)).thenReturn(false);
		when(recetaMock.esPublica()).thenReturn(true);
		when(juanchi.sosRecetaInadecuadaParaMi(recetaMock)).thenReturn(false);
		when(gaston.sosRecetaInadecuadaParaMi(recetaMock)).thenReturn(false);
		when(recetaMock.tenesAlgunIngredienteDeEstos(preferenciasVariadas)).thenReturn(true);

		assertTrue(grupoConPreferenciasVariadas.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).esElDueño(gaston);
		verify(juanchi, times(1)).sosRecetaInadecuadaParaMi(recetaMock);
		verify(gaston, times(1)).sosRecetaInadecuadaParaMi(recetaMock);
		verify(recetaMock, times(1)).tenesAlgunIngredienteDeEstos(preferenciasVariadas);
	}

	@Test
	public void noPuedeSugerirseSiEsInadecuadaParaAlgunIntegrante() {
		when(recetaMock.esElDueño(gaston)).thenReturn(true);
		when(juanchi.sosRecetaInadecuadaParaMi(recetaMock)).thenReturn(true);
		when(gaston.sosRecetaInadecuadaParaMi(recetaMock)).thenReturn(false);
		when(recetaMock.tenesAlgunIngredienteDeEstos(preferenciasVariadas)).thenReturn(true);

		assertFalse(grupoConPreferenciasVariadas.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).esElDueño(gaston);
		verify(juanchi, times(1)).sosRecetaInadecuadaParaMi(recetaMock);
		verify(gaston, times(1)).sosRecetaInadecuadaParaMi(recetaMock);
		verify(recetaMock, times(1)).tenesAlgunIngredienteDeEstos(preferenciasVariadas);
	}

	@Test
	public void noPuedeSugerirseSiNoTieneIngredientesPreferentes() {
		when(recetaMock.esElDueño(gaston)).thenReturn(true);
		when(recetaMock.tenesAlgunIngredienteDeEstos(preferenciasVariadas)).thenReturn(false);

		assertFalse(grupoConPreferenciasVariadas.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).esElDueño(gaston);
		verify(recetaMock, times(1)).tenesAlgunIngredienteDeEstos(preferenciasVariadas);
	}

	@Test
	public void noPuedeSugerirseSiNoPuedenAcceder() {
		when(recetaMock.esElDueño(gaston)).thenReturn(false);
		when(recetaMock.esPublica()).thenReturn(false);

		assertFalse(grupoConPreferenciasVariadas.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).esElDueño(gaston);
		verify(recetaMock, times(1)).esPublica();
	}

}
