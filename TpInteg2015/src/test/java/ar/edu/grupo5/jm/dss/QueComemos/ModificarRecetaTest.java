package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ModificarRecetaTest {
	private Receta ensalada;
	private Receta pancho;
	private Receta choripan;
	private Receta choripan2;
	private Usuario ramiro;

	private Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasRamiro;

	@Before
	public void setUp() {
		ensalada = mock(Receta.class);
		pancho = mock(Receta.class);
		choripan = mock(Receta.class);
		choripan2 = mock(Receta.class);

		recetasPublicas = Arrays.asList(ensalada, pancho);
		recetasRamiro = Arrays.asList(choripan);

		Usuario.recetasPublicas(recetasPublicas);

		// FIXME falta una abstraccion
		ramiro = new Usuario(null, null, 0, 0, null, recetasRamiro, null, null);
	}

	@Test
	@Ignore
	public void eliminarUnaRecetaPrivada() {
		assertTrue(ramiro.getRecetasPropias().contains(choripan));
		assertTrue(ramiro.getRecetasPropias().size() == 1);
		when(choripan.getNombre()).thenReturn("choripan");
		assertTrue(ramiro.getRecetasPropias().stream()
				.filter(unaReceta -> unaReceta.getNombre().equals("choripan"))
				.findAny().get().equals(choripan));
		// hasta aca anda
		// ramiro.eliminarRecetaPropia("choripan");

		ramiro.getRecetasPropias().add(ensalada);
		assertTrue(ramiro.getRecetasPropias().size() == 2);

		// ramiro.getRecetasPropias().remove(choripan);
		// assertTrue(ramiro.getRecetasPropias().isEmpty());
	}

	@Ignore
	@Test
	public void RamiroModificaRecetaPrivada() {
		assertTrue(ramiro.getRecetasPropias().contains(choripan));
		assertFalse(ramiro.getRecetasPropias().contains(choripan2));
		when(choripan.getNombre()).thenReturn("choripan");
		when(choripan2.getNombre()).thenReturn("choripan");
		when(choripan2.esValida()).thenReturn(true);
		when(ramiro.esRecetaPropia(choripan2)).thenReturn(true);
		assertTrue(ramiro.esRecetaPropia(choripan2));

		// ramiro.modificarReceta(choripan2);
		// assertTrue(ramiro.getRecetasPropias().contains(choripan2));
		// assertFalse(ramiro.getRecetasPropias().contains(choripan));
	}

}
