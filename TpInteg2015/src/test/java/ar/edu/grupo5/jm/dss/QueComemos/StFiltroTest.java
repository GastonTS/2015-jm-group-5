package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StGestorDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StCondicionUsuario;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StExcesoCalorias;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StNoLeDisgustaAlUsuario;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StOrdenadosPorCriterio;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StPreparacionBarata;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StPrimeros10;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StSoloPares;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class StFiltroTest {
	private Usuario usuarioMock = mock(Usuario.class);

	private Receta guisoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta vegetarianaMock = mock(Receta.class);

	private Collection<Receta> recetas = new ArrayList<Receta>();

	private Collection<String> ingredientesCaros = Arrays.asList("lechon", "lomo", "salmon", "alcaparras");

	private StExcesoCalorias excesoDeCalorias = new StExcesoCalorias();
	private StCondicionUsuario segunCondicionesDelusuario = new StCondicionUsuario();
	private StPreparacionBarata preparacionBarata = new StPreparacionBarata();
	private StNoLeDisgustaAlUsuario leGustaAlUsuario = new StNoLeDisgustaAlUsuario();

	private StPrimeros10 primeros10 = new StPrimeros10();
	private StSoloPares soloPares = new StSoloPares();
	private StOrdenadosPorCriterio ordenadosPorCalorias = new StOrdenadosPorCriterio(
			((receta1, receta2) -> ((Double) receta1.getCantCaloriasTotales()).compareTo((Double) receta2.getCantCaloriasTotales())));

	private StOrdenadosPorCriterio ordenadosAlfabeticamente = new StOrdenadosPorCriterio(((receta1, receta2) -> (receta1.getNombre()).compareTo(receta2
			.getNombre())));

	private StGestorDeConsultas combinacionPostProcesadoConFiltro = new StGestorDeConsultas(Arrays.asList(excesoDeCalorias), Arrays.asList(soloPares));

	private StGestorDeConsultas superPreFiltro = new StGestorDeConsultas(Arrays.asList(excesoDeCalorias, segunCondicionesDelusuario, preparacionBarata,
			leGustaAlUsuario), null);

	@Before
	public void setUp() {
		recetas.add(guisoMock);
		recetas.add(ensaladaMock);
		recetas.add(panchoMock);
		recetas.add(vegetarianaMock);
	}

	@Test
	public void excesoDeCaloriasDevuelveLasQueTenganMenosDe500() {
		when(guisoMock.getCantCaloriasTotales()).thenReturn((double) 550);
		when(ensaladaMock.getCantCaloriasTotales()).thenReturn((double) 200);
		when(panchoMock.getCantCaloriasTotales()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCaloriasTotales()).thenReturn((double) 400);

		Collection<Receta> todasLasRecetas = Arrays.asList(guisoMock, ensaladaMock, panchoMock, vegetarianaMock);

		Collection<Receta> menosDe500Calorias = Arrays.asList(ensaladaMock, vegetarianaMock);

		assertEquals(todasLasRecetas.stream().filter(unaReceta -> excesoDeCalorias.filtrar(unaReceta, usuarioMock)).collect(Collectors.toList()),
				menosDe500Calorias);

		verify(guisoMock, times(1)).getCantCaloriasTotales();
		verify(ensaladaMock, times(1)).getCantCaloriasTotales();
		verify(panchoMock, times(1)).getCantCaloriasTotales();
		verify(vegetarianaMock, times(1)).getCantCaloriasTotales();
	}

	@Test
	public void segunCondicionesDeUsuarioDevuelveSoloLasAdecuadas() {
		when(usuarioMock.sosRecetaInadecuadaParaMi(guisoMock)).thenReturn(true);
		when(usuarioMock.sosRecetaInadecuadaParaMi(ensaladaMock)).thenReturn(false);
		when(usuarioMock.sosRecetaInadecuadaParaMi(panchoMock)).thenReturn(true);
		when(usuarioMock.sosRecetaInadecuadaParaMi(vegetarianaMock)).thenReturn(false);

		Collection<Receta> todasLasRecetas = Arrays.asList(guisoMock, ensaladaMock, panchoMock, vegetarianaMock);

		Collection<Receta> sonAdecuadasParaUsuario = Arrays.asList(ensaladaMock, vegetarianaMock);

		assertEquals(todasLasRecetas.stream().filter(unaReceta -> segunCondicionesDelusuario.filtrar(unaReceta, usuarioMock)).collect(Collectors.toList()),
				sonAdecuadasParaUsuario);

		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(guisoMock);
		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(ensaladaMock);
		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(panchoMock);
		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(vegetarianaMock);

	}

	@Test
	public void leGustaAlUsuarioSoloDevuelveLasQueLeGustan() {
		when(usuarioMock.noLeDisgusta(guisoMock)).thenReturn(true);
		when(usuarioMock.noLeDisgusta(ensaladaMock)).thenReturn(false);
		when(usuarioMock.noLeDisgusta(panchoMock)).thenReturn(true);
		when(usuarioMock.noLeDisgusta(vegetarianaMock)).thenReturn(false);

		Collection<Receta> todasLasRecetas = Arrays.asList(guisoMock, ensaladaMock, panchoMock, vegetarianaMock);

		Collection<Receta> noLeDisgustanAlUsuario = Arrays.asList(guisoMock, panchoMock);

		assertEquals(todasLasRecetas.stream().filter(unaReceta -> leGustaAlUsuario.filtrar(unaReceta, usuarioMock)).collect(Collectors.toList()),
				noLeDisgustanAlUsuario);

		verify(usuarioMock, times(1)).noLeDisgusta(guisoMock);
		verify(usuarioMock, times(1)).noLeDisgusta(ensaladaMock);
		verify(usuarioMock, times(1)).noLeDisgusta(panchoMock);
		verify(usuarioMock, times(1)).noLeDisgusta(vegetarianaMock);

	}

	@Test
	public void siEsCaraSuPreparacionNoLaDevuelve() {
		when(guisoMock.tieneAlgunIngredienteDeEstos(ingredientesCaros)).thenReturn(true);
		when(ensaladaMock.tieneAlgunIngredienteDeEstos(ingredientesCaros)).thenReturn(false);
		when(panchoMock.tieneAlgunIngredienteDeEstos(ingredientesCaros)).thenReturn(false);
		when(vegetarianaMock.tieneAlgunIngredienteDeEstos(ingredientesCaros)).thenReturn(true);

		Collection<Receta> todasLasRecetas = Arrays.asList(guisoMock, ensaladaMock, panchoMock, vegetarianaMock);

		Collection<Receta> recetasBaratas = Arrays.asList(ensaladaMock, panchoMock);

		assertEquals(todasLasRecetas.stream().filter(unaReceta -> preparacionBarata.filtrar(unaReceta, usuarioMock)).collect(Collectors.toList()),
				recetasBaratas);

		verify(guisoMock, times(1)).tieneAlgunIngredienteDeEstos(ingredientesCaros);
		verify(ensaladaMock, times(1)).tieneAlgunIngredienteDeEstos(ingredientesCaros);
		verify(panchoMock, times(1)).tieneAlgunIngredienteDeEstos(ingredientesCaros);
		verify(vegetarianaMock, times(1)).tieneAlgunIngredienteDeEstos(ingredientesCaros);
	}

	@Test
	public void superFiltroAplicaTodosLosFiltrosEnOrden() {
		when(guisoMock.getCantCaloriasTotales()).thenReturn((double) 550);
		when(ensaladaMock.getCantCaloriasTotales()).thenReturn((double) 200);
		when(panchoMock.getCantCaloriasTotales()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCaloriasTotales()).thenReturn((double) 400);
		when(usuarioMock.noLeDisgusta(ensaladaMock)).thenReturn(true);
		when(usuarioMock.noLeDisgusta(vegetarianaMock)).thenReturn(false);

		when(usuarioMock.sosRecetaInadecuadaParaMi(ensaladaMock)).thenReturn(false);
		when(usuarioMock.sosRecetaInadecuadaParaMi(vegetarianaMock)).thenReturn(false);

		when(ensaladaMock.tieneAlgunIngredienteDeEstos(ingredientesCaros)).thenReturn(false);

		Collection<Receta> todasLasRecetas = Arrays.asList(guisoMock, ensaladaMock, panchoMock, vegetarianaMock);

		Collection<Receta> recetasFiltradas = Arrays.asList(ensaladaMock);

		assertEquals(superPreFiltro.aplicarFiltros(todasLasRecetas, usuarioMock), recetasFiltradas);

		verify(guisoMock, times(1)).getCantCaloriasTotales();
		verify(ensaladaMock, times(1)).getCantCaloriasTotales();
		verify(panchoMock, times(1)).getCantCaloriasTotales();
		verify(vegetarianaMock, times(1)).getCantCaloriasTotales();

		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(ensaladaMock);
		verify(usuarioMock, times(1)).sosRecetaInadecuadaParaMi(vegetarianaMock);

		verify(usuarioMock, times(1)).noLeDisgusta(ensaladaMock);
		verify(usuarioMock, times(1)).noLeDisgusta(vegetarianaMock);

		verify(ensaladaMock, times(1)).tieneAlgunIngredienteDeEstos(ingredientesCaros);
	}

	@Test
	public void primeros10() {
		Collection<Receta> receta10Elementos = Arrays.asList(guisoMock, panchoMock, guisoMock, panchoMock, guisoMock, panchoMock, guisoMock, panchoMock,
				guisoMock, panchoMock);

		Collection<Receta> receta12Elementos = new ArrayList<Receta>();
		receta12Elementos.addAll(receta10Elementos);
		receta12Elementos.add(ensaladaMock);
		receta12Elementos.add(vegetarianaMock);

		assertEquals(primeros10.procesarRecetas(receta12Elementos), receta10Elementos);

	}

	@Test
	public void soloPares() {
		Collection<Receta> recetaSoloPares = Arrays.asList(ensaladaMock, vegetarianaMock);

		assertEquals(soloPares.procesarRecetas(recetas), recetaSoloPares);
	}

	@Test
	public void ordenadosPorCalorias() {
		when(guisoMock.getCantCaloriasTotales()).thenReturn((double) 550);
		when(ensaladaMock.getCantCaloriasTotales()).thenReturn((double) 200);
		when(panchoMock.getCantCaloriasTotales()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCaloriasTotales()).thenReturn((double) 400);

		Collection<Receta> recetasPorCalorias = Arrays.asList(ensaladaMock, vegetarianaMock, panchoMock, guisoMock);

		assertEquals(ordenadosPorCalorias.procesarRecetas(recetas), recetasPorCalorias);

		verify(guisoMock, times(2)).getCantCaloriasTotales();
		verify(ensaladaMock, times(4)).getCantCaloriasTotales();
		verify(panchoMock, times(4)).getCantCaloriasTotales();
		verify(vegetarianaMock, times(2)).getCantCaloriasTotales();
	}

	@Test
	public void ordenadasAlfabeticamente() {
		when(guisoMock.getNombre()).thenReturn("Guiso");
		when(ensaladaMock.getNombre()).thenReturn("Ensalada");
		when(panchoMock.getNombre()).thenReturn("Pancho");
		when(vegetarianaMock.getNombre()).thenReturn("Vegetariana");

		Collection<Receta> recetasPorCalorias = Arrays.asList(ensaladaMock, guisoMock, panchoMock, vegetarianaMock);

		assertEquals(ordenadosAlfabeticamente.procesarRecetas(recetas), recetasPorCalorias);

		verify(guisoMock, times(3)).getNombre();
		verify(ensaladaMock, times(2)).getNombre();
		verify(panchoMock, times(3)).getNombre();
		verify(vegetarianaMock, times(2)).getNombre();
	}

	@Test
	public void FitradoPorPostprocesadoYPreProcesado() {
		when(guisoMock.getCantCaloriasTotales()).thenReturn((double) 550);
		when(ensaladaMock.getCantCaloriasTotales()).thenReturn((double) 200);
		when(panchoMock.getCantCaloriasTotales()).thenReturn((double) 500);
		when(vegetarianaMock.getCantCaloriasTotales()).thenReturn((double) 400);

		Collection<Receta> paresDeMenosDe500Calorias = new ArrayList<Receta>();
		paresDeMenosDe500Calorias.add(vegetarianaMock);

		assertEquals(combinacionPostProcesadoConFiltro.aplicar(recetas, usuarioMock), paresDeMenosDe500Calorias);

		verify(guisoMock, times(1)).getCantCaloriasTotales();
		verify(ensaladaMock, times(1)).getCantCaloriasTotales();
		verify(panchoMock, times(1)).getCantCaloriasTotales();
		verify(vegetarianaMock, times(1)).getCantCaloriasTotales();
	}
}
