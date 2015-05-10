package ar.edu.grupo5.jm.dss.QueComemos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.mockito.Mockito.*;

public class CondicionesPreexistentesTest {

	private Usuario usuarioFalso;
	private Receta recetaFalsa;
	private Vegano vegano;
	private Hipertenso hipertenso;
	private Diabetico diabetico;
	private Celiaco celiaco;

	@Before
	public void setUp() {
		usuarioFalso = mock(Usuario.class);
		recetaFalsa = mock(Receta.class);
		vegano = new Vegano();
		hipertenso = new Hipertenso();
		diabetico = new Diabetico();
		celiaco = new Celiaco();
	}

	// Tests de SubsanaCondicion
	@Test
	public void UsuarioSubsanaCondicionCeliaco() {
		assertTrue(celiaco.subsanaCondicion(usuarioFalso));
	}

	@Test
	public void UsuarioSubsanaCondicionVegano() {
		when(usuarioFalso.tienePreferencia("fruta")).thenReturn(true);

		assertTrue(vegano.subsanaCondicion(usuarioFalso));

		verify(usuarioFalso, times(1)).tienePreferencia("fruta");
	}

	@Test
	public void UsuarioNoSubsanaCondicionVegano() {
		when(usuarioFalso.tienePreferencia("fruta")).thenReturn(false);

		assertFalse(vegano.subsanaCondicion(usuarioFalso));

		verify(usuarioFalso, times(1)).tienePreferencia("fruta");
	}

	@Test
	public void UsuarioSubsanaCondicionHipertenso() {
		when(usuarioFalso.tieneRutinaIntensiva()).thenReturn(true);

		assertTrue(hipertenso.subsanaCondicion(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneRutinaIntensiva();
	}

	@Test
	public void UsuarioNoSubsanaCondicionHipertenso() {
		when(usuarioFalso.tieneRutinaIntensiva()).thenReturn(false);

		assertFalse(hipertenso.subsanaCondicion(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneRutinaIntensiva();
	}

	@Test
	public void UsuarioSubsanaCondicionDiabeticoPorPeso() {
		when(usuarioFalso.getPeso()).thenReturn(65.00);

		assertTrue(diabetico.subsanaCondicion(usuarioFalso));

		verify(usuarioFalso, times(1)).getPeso();
	}

	@Test
	public void UsuarioSubsanaCondicionDiabeticoPorRutina() {
		when(usuarioFalso.getPeso()).thenReturn(85.00);
		when(usuarioFalso.tieneRutinaActiva()).thenReturn(true);

		assertTrue(diabetico.subsanaCondicion(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneRutinaActiva();
		verify(usuarioFalso, times(1)).getPeso();
	}

	@Test
	public void UsuarioNoSubsanaCondicionDiabetic() {
		when(usuarioFalso.getPeso()).thenReturn(85.00);
		when(usuarioFalso.tieneRutinaActiva()).thenReturn(false);

		assertFalse(diabetico.subsanaCondicion(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneRutinaActiva();
		verify(usuarioFalso, times(1)).getPeso();
	}

	// Tests de Usuarios validos para esa condicion
	@Test
	public void UsuarioCeliacoValido() {
		assertTrue(celiaco.esUsuarioValido(usuarioFalso));
	}

	@Test
	public void UsuarioHipertensoValido() {
		when(usuarioFalso.tieneAlgunaPreferencia()).thenReturn(true);

		assertTrue(hipertenso.esUsuarioValido(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneAlgunaPreferencia();
	}

	@Test
	public void UsuarioHipertensoNoEsValido() {
		when(usuarioFalso.tieneAlgunaPreferencia()).thenReturn(false);

		assertFalse(hipertenso.esUsuarioValido(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneAlgunaPreferencia();
	}

	@Test
	public void UsuarioDiabeticoValido() {
		when(usuarioFalso.indicaSexo()).thenReturn(true);
		when(usuarioFalso.tieneAlgunaPreferencia()).thenReturn(true);

		assertTrue(diabetico.esUsuarioValido(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneAlgunaPreferencia();
		verify(usuarioFalso, times(1)).indicaSexo();
	}

	@Test
	public void UsuarioDiabeticoNoEsValido() {
		when(usuarioFalso.indicaSexo()).thenReturn(false);

		assertFalse(diabetico.esUsuarioValido(usuarioFalso));

		verify(usuarioFalso, times(1)).indicaSexo();
	}

	@Test
	public void UsuarioVeganoValido() {
		when(
				usuarioFalso.tieneAlgunaDeEstasPreferencias(Vegano
						.getPreferenciasProhibidas())).thenReturn(false);

		assertTrue(vegano.esUsuarioValido(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneAlgunaDeEstasPreferencias(
				Vegano.getPreferenciasProhibidas());
	}

	@Test
	public void UsuarioVeganoNoEsValido() {
		when(
				usuarioFalso.tieneAlgunaDeEstasPreferencias(Vegano
						.getPreferenciasProhibidas())).thenReturn(true);

		assertFalse(vegano.esUsuarioValido(usuarioFalso));

		verify(usuarioFalso, times(1)).tieneAlgunaDeEstasPreferencias(
				Vegano.getPreferenciasProhibidas());
	}

	// Tests de receta inadecuada para esa condicion
	@Test
	public void recetaCeliacaSiempreEsAdecuada() {

		assertFalse(celiaco.esInadecuada(recetaFalsa));
	}

	@Test
	public void recetaVeganaEsInadecuadaSiTieneIngredientesProhibidos() {
		when(
				recetaFalsa.tieneAlgunIngredienteDeEstos(Vegano
						.getPreferenciasProhibidas())).thenReturn(true);

		assertTrue(vegano.esInadecuada(recetaFalsa));

		verify(recetaFalsa, times(1)).tieneAlgunIngredienteDeEstos(
				Vegano.getPreferenciasProhibidas());
	}

	@Test
	public void recetaDiabeticaEsInadecuadaSiTieneCondimentosProhibidos() {
		when(recetaFalsa.tenesMasDe(Diabetico.GetCondimentosProhibidos()))
				.thenReturn(true);

		assertTrue(diabetico.esInadecuada(recetaFalsa));

		verify(recetaFalsa, times(1)).tenesMasDe(
				Diabetico.GetCondimentosProhibidos());
	}

	@Test
	public void recetaHipertensaEsInadecuadaSiTieneCondimentosProhibidos() {
		when(recetaFalsa.tenesAlgoDe(Hipertenso.getCondimentosProhibidos()))
				.thenReturn(true);

		assertTrue(hipertenso.esInadecuada(recetaFalsa));

		verify(recetaFalsa, times(1)).tenesAlgoDe(
				Hipertenso.getCondimentosProhibidos());
	}
}