package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ConsultaVeganoRecetasDificles;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ConsultasSegunSexo;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ConsultasTotales;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.HoraIngresadaNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.PorHoraDelDia;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Vegano;

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
	
	private Consulta consulta = mock(Consulta.class);
	private Consulta consulta1 = mock(Consulta.class);
	private Consulta consulta2 = mock(Consulta.class);
	private Consulta consulta3 = mock(Consulta.class);
	private Consulta consulta4 = mock(Consulta.class);
	private Consulta consulta5 = mock(Consulta.class);
		
	
	PorHoraDelDia observerPorHoraDelDia = new PorHoraDelDia();
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
		observerPorHoraDelDia.notificarConsulta(consulta);
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T11:04:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")));
		observerPorHoraDelDia.notificarConsulta(consulta);
		
		observerPorHoraDelDia.setReloj(Clock.fixed(Instant.parse("2007-12-03T11:10:30.00Z"), ZoneId.of("America/Argentina/Buenos_Aires")));
		observerPorHoraDelDia.notificarConsulta(consulta);

		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(7), 1);
		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(8), 2);
		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(9), 0);
	}
	
	@Test
	public void devuelveNombreYCantidadDeConsultasDeRecetaMasConsultada() {
		when(consulta.getUsuario()).thenReturn(usuarioMock);
		when(consulta.getRecetasConsultadas()).thenReturn(recetas);
		when(consulta1.getUsuario()).thenReturn(usuarioMock);
		when(consulta1.getRecetasConsultadas()).thenReturn(recetasDeGuisoYPancho);
		when(consulta2.getUsuario()).thenReturn(usuarioMock);
		when(consulta2.getRecetasConsultadas()).thenReturn(recetasDePanchoYEnsalada);
		
		observerRecetaMasConsultada.notificarConsulta(consulta);
		observerRecetaMasConsultada.notificarConsulta(consulta1);
		observerRecetaMasConsultada.notificarConsulta(consulta2);

		assertEquals(observerRecetaMasConsultada.recetaMasConsultada(), Optional.of(panchoMock));
		assertEquals(observerRecetaMasConsultada.cantidadDeConsultasDeRecetaMasConsultada(), 3);

	}

	@Test
	public void cantidadYNombreDeRecetasConsultadasDeHombresYMujeres() {
		when(consulta.getUsuario()).thenReturn(usuarioMock);
		when(consulta.getRecetasConsultadas()).thenReturn(recetas);
		when(consulta1.getUsuario()).thenReturn(usuarioMock);
		when(consulta1.getRecetasConsultadas()).thenReturn(recetasDeGuisoYPancho);
		when(consulta2.getUsuario()).thenReturn(usuarioMock);
		when(consulta2.getRecetasConsultadas()).thenReturn(recetasDePanchoYEnsalada);
		
		
		when(consulta3.getUsuario()).thenReturn(usuarioMockFem);
		when(consulta3.getRecetasConsultadas()).thenReturn(recetas);
		when(consulta4.getUsuario()).thenReturn(usuarioMockFem);
		when(consulta4.getRecetasConsultadas()).thenReturn(recetasDePanchoYEnsalada);
		when(consulta5.getUsuario()).thenReturn(usuarioMockFem);
		when(consulta5.getRecetasConsultadas()).thenReturn(recetaExtraEnsalada);
		
		when(usuarioMock.getSexo()).thenReturn(Sexo.MASCULINO);
		when(usuarioMockFem.getSexo()).thenReturn(Sexo.FEMENINO);

		observerSegunSexo.notificarConsulta(consulta);
		observerSegunSexo.notificarConsulta(consulta1);
		observerSegunSexo.notificarConsulta(consulta2);

		observerSegunSexo.notificarConsulta(consulta3);
		observerSegunSexo.notificarConsulta(consulta4);
		observerSegunSexo.notificarConsulta(consulta5);
		
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
		
		when(consulta.getUsuario()).thenReturn(veganoMock);
		when(consulta.getRecetasConsultadas()).thenReturn(recetas);
		when(consulta1.getUsuario()).thenReturn(veganoMock);
		when(consulta1.getRecetasConsultadas()).thenReturn(recetasDePanchoYEnsalada);
		when(consulta2.getUsuario()).thenReturn(otroVeganoMock);
		when(consulta2.getRecetasConsultadas()).thenReturn(recetasDePanchoYEnsalada);
		when(consulta3.getUsuario()).thenReturn(noVeganoMock);
		when(consulta3.getRecetasConsultadas()).thenReturn(recetasDeGuisoYPancho);
		

		observerConsultaVeganoRecetasDificiles.notificarConsulta(consulta);
		observerConsultaVeganoRecetasDificiles.notificarConsulta(consulta1);
		observerConsultaVeganoRecetasDificiles.notificarConsulta(consulta2);
		observerConsultaVeganoRecetasDificiles.notificarConsulta(consulta3);
		
		assertEquals(observerConsultaVeganoRecetasDificiles.cantidadDeVeganos(), 2);

		verify(guisoMock, times(1)).esDificil();
		verify(panchoMock, times(1)).esDificil();
		verify(ensaladaMock, times(1)).esDificil();
	}
}
