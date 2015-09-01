package ar.edu.grupo5.jm.dss.QueComemos.RecetaTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeAccederARecetaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeEliminarRecetaExeption;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class RecetarioTest {

	private Usuario gustavo = mock(Usuario.class);
	private Usuario gaston = mock(Usuario.class);
	private Usuario juanchi = mock(Usuario.class);

	private Collection<Receta> recetasTotales = new ArrayList<Receta>();

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);

	private Receta nuevaEnsaladaMock = mock(Receta.class);

	@Before
	public void setUp() {

		recetasTotales.add(ensaladaMock);
		recetasTotales.add(panchoMock);
		Recetario.instancia.setRecetasTotales(recetasTotales);
	}

	@Test
	public void listarTodasPuedeAccederTest() {
		when(gaston.puedeAccederA(ensaladaMock)).thenReturn(true);
		when(gaston.puedeAccederA(panchoMock)).thenReturn(false);

		Collection<Receta> recetasQuePuedeAcceder = Recetario.instancia
				.listarTodasPuedeAcceder(gaston);

		assertTrue(recetasQuePuedeAcceder.contains(ensaladaMock));
		assertFalse(recetasQuePuedeAcceder.contains(panchoMock));

		verify(gaston, times(1)).puedeAccederA(ensaladaMock);
		verify(gaston, times(1)).puedeAccederA(panchoMock);
	}

	@Test
	public void gastonCreaRecetaExitosa() {
		Recetario.instancia.crearReceta(recetaMock, gustavo);
		assertTrue(Recetario.instancia.getRecetasTotales().contains(recetaMock));
		verify(recetaMock, times(1)).setDueño(gustavo);
	}

	@Test
	public void gastonCreaRecetaConSubrecetas() {
		when(gaston.puedeAccederA(panchoMock)).thenReturn(true);
		when(gaston.puedeAccederA(ensaladaMock)).thenReturn(true);

		Recetario.instancia.crearRecetaConSubRecetas(recetaMock,
				Arrays.asList(panchoMock, ensaladaMock), gaston);

		verify(gaston, times(1)).puedeAccederA(panchoMock);
		verify(gaston, times(1)).puedeAccederA(ensaladaMock);
		verify(recetaMock, times(1)).agregarSubRecetas(
				Arrays.asList(panchoMock, ensaladaMock));
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void noPuedeCrearRecetaConSubRecetasSinAccesoAEllas() {
		when(gaston.puedeAccederA(panchoMock)).thenReturn(true);
		when(gaston.puedeAccederA(ensaladaMock)).thenReturn(false);

		Recetario.instancia.crearRecetaConSubRecetas(recetaMock,
				Arrays.asList(panchoMock, ensaladaMock), gaston);

		verify(gaston, times(1)).puedeAccederA(panchoMock);
		verify(gaston, times(1)).puedeAccederA(ensaladaMock);
	}

	@Test
	public void eliminarUnaReceta() {
		when(panchoMock.esElDueño(gaston)).thenReturn(true);

		Recetario.instancia.eliminarReceta(panchoMock, gaston);

		verify(panchoMock, times(1)).esElDueño(gaston);
		verify(gaston, times(1)).quitarRecetaFavorita(panchoMock);
	}

	@Test(expected = NoPuedeEliminarRecetaExeption.class)
	public void noPuedeEliminarUnaRecetaQueNoCreo() {
		Recetario.instancia.eliminarReceta(panchoMock, gaston);
	}

	@Test
	public void juanchiModificaReceta() {
		when(juanchi.puedeAccederA(ensaladaMock)).thenReturn(true);
		when(ensaladaMock.esElDueño(juanchi)).thenReturn(false);

		Recetario.instancia.modificarReceta(ensaladaMock, nuevaEnsaladaMock,
				juanchi);

		verify(juanchi, times(1)).puedeAccederA(ensaladaMock);
		verify(ensaladaMock, times(1)).esElDueño(juanchi);
		verify(nuevaEnsaladaMock, times(1)).setDueño(juanchi);
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void gastonNoPuedeModificarUnaRecetaDeOtro() {
		Recetario.instancia.modificarReceta(ensaladaMock, nuevaEnsaladaMock,
				gaston);
	}

	@Test
	public void recetaEsModificadaCorrectamente() {
		//testIntegral
		Usuario pepita = new UsuarioBuilder()
				.setNombre("pepita")
				.setFechaDeNacimiento(LocalDate.parse("2000-01-01"))
				.setPeso(1)
				.setEstatura(1)
				.setRutina(Rutina.INTENSIVA)
				.construirUsuario();

		Condimentacion sal = new Condimentacion("sal fina", 100);
		Condimentacion pocaSal = new Condimentacion("sal fina", 50);
		Condimentacion aceite = new Condimentacion("Aceite de Maiz", 2);

		Receta ensaladaVieja = new RecetaBuilder().setNombre("Ensalada")
				.agregarIngrediente("Lechuga 2kg")
				.agregarIngrediente("Cebolla 1.5kg")
				.agregarIngrediente("Tomate 200gr")
				.agregarCondimentaciones(sal).agregarCondimentaciones(aceite)
				.setCantCalorias(40).setDificultad(Dificultad.BAJA)
				.construirReceta();

		Receta ensaladaNueva = new RecetaBuilder().setNombre("Ensalada")
				.agregarIngrediente("Lechuga 3kg")
				.agregarIngrediente("Cebolla 1kg")
				.agregarIngrediente("Tomate 300gr")
				.agregarCondimentaciones(pocaSal)
				.agregarCondimentaciones(aceite).setCantCalorias(40)
				.setDificultad(Dificultad.BAJA).construirReceta();
		
		Recetario.instancia.crearReceta(ensaladaNueva, pepita);
		Recetario.instancia.crearReceta(ensaladaVieja, pepita);
		
		assertFalse(Recetario.instancia.sonObjetosActualizados(ensaladaVieja,
				ensaladaNueva));

		Recetario.instancia.modificarReceta(ensaladaNueva, ensaladaVieja,
				pepita);

		assertTrue(Recetario.instancia.sonObjetosActualizados(ensaladaVieja,
				ensaladaNueva));

	}

}
