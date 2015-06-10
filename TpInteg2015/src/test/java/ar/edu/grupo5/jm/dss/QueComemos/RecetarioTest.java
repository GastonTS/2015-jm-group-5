package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeAccederARecetaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeEliminarRecetaExeption;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.GestorDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Vegano;

public class RecetarioTest {

	private Usuario gustavo = mock(Usuario.class);
	private Usuario gaston = mock(Usuario.class);
	private Usuario juanchi = mock(Usuario.class);
	private Vegano condicionMock = mock(Vegano.class);
	private Collection<CondicionDeSalud> condicionesDeSalud = Arrays.asList(condicionMock);

	private Collection<Receta> recetasTotales = new ArrayList<Receta>();

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);

	private Receta nuevaEnsaladaMock = mock(Receta.class);

	private IFiltro filtroMock = mock(IFiltro.class);
	private GestorDeConsultas filtroStMock = mock(GestorDeConsultas.class);

	Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock, recetaMock);
	Collection<Receta> otroResultadoConsulta = Arrays.asList(panchoMock);

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
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston)).thenReturn(resultadoConsulta);

		assertEquals(Recetario.instancia.consultarRecetas(filtroMock, gaston), resultadoConsulta);

		verify(filtroMock, times(1)).filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston);
	}

	@Test
	public void consultasPorHora() {
		int horaSiguiente, horaActual = Calendar.HOUR_OF_DAY;
		if (horaActual == 23)
			horaSiguiente = 0;
		else
			horaSiguiente = +1;

		Recetario.instancia.consultarRecetas(filtroMock, gaston);
		Recetario.instancia.consultarRecetas(filtroMock, gaston);

		assertEquals(Recetario.instancia.getConsultasPorHoraDelDia(horaActual), 2);
		assertEquals(Recetario.instancia.getConsultasPorHoraDelDia(horaSiguiente), 0);
	}

	@Test
	public void devuelveNombreYCantidadDeConsultasDeRecetaMasConsultada() {
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston)).thenReturn(resultadoConsulta);
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gustavo), gustavo)).thenReturn(otroResultadoConsulta);

		Recetario.instancia.consultarRecetas(filtroMock, gaston);
		Recetario.instancia.consultarRecetas(filtroMock, gustavo);

		assertEquals(Recetario.instancia.recetaMasConsultada(Recetario.instancia.getRecetasConsultadasTotales()), Optional.of(panchoMock));
		assertEquals(Recetario.instancia.cantidadDeConsultasDeRecetaMAsConsultada(Recetario.instancia.getRecetasConsultadasTotales()), 2);
	}

	@Test
	public void cantidadYNombreDeRecetasConsultadasDeHombresYMujeres() {
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston)).thenReturn(resultadoConsulta);
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gustavo), gustavo)).thenReturn(otroResultadoConsulta);
		when(gaston.esDeSexo("Masculino")).thenReturn(true);
		when(gustavo.esDeSexo("Masculino")).thenReturn(true);
		when(gustavo.esDeSexo("Femenino")).thenReturn(true);

		Recetario.instancia.consultarRecetas(filtroMock, gaston);
		Recetario.instancia.consultarRecetas(filtroMock, gaston);
		Recetario.instancia.consultarRecetas(filtroMock, gustavo);

		assertEquals(Recetario.instancia.recetaMasConsultada(Recetario.instancia.getRecetasConsultadasTotalesHombres()), Optional.of(panchoMock));
		assertEquals(Recetario.instancia.cantidadDeConsultasDeRecetaMAsConsultada(Recetario.instancia.getRecetasConsultadasTotalesHombres()), 3);

		assertEquals(Recetario.instancia.recetaMasConsultada(Recetario.instancia.getRecetasConsultadasTotalesMujeres()), Optional.of(panchoMock));
		assertEquals(Recetario.instancia.cantidadDeConsultasDeRecetaMAsConsultada(Recetario.instancia.getRecetasConsultadasTotalesMujeres()), 1);

		verify(gaston, times(2)).esDeSexo("Masculino");
		verify(gustavo, times(1)).esDeSexo("Masculino");
		verify(gustavo, times(1)).esDeSexo("Femenino");
	}

	@Test
	public void cantidadDeVeganosQueConsultanRecetasDificiles() {
		DatosPersonales unosDatosPersonales = new DatosPersonales(null, "Masculino", null);
		Usuario vegano = new Usuario(unosDatosPersonales, null, new ArrayList<String>(), new ArrayList<String>(), condicionesDeSalud, null);
		Usuario otroVegano = new Usuario(unosDatosPersonales, null, new ArrayList<String>(), new ArrayList<String>(), condicionesDeSalud, null);
		when(recetaMock.esDificil()).thenReturn(true);
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston)).thenReturn(otroResultadoConsulta);
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(vegano), vegano)).thenReturn(resultadoConsulta);
		when(filtroMock.filtrarRecetas(Recetario.instancia.listarTodasPuedeAcceder(otroVegano), otroVegano)).thenReturn(resultadoConsulta);
		when(condicionMock.deboNotificar()).thenReturn(true);

		Recetario.instancia.consultarRecetas(filtroMock, gaston);
		Recetario.instancia.consultarRecetas(filtroMock, vegano);
		Recetario.instancia.consultarRecetas(filtroMock, otroVegano);

		assertEquals(Recetario.instancia.cantidadDeVeganosQueConsultaronDificiles(), 2);

		verify(recetaMock, times(2)).esDificil();
		verify(panchoMock, times(3)).esDificil();
		verify(condicionMock, times(2)).deboNotificar();
	}

	@Test
	public void consultaRecetasStrategy() {
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock, recetaMock);
		when(filtroStMock.aplicarFiltros(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston)).thenReturn(resultadoConsulta);

		assertEquals(Recetario.instancia.consultarRecetasSt(filtroStMock, gaston), resultadoConsulta);

		verify(filtroStMock, times(1)).aplicarFiltros(Recetario.instancia.listarTodasPuedeAcceder(gaston), gaston);
	}

}
