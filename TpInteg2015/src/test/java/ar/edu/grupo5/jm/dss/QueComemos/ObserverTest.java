package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ConsultaVeganoRecetasDificles;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ConsultasSegunSexo;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.HoraIngresadaNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.PorHoraDelDia;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ConsultasTotales;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Vegano;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;

public class ObserverTest {
	private Usuario usuarioMock = mock(Usuario.class);
	private Usuario usuarioMockFem = mock(Usuario.class);
	private Usuario veganoMock  = mock(Usuario.class);
	private Usuario otroVeganoMock = mock(Usuario.class);
	private Usuario noVeganoMock = mock(Usuario.class);
	
	private Receta guisoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta vegetarianaMock = mock(Receta.class);

	private Collection<Receta> recetas = new ArrayList<Receta>();
	private Collection<Receta> recetasDeGuisoYPancho = new ArrayList<Receta>();
	private Collection<Receta> recetasDePanchoYEnsalada = new ArrayList<Receta>();
	private Collection<Receta> recetaExtraEnsalada = new ArrayList<Receta>();
	
	private Vegano condicionMock = mock(Vegano.class);
	
	PorHoraDelDia observerPorHoraDelDia = new PorHoraDelDia(Clock.system(ZoneId.of("America/Argentina/Buenos_Aires")));
	ConsultasTotales observerRecetaMasConsultada = new ConsultasTotales();
	ConsultasSegunSexo observerSegunSexo = new ConsultasSegunSexo();
	ConsultaVeganoRecetasDificles observerConsultaVeganoRecetasDificiles = new ConsultaVeganoRecetasDificles();

	@Before
	public void setUp() {
		when(condicionMock.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		
		recetas = Arrays.asList(guisoMock, ensaladaMock, panchoMock, vegetarianaMock);
		recetasDeGuisoYPancho = Arrays.asList(guisoMock, panchoMock);
		recetasDePanchoYEnsalada = Arrays.asList(panchoMock, ensaladaMock);
		recetaExtraEnsalada = Arrays.asList(ensaladaMock, ensaladaMock);
		
		when(veganoMock.esVegano()).thenReturn(true);
		when(noVeganoMock.esVegano()).thenReturn(false);
		when(otroVeganoMock.esVegano()).thenReturn(true);
	}

	@Test(expected = HoraIngresadaNoValidaException.class)
	public void noEsValidoSiConsultaUnaHoraFueraDelIntervaloPermitido() {
		observerPorHoraDelDia.getConsultasPorHoraDelDia(-1);
		observerPorHoraDelDia.getConsultasPorHoraDelDia(24);
	}
	
	@Test
	public void agregaCantidadDeConsultasALaHoraEnQueSeRealizan() {
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T10:03:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")));
		observerPorHoraDelDia.notificarConsulta(usuarioMock, recetas);
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T11:04:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")));
		observerPorHoraDelDia.notificarConsulta(usuarioMock, recetasDeGuisoYPancho);
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T11:10:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")));
		observerPorHoraDelDia.notificarConsulta(usuarioMock, recetaExtraEnsalada);

		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(7), 1);
		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(8), 2);
		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(9), 0);
	}
	
	@Test
	public void devuelveNombreYCantidadDeConsultasDeRecetaMasConsultada() {

		observerRecetaMasConsultada.notificarConsulta(usuarioMock, recetas);
		observerRecetaMasConsultada.notificarConsulta(usuarioMock, recetasDeGuisoYPancho);
		observerRecetaMasConsultada.notificarConsulta(usuarioMock, recetasDePanchoYEnsalada);

		assertEquals(observerRecetaMasConsultada.recetaMasConsultada(), Optional.of(panchoMock));
		assertEquals(observerRecetaMasConsultada.cantidadDeConsultasDeRecetaMasConsultada(), 3);

	}

	@Test
	public void cantidadYNombreDeRecetasConsultadasDeHombresYMujeres() {

		when(usuarioMock.getSexo()).thenReturn(Sexo.MASCULINO);
		when(usuarioMockFem.getSexo()).thenReturn(Sexo.FEMENINO);

		observerSegunSexo.notificarConsulta(usuarioMock, recetas);
		observerSegunSexo.notificarConsulta(usuarioMock, recetasDeGuisoYPancho);
		observerSegunSexo.notificarConsulta(usuarioMock, recetasDePanchoYEnsalada);

		observerSegunSexo.notificarConsulta(usuarioMockFem, recetas);
		observerSegunSexo.notificarConsulta(usuarioMockFem, recetasDePanchoYEnsalada);
		observerSegunSexo.notificarConsulta(usuarioMockFem, recetaExtraEnsalada);

		assertEquals(observerSegunSexo.recetaMasConsultadaPor(Sexo.MASCULINO), Optional.of(panchoMock));
		assertEquals(observerSegunSexo.cantidadRecetaMasConsultadaPor(Sexo.MASCULINO), 3);

		assertEquals(observerSegunSexo.recetaMasConsultadaPor(Sexo.FEMENINO), Optional.of(ensaladaMock));
		assertEquals(observerSegunSexo.cantidadRecetaMasConsultadaPor(Sexo.FEMENINO), 4);
		
		verify(usuarioMock, times(3)).getSexo();
		verify(usuarioMockFem, times(3)).getSexo();
	}

	@Test
	public void cantidadDeVeganosQueConsultanRecetasDificiles() {
		when(guisoMock.esDificil()).thenReturn(true);
		when(panchoMock.esDificil()).thenReturn(false);
		when(ensaladaMock.esDificil()).thenReturn(true);

		observerConsultaVeganoRecetasDificiles.notificarConsulta(veganoMock, recetas);
		observerConsultaVeganoRecetasDificiles.notificarConsulta(veganoMock, recetasDePanchoYEnsalada);
		observerConsultaVeganoRecetasDificiles.notificarConsulta(otroVeganoMock, recetasDePanchoYEnsalada);
		observerConsultaVeganoRecetasDificiles.notificarConsulta(noVeganoMock, recetasDeGuisoYPancho);
		
		assertEquals(observerConsultaVeganoRecetasDificiles.cantidadDeVeganos(), 2);

		verify(guisoMock, times(1)).esDificil();
		verify(panchoMock, times(1)).esDificil();
		verify(ensaladaMock, times(1)).esDificil();
	}
}
