package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class GrupoTest {

	private Usuario gaston;
	private Usuario juanchi;

	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);

	private Complexion complexionMock = mock(Complexion.class);

	private Hipertenso hipertensoMock = mock(Hipertenso.class);

	private Collection<Receta> recetasGaston = new ArrayList<Receta>();

	private Collection<ICondicionDeSalud> condicionHipertenso = new ArrayList<ICondicionDeSalud>();
	private Collection<ICondicionDeSalud> condicionSinNada = new ArrayList<ICondicionDeSalud>();

	private Receta recetaMock = mock(Receta.class);

	private Grupo grupoConPreferenciasVariadas;
	private Grupo grupoSoloPreferenciaPapa;
	private Collection<Usuario> integrantesGrupo = new ArrayList<Usuario>();
	private Collection<Usuario> integrantesGrupoSinGaston = new ArrayList<Usuario>();

	private Collection<String> preferenciaPapa = new ArrayList<String>();
	private Collection<String> preferenciasVariadas = new ArrayList<String>();

	@Before
	public void setUp() {
		;

		preferenciasVariadas.add("fruta");
		preferenciasVariadas.add("semillas");
		preferenciasVariadas.add("queso");

		preferenciaPapa.add("papas");

		condicionHipertenso.add(hipertensoMock);

		recetasGaston.add(recetaMock);

		gaston = new Usuario(datosPersonalesMock, complexionMock, null, null,
				recetasGaston, condicionSinNada, null);
		juanchi = new Usuario(datosPersonalesMock, complexionMock, null, null,
				null, condicionHipertenso, null);

		integrantesGrupo.add(gaston);
		integrantesGrupo.add(juanchi);
		integrantesGrupoSinGaston.add(juanchi);

		grupoConPreferenciasVariadas = new Grupo(preferenciasVariadas,
				integrantesGrupo);
		grupoSoloPreferenciaPapa = new Grupo(preferenciaPapa,
				integrantesGrupoSinGaston);
	}

	@Test
	public void noPuedeSugerirUnaRecetaPorSalAHipertenso() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(preferenciasVariadas))
				.thenReturn(true);
		when(hipertensoMock.esInadecuada(any(Receta.class))).thenReturn(true);

		assertFalse(grupoConPreferenciasVariadas.puedeSugerirse(recetaMock));

		verify(hipertensoMock, times(1)).esInadecuada(any(Receta.class));
		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				preferenciasVariadas);
	}

	@Test
	public void noPuedeSugerirAlNoTenerIngredientesPreferidos() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(preferenciaPapa))
				.thenReturn(false);

		assertFalse(grupoSoloPreferenciaPapa.puedeSugerirse(recetaMock));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				preferenciaPapa);
	}

	@Test
	public void puedeSugerirUnaReseta() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(preferenciasVariadas))
				.thenReturn(true);
		when(hipertensoMock.esInadecuada(any(Receta.class))).thenReturn(false);

		assertTrue(grupoConPreferenciasVariadas.puedeSugerirse(recetaMock));

		verify(hipertensoMock, times(1)).esInadecuada(any(Receta.class));
		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(
				preferenciasVariadas);
	}

	@Test
	public void alguienTieneAReceta() {
		assertTrue(grupoConPreferenciasVariadas.alguienTiene(recetaMock));
	}

	@Test
	public void nadieTieneAReceta() {
		assertFalse(grupoSoloPreferenciaPapa.alguienTiene(recetaMock));
	}

}
