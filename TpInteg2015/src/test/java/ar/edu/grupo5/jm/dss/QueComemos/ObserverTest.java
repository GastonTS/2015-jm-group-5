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
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.MasConsultada;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.PorHoraDelDia;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.SegunSexo;
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
	
	PorHoraDelDia observerPorHoraDelDia = new PorHoraDelDia(Clock.systemDefaultZone());
	MasConsultada observerRecetaMasConsultada = new MasConsultada();
	SegunSexo observerSegunSexo = new SegunSexo();
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

	@Test
	public void agregaCantidadDeConsultasALaHoraEnQueSeRealizan() {
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T10:03:30.00Z"), ZoneId.systemDefault()));
		observerPorHoraDelDia.notificarConsulta(usuarioMock, recetas);
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T11:04:30.00Z"), ZoneId.systemDefault()));
		observerPorHoraDelDia.notificarConsulta(usuarioMock, recetasDeGuisoYPancho);
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T11:10:30.00Z"), ZoneId.systemDefault()));
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
		assertEquals(observerRecetaMasConsultada.cantidadDeConsultasDeRecetaMAsConsultada(), 3);

	}

	@Test
	public void cantidadYNombreDeRecetasConsultadasDeHombresYMujeres() {

		when(usuarioMock.esDeSexo(Sexo.MASCULINO)).thenReturn(true);
		when(usuarioMockFem.esDeSexo(Sexo.FEMENINO)).thenReturn(true);

		observerSegunSexo.notificarConsulta(usuarioMock, recetas);
		observerSegunSexo.notificarConsulta(usuarioMock, recetasDeGuisoYPancho);
		observerSegunSexo.notificarConsulta(usuarioMock, recetasDePanchoYEnsalada);

		observerSegunSexo.notificarConsulta(usuarioMockFem, recetas);
		observerSegunSexo.notificarConsulta(usuarioMockFem, recetasDePanchoYEnsalada);
		observerSegunSexo.notificarConsulta(usuarioMockFem, recetaExtraEnsalada);

		assertEquals(observerSegunSexo.recetaHombre(), Optional.of(panchoMock));
		assertEquals(observerSegunSexo.cantidadRecetaMasConsultadaHombre(), 3);

		assertEquals(observerSegunSexo.recetaMujer(), Optional.of(ensaladaMock));
		assertEquals(observerSegunSexo.cantidadRecetaMasConsultadaMujer(), 4);

		verify(usuarioMock, times(3)).esDeSexo(Sexo.MASCULINO);
		verify(usuarioMockFem, times(3)).esDeSexo(Sexo.FEMENINO);
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
