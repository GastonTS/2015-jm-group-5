package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Rutina;

public class GrupoTest {

	private Usuario usuarioHipertenso;
	private Usuario gaston;
	private Usuario juanchi;

	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);

	private Complexion complexionMock = mock(Complexion.class);

	private Hipertenso hipertenso = new Hipertenso();

	private Collection<Receta> recetasGaston = new ArrayList<Receta>();
	private Collection<Receta> recetasGustavo = new ArrayList<Receta>();
	// Juanchi queda sin recetas
	private Collection<Receta> recetasJuanchi = new ArrayList<Receta>();

	private Collection<CondicionDeSalud> condicionHipertenso = new ArrayList<CondicionDeSalud>();
	private Collection<CondicionDeSalud> condicionSinNada = new ArrayList<CondicionDeSalud>();

	private Receta pizzaConQueso;

	private Condimentacion sal = new Condimentacion("sal", 100);

	private Grupo grupoConHipertenso;
	private Grupo grupoSinHipertenso;
	private Grupo grupoSoloPreferenciaPapa;
	private Collection<Usuario> integrantesGrupoConHipertenso = new ArrayList<Usuario>();
	private Collection<Usuario> integrantesGrupoSinHipertenso = new ArrayList<Usuario>();

	private Collection<String> preferenciaPapa = new ArrayList<String>();
	private Collection<String> preferenciasVariadas = new ArrayList<String>();

	@Before
	public void setUp() {

		pizzaConQueso = new Receta(Arrays.asList("harina", "tomate", "agua",
				"queso"), Arrays.asList(sal), new ArrayList<Receta>(), 400);

		preferenciasVariadas.add("fruta");
		preferenciasVariadas.add("semillas");
		preferenciasVariadas.add("queso");

		preferenciaPapa.add("papas");

		condicionHipertenso.add(hipertenso);

		usuarioHipertenso = new Usuario(datosPersonalesMock, complexionMock,
				null, null, recetasGustavo, condicionHipertenso, Rutina.MEDIANA);
		gaston = new Usuario(datosPersonalesMock, complexionMock, null, null,
				recetasGaston, condicionSinNada, null);
		juanchi = new Usuario(datosPersonalesMock, complexionMock, null, null,
				recetasJuanchi, condicionSinNada, null);

		integrantesGrupoConHipertenso.add(usuarioHipertenso);
		integrantesGrupoConHipertenso.add(gaston);
		integrantesGrupoConHipertenso.add(juanchi);

		integrantesGrupoSinHipertenso.add(gaston);
		integrantesGrupoSinHipertenso.add(juanchi);

		grupoConHipertenso = new Grupo(preferenciasVariadas,
				integrantesGrupoConHipertenso);
		grupoSinHipertenso = new Grupo(preferenciasVariadas,
				integrantesGrupoSinHipertenso);
		grupoSoloPreferenciaPapa = new Grupo(preferenciaPapa,
				integrantesGrupoSinHipertenso);
	}

	@Test
	public void noPuedeSugerirUnaRecetaPorSalAHipertenso() {
		assertFalse(grupoConHipertenso.puedeSugerirse(pizzaConQueso));
	}

	@Test
	public void noPuedeSugerirAlNoTenerIngredientesPreferidos() {
		assertFalse(grupoSoloPreferenciaPapa.puedeSugerirse(pizzaConQueso));
	}

	@Test
	public void puedeSugerirUnaReseta() {
		assertTrue(grupoSinHipertenso.puedeSugerirse(pizzaConQueso));
	}

}
