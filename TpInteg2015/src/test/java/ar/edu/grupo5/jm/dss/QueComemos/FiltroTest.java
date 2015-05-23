package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FiltroTest {

	private Usuario usuarioMock = mock(Usuario.class);

	private Receta guisoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta vegetarianaMock = mock(Receta.class);

	private Collection<Receta> recetas = new ArrayList<Receta>();

	private SinFiltro sinFiltro = new SinFiltro();
	private ExcesoDeCalorias excesoDeCalorias = new ExcesoDeCalorias(sinFiltro);
	private SegunCondicionesDeUsuario segunCondicionesDelusuario = new SegunCondicionesDeUsuario(
			sinFiltro, usuarioMock);
	private PreparacionBarata preparacionBarata = new PreparacionBarata(
			sinFiltro);
	private LeGustaAlUsuario leGustaAlUsuario = new LeGustaAlUsuario(sinFiltro,
			usuarioMock);

	private ExcesoDeCalorias superFiltro = new ExcesoDeCalorias(
			new SegunCondicionesDeUsuario(new PreparacionBarata(
					new LeGustaAlUsuario(sinFiltro, usuarioMock)), usuarioMock));

	@Before
	public void setUp() {
		recetas.add(guisoMock);
		recetas.add(ensaladaMock);
		recetas.add(panchoMock);
		recetas.add(vegetarianaMock);
	}

	@Test
	public void sinFiltroDevuelveLoMismo() {
		assertEquals(sinFiltro.filtrarRecetas(recetas), recetas);
	}

	@Test
	public void excesoDeCaloriasDevuelveLasQueTenganMenosDe500() {
		when(guisoMock.getCantCalorias()).thenReturn((double) 550);
		when(ensaladaMock.getCantCalorias()).thenReturn((double) 200);
		when(panchoMock.getCantCalorias()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCalorias()).thenReturn((double) 400);

		Collection<Receta> menosDe500Calorias = new ArrayList<Receta>();
		menosDe500Calorias.add(ensaladaMock);
		menosDe500Calorias.add(vegetarianaMock);

		assertEquals(excesoDeCalorias.filtrarRecetas(recetas),
				menosDe500Calorias);

		verify(guisoMock, times(1)).getCantCalorias();
		verify(ensaladaMock, times(1)).getCantCalorias();
		verify(panchoMock, times(1)).getCantCalorias();
		verify(vegetarianaMock, times(1)).getCantCalorias();
	}

	@Test
	public void segunCondicionesDeUsuarioDevuelveSoloLasAdecuadas() {
		when(usuarioMock.sosRecetaInadecuadaParaMi(guisoMock)).thenReturn(true);
		when(usuarioMock.sosRecetaInadecuadaParaMi(ensaladaMock)).thenReturn(
				false);
		when(usuarioMock.sosRecetaInadecuadaParaMi(panchoMock))
				.thenReturn(true);
		when(usuarioMock.sosRecetaInadecuadaParaMi(vegetarianaMock))
				.thenReturn(false);

		Collection<Receta> sonAdecuadasParaUsuario = new ArrayList<Receta>();
		sonAdecuadasParaUsuario.add(ensaladaMock);
		sonAdecuadasParaUsuario.add(vegetarianaMock);

		assertEquals(segunCondicionesDelusuario.filtrarRecetas(recetas),
				sonAdecuadasParaUsuario);

		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(guisoMock);
		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(ensaladaMock);
		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(panchoMock);
		verify(usuarioMock, times(1))
				.sosRecetaInadecuadaParaMi(vegetarianaMock);

	}

	@Test
	public void leGustaAlUsuarioSoloDevuelveLasQueLeGustan() {
		when(usuarioMock.teGusta(guisoMock)).thenReturn(true);
		when(usuarioMock.teGusta(ensaladaMock)).thenReturn(false);
		when(usuarioMock.teGusta(panchoMock)).thenReturn(true);
		when(usuarioMock.teGusta(vegetarianaMock)).thenReturn(false);

		Collection<Receta> sonAdecuadasParaUsuario = new ArrayList<Receta>();
		sonAdecuadasParaUsuario.add(guisoMock);
		sonAdecuadasParaUsuario.add(panchoMock);

		assertEquals(leGustaAlUsuario.filtrarRecetas(recetas),
				sonAdecuadasParaUsuario);

		verify(usuarioMock, times(1)).teGusta(guisoMock);
		verify(usuarioMock, times(1)).teGusta(ensaladaMock);
		verify(usuarioMock, times(1)).teGusta(panchoMock);
		verify(usuarioMock, times(1)).teGusta(vegetarianaMock);

	}

	@Test
	public void siEsCaraSuPreparacionNoLaDevuelve() {
		when(
				guisoMock.tieneAlgunIngredienteDeEstos(Arrays.asList("lechon",
						"lomo", "salmon", "alcaparras"))).thenReturn(true);
		when(
				ensaladaMock.tieneAlgunIngredienteDeEstos(Arrays.asList(
						"lechon", "lomo", "salmon", "alcaparras"))).thenReturn(
				false);
		when(
				panchoMock.tieneAlgunIngredienteDeEstos(Arrays.asList("lechon",
						"lomo", "salmon", "alcaparras"))).thenReturn(false);
		when(
				vegetarianaMock.tieneAlgunIngredienteDeEstos(Arrays.asList(
						"lechon", "lomo", "salmon", "alcaparras"))).thenReturn(
				true);

		Collection<Receta> recetasBaratas = new ArrayList<Receta>();
		recetasBaratas.add(ensaladaMock);
		recetasBaratas.add(panchoMock);

		assertEquals(preparacionBarata.filtrarRecetas(recetas), recetasBaratas);

		verify(guisoMock, times(1)).tieneAlgunIngredienteDeEstos(
				Arrays.asList("lechon", "lomo", "salmon", "alcaparras"));
		verify(ensaladaMock, times(1)).tieneAlgunIngredienteDeEstos(
				Arrays.asList("lechon", "lomo", "salmon", "alcaparras"));
		verify(panchoMock, times(1)).tieneAlgunIngredienteDeEstos(
				Arrays.asList("lechon", "lomo", "salmon", "alcaparras"));
		verify(vegetarianaMock, times(1)).tieneAlgunIngredienteDeEstos(
				Arrays.asList("lechon", "lomo", "salmon", "alcaparras"));
	}

	@Test
	public void superFiltroAplicaTodosLosFiltrosEnOrden() {
		when(guisoMock.getCantCalorias()).thenReturn((double) 550);
		when(ensaladaMock.getCantCalorias()).thenReturn((double) 200);
		when(panchoMock.getCantCalorias()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCalorias()).thenReturn((double) 400);
		when(usuarioMock.teGusta(ensaladaMock)).thenReturn(true);
		when(usuarioMock.teGusta(vegetarianaMock)).thenReturn(false);

		when(usuarioMock.sosRecetaInadecuadaParaMi(ensaladaMock)).thenReturn(
				false);
		when(usuarioMock.sosRecetaInadecuadaParaMi(vegetarianaMock))
				.thenReturn(false);

		when(
				ensaladaMock.tieneAlgunIngredienteDeEstos(Arrays.asList(
						"lechon", "lomo", "salmon", "alcaparras"))).thenReturn(
				false);

		Collection<Receta> recetasFiltradas = new ArrayList<Receta>();
		recetasFiltradas.add(ensaladaMock);

		assertEquals(superFiltro.filtrarRecetas(recetas), recetasFiltradas);

		verify(guisoMock, times(1)).getCantCalorias();
		verify(ensaladaMock, times(1)).getCantCalorias();
		verify(panchoMock, times(1)).getCantCalorias();
		verify(vegetarianaMock, times(1)).getCantCalorias();

		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(ensaladaMock);
		verify(usuarioMock, times(1))
				.sosRecetaInadecuadaParaMi(vegetarianaMock);

		verify(usuarioMock, times(1)).teGusta(ensaladaMock);
		verify(usuarioMock, times(1)).teGusta(vegetarianaMock);

		verify(ensaladaMock, times(1)).tieneAlgunIngredienteDeEstos(
				Arrays.asList("lechon", "lomo", "salmon", "alcaparras"));
	}

}
