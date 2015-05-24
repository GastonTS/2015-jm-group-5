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

	private ExcesoDeCalorias superPreFiltro = new ExcesoDeCalorias(
			new SegunCondicionesDeUsuario(new PreparacionBarata(
					new LeGustaAlUsuario(sinFiltro, usuarioMock)), usuarioMock));

	private Primeros10 primeros10 = new Primeros10(sinFiltro);
	private SoloPares soloPares = new SoloPares(sinFiltro);
	private OrdenadosPorCriterio ordenadosPorCalorias = new OrdenadosPorCriterio(
			sinFiltro,
			((receta1, receta2) -> ((Double) receta1.getCantCalorias())
					.compareTo((Double) receta2.getCantCalorias())));

	private OrdenadosPorCriterio ordenadosAlfabeticamente = new OrdenadosPorCriterio(
			sinFiltro,
			((receta1, receta2) -> (receta1.getNombre()).compareTo(receta2
					.getNombre())));

	private SoloPares combinacionPostProcesadoConPre = new SoloPares(
			new ExcesoDeCalorias(sinFiltro));
	
	private ExcesoDeCalorias combinacionPreProcesadoConPost = new ExcesoDeCalorias(
			new SoloPares(sinFiltro));

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
		when(usuarioMock.noLeDisgusta(guisoMock)).thenReturn(true);
		when(usuarioMock.noLeDisgusta(ensaladaMock)).thenReturn(false);
		when(usuarioMock.noLeDisgusta(panchoMock)).thenReturn(true);
		when(usuarioMock.noLeDisgusta(vegetarianaMock)).thenReturn(false);

		Collection<Receta> sonAdecuadasParaUsuario = new ArrayList<Receta>();
		sonAdecuadasParaUsuario.add(guisoMock);
		sonAdecuadasParaUsuario.add(panchoMock);

		assertEquals(leGustaAlUsuario.filtrarRecetas(recetas),
				sonAdecuadasParaUsuario);

		verify(usuarioMock, times(1)).noLeDisgusta(guisoMock);
		verify(usuarioMock, times(1)).noLeDisgusta(ensaladaMock);
		verify(usuarioMock, times(1)).noLeDisgusta(panchoMock);
		verify(usuarioMock, times(1)).noLeDisgusta(vegetarianaMock);

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
		when(usuarioMock.noLeDisgusta(ensaladaMock)).thenReturn(true);
		when(usuarioMock.noLeDisgusta(vegetarianaMock)).thenReturn(false);

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

		assertEquals(superPreFiltro.filtrarRecetas(recetas), recetasFiltradas);

		verify(guisoMock, times(1)).getCantCalorias();
		verify(ensaladaMock, times(1)).getCantCalorias();
		verify(panchoMock, times(1)).getCantCalorias();
		verify(vegetarianaMock, times(1)).getCantCalorias();

		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(ensaladaMock);
		verify(usuarioMock, times(1))
				.sosRecetaInadecuadaParaMi(vegetarianaMock);

		verify(usuarioMock, times(1)).noLeDisgusta(ensaladaMock);
		verify(usuarioMock, times(1)).noLeDisgusta(vegetarianaMock);

		verify(ensaladaMock, times(1)).tieneAlgunIngredienteDeEstos(
				Arrays.asList("lechon", "lomo", "salmon", "alcaparras"));
	}

	@Test
	public void primeros10() {
		Collection<Receta> receta10Elementos = new ArrayList<Receta>();
		receta10Elementos.add(guisoMock);
		receta10Elementos.add(panchoMock);
		receta10Elementos.add(guisoMock);
		receta10Elementos.add(panchoMock);
		receta10Elementos.add(guisoMock);
		receta10Elementos.add(panchoMock);
		receta10Elementos.add(guisoMock);
		receta10Elementos.add(panchoMock);
		receta10Elementos.add(guisoMock);
		receta10Elementos.add(panchoMock);
		Collection<Receta> receta12Elementos = new ArrayList<Receta>();
		receta12Elementos.addAll(receta10Elementos);
		receta12Elementos.add(ensaladaMock);
		receta12Elementos.add(vegetarianaMock);

		assertEquals(primeros10.filtrarRecetas(receta12Elementos),
				receta10Elementos);

	}

	@Test
	public void soloPares() {
		Collection<Receta> recetaSoloPares = new ArrayList<Receta>();
		recetaSoloPares.add(ensaladaMock);
		recetaSoloPares.add(vegetarianaMock);

		assertEquals(soloPares.filtrarRecetas(recetas), recetaSoloPares);
	}

	@Test
	public void ordenadosPorCalorias() {
		when(guisoMock.getCantCalorias()).thenReturn((double) 550);
		when(ensaladaMock.getCantCalorias()).thenReturn((double) 200);
		when(panchoMock.getCantCalorias()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCalorias()).thenReturn((double) 400);

		Collection<Receta> recetasPorCalorias = new ArrayList<Receta>();
		recetasPorCalorias.add(ensaladaMock);
		recetasPorCalorias.add(vegetarianaMock);
		recetasPorCalorias.add(panchoMock);
		recetasPorCalorias.add(guisoMock);

		assertEquals(ordenadosPorCalorias.filtrarRecetas(recetas),
				recetasPorCalorias);

		verify(guisoMock, times(2)).getCantCalorias();
		verify(ensaladaMock, times(4)).getCantCalorias();
		verify(panchoMock, times(4)).getCantCalorias();
		verify(vegetarianaMock, times(2)).getCantCalorias();
	}

	@Test
	public void ordenadasAlfabeticamente() {
		when(guisoMock.getNombre()).thenReturn("Guiso");
		when(ensaladaMock.getNombre()).thenReturn("Ensalada");
		when(panchoMock.getNombre()).thenReturn("Pancho");
		when(vegetarianaMock.getNombre()).thenReturn("Vegetariana");

		Collection<Receta> recetasPorCalorias = new ArrayList<Receta>();
		recetasPorCalorias.add(ensaladaMock);
		recetasPorCalorias.add(guisoMock);
		recetasPorCalorias.add(panchoMock);
		recetasPorCalorias.add(vegetarianaMock);

		assertEquals(ordenadosAlfabeticamente.filtrarRecetas(recetas),
				recetasPorCalorias);

		verify(guisoMock, times(3)).getNombre();
		verify(ensaladaMock, times(2)).getNombre();
		verify(panchoMock, times(3)).getNombre();
		verify(vegetarianaMock, times(2)).getNombre();
	}

	@Test
	public void sinImportarElOrdenEntrePostprocesadoYPreProcesado() {
		when(guisoMock.getCantCalorias()).thenReturn((double) 550);
		when(ensaladaMock.getCantCalorias()).thenReturn((double) 200);
		when(panchoMock.getCantCalorias()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCalorias()).thenReturn((double) 400);

		Collection<Receta> paresDeMenosDe500Calorias = new ArrayList<Receta>();
		paresDeMenosDe500Calorias.add(vegetarianaMock);

		assertEquals(combinacionPostProcesadoConPre.filtrarRecetas(recetas),
				paresDeMenosDe500Calorias);
		assertEquals(combinacionPreProcesadoConPost.filtrarRecetas(recetas),
				paresDeMenosDe500Calorias);

		verify(guisoMock, times(2)).getCantCalorias();
		verify(ensaladaMock, times(2)).getCantCalorias();
		verify(panchoMock, times(2)).getCantCalorias();
		verify(vegetarianaMock, times(2)).getCantCalorias();
	}

}
