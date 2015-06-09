package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.MasConsultada;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.PorHoraDelDia;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeAccederARecetaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeEliminarRecetaExeption;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.GestorDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class RecetarioTest {

	private Usuario gustavo = mock(Usuario.class);
	private Usuario gaston = mock(Usuario.class);
	private Usuario juanchi = mock(Usuario.class);

	private Collection<Receta> recetasTotales = new ArrayList<Receta>();

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);

	private Receta nuevaEnsaladaMock = mock(Receta.class);

	private IFiltro filtroMock = mock(IFiltro.class);
	private GestorDeConsultas filtroStMock = mock(GestorDeConsultas.class);

	private MasConsultada masConsultadaMock = mock(MasConsultada.class);
	private PorHoraDelDia porHoraDelDiaMock = mock(PorHoraDelDia.class);

	@Before
	public void setUp() {

		recetasTotales.add(ensaladaMock);
		recetasTotales.add(panchoMock);
		Recetario.instancia.setRecetasTotales(recetasTotales);
		Recetario.instancia.agregarObservador(masConsultadaMock);
		Recetario.instancia.agregarObservador(porHoraDelDiaMock);

	}

	@Test
	public void listarTodasPuedeAccederTest() {
		when(gaston.puedeAccederA(ensaladaMock)).thenReturn(true);
		when(gaston.puedeAccederA(panchoMock)).thenReturn(false);

		Collection<Receta> recetasQuePuedeAcceder = Recetario.instancia.listarTodasPuedeAcceder(gaston);

		assertTrue(recetasQuePuedeAcceder.contains(ensaladaMock));
		assertFalse(recetasQuePuedeAcceder.contains(panchoMock));

		verify(gaston, times(1)).puedeAccederA(ensaladaMock);
		verify(gaston, times(1)).puedeAccederA(panchoMock);
	}

	@Test
	public void gastonCreaRecetaExitosa() {
		when(recetaMock.esValida()).thenReturn(true);

		Recetario.instancia.crearReceta(recetaMock, gustavo);

		assertTrue(Recetario.instancia.getRecetasTotales().contains(recetaMock));

		verify(recetaMock, times(1)).esValida();
		verify(recetaMock, times(1)).setDueño(gustavo);
	}

	@Test(expected = RecetaNoValidaException.class)
	public void usuarioCreaRecetaFallida() {
		Recetario.instancia.crearReceta(recetaMock, gaston);
	}

	@Test
	public void gastonCreaRecetaConSubrecetas() {
		when(gaston.puedeAccederA(panchoMock)).thenReturn(true);
		when(gaston.puedeAccederA(ensaladaMock)).thenReturn(true);
		when(recetaMock.esValida()).thenReturn(true);

		Recetario.instancia.crearRecetaConSubRecetas(recetaMock, Arrays.asList(panchoMock, ensaladaMock), gaston);

		verify(gaston, times(1)).puedeAccederA(panchoMock);
		verify(gaston, times(1)).puedeAccederA(ensaladaMock);
		verify(recetaMock, times(1)).esValida();
		verify(recetaMock, times(1)).agregarSubRecetas(Arrays.asList(panchoMock, ensaladaMock));
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void noPuedeCrearRecetaConSubRecetasSinAccesoAEllas() {
		when(gaston.puedeAccederA(panchoMock)).thenReturn(true);
		when(gaston.puedeAccederA(ensaladaMock)).thenReturn(false);

		Recetario.instancia.crearRecetaConSubRecetas(recetaMock, Arrays.asList(panchoMock, ensaladaMock), gaston);

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
		when(nuevaEnsaladaMock.esValida()).thenReturn(true);

		Recetario.instancia.modificarReceta(ensaladaMock, nuevaEnsaladaMock, juanchi);

		verify(juanchi, times(1)).puedeAccederA(ensaladaMock);
		verify(ensaladaMock, times(1)).esElDueño(juanchi);
		verify(nuevaEnsaladaMock, times(1)).esValida();
		verify(nuevaEnsaladaMock, times(1)).setDueño(juanchi);
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void gastonNoPuedeModificarUnaRecetaDeOtro() {
		Recetario.instancia.modificarReceta(ensaladaMock, nuevaEnsaladaMock, gaston);
	}

	@Test
	public void consultaRecetasDecorador() {
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock, recetaMock);
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston)).thenReturn(resultadoConsulta);

		assertEquals(Recetario.instancia.consultarRecetas(filtroMock, gaston), resultadoConsulta);

		verify(filtroMock, times(1)).filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston);
		verify(masConsultadaMock, times(1)).notificar(gaston, resultadoConsulta);
		verify(porHoraDelDiaMock, times(1)).notificar(gaston, resultadoConsulta);
	}

	@Test
	public void consultaRecetasStrategy() {
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock, recetaMock);
		when(filtroStMock.aplicarFiltros(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston)).thenReturn(resultadoConsulta);

		assertEquals(Recetario.instancia.consultarRecetasSt(filtroStMock, gaston), resultadoConsulta);

		verify(filtroStMock, times(1)).aplicarFiltros(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston);
	}

}
