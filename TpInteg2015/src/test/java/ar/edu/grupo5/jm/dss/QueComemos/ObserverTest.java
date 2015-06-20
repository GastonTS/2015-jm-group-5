package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ConsultaVeganoRecetasDificles;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.MasConsultada;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.PorHoraDelDia;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.SegunSexo;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Celiaco;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Vegano;

public class ObserverTest {
	
	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);
	private Complexion complexionMock = mock(Complexion.class);

	private Usuario usuarioMock = mock(Usuario.class);
	private Usuario usuarioMockFem = mock(Usuario.class);
	private Usuario vegano;
	private Usuario otroVegano;
	private Usuario unNoVegano;
	
	private Receta guisoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta vegetarianaMock = mock(Receta.class);

	private Collection<Receta> recetas = new ArrayList<Receta>();
	private Collection<Receta> recetasDeGuisoYPancho = new ArrayList<Receta>();
	private Collection<Receta> recetasDePanchoYEnsalada = new ArrayList<Receta>();
	private Collection<Receta> recetaExtraEnsalada = new ArrayList<Receta>();

	private Vegano condicionMock = mock(Vegano.class);
	
	PorHoraDelDia observerPorHoraDelDia = new PorHoraDelDia();
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
		
		vegano = new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.agregarCondicionesDeSalud(new Vegano())
				.setRutina(Rutina.ALTA)
				.construirUsuario();
				
		otroVegano = new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.agregarCondicionesDeSalud(new Vegano())
				.setRutina(Rutina.ALTA)
				.construirUsuario();
				
		unNoVegano = new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.agregarCondicionesDeSalud(new Celiaco())
				.setRutina(Rutina.ALTA)
				.construirUsuario();
	}

	@Test
	public void agregaCantidadDeConsultasALaHoraEnQueSeRealizan() {
		int horaSiguiente, horaActual = Calendar.HOUR_OF_DAY;
		if (horaActual == 23)
			horaSiguiente = 0;
		else
			horaSiguiente = +1;

		observerPorHoraDelDia.notificar(usuarioMock, recetas);
		observerPorHoraDelDia.notificar(usuarioMock, recetasDeGuisoYPancho);

		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(horaActual), 2);
		assertEquals(observerPorHoraDelDia.getConsultasPorHoraDelDia(horaSiguiente), 0);
	}

	@Test
	public void devuelveNombreYCantidadDeConsultasDeRecetaMasConsultada() {

		observerRecetaMasConsultada.notificar(usuarioMock, recetas);
		observerRecetaMasConsultada.notificar(usuarioMock, recetasDeGuisoYPancho);
		observerRecetaMasConsultada.notificar(usuarioMock, recetasDePanchoYEnsalada);

		assertEquals(observerRecetaMasConsultada.recetaMasConsultada(), Optional.of(panchoMock));
		assertEquals(observerRecetaMasConsultada.cantidadDeConsultasDeRecetaMAsConsultada(), 3);

	}

	@Test
	public void cantidadYNombreDeRecetasConsultadasDeHombresYMujeres() {

		when(usuarioMock.esDeSexo("Masculino")).thenReturn(true);
		when(usuarioMockFem.esDeSexo("Femenino")).thenReturn(true);

		observerSegunSexo.notificar(usuarioMock, recetas);
		observerSegunSexo.notificar(usuarioMock, recetasDeGuisoYPancho);
		observerSegunSexo.notificar(usuarioMock, recetasDePanchoYEnsalada);

		observerSegunSexo.notificar(usuarioMockFem, recetas);
		observerSegunSexo.notificar(usuarioMockFem, recetasDePanchoYEnsalada);
		observerSegunSexo.notificar(usuarioMockFem, recetaExtraEnsalada);

		assertEquals(observerSegunSexo.recetaHombre(), Optional.of(panchoMock));
		assertEquals(observerSegunSexo.cantidadRecetaMasConsultadaHombre(), 3);

		assertEquals(observerSegunSexo.recetaMujer(), Optional.of(ensaladaMock));
		assertEquals(observerSegunSexo.cantidadRecetaMasConsultadaMujer(), 4);

		verify(usuarioMock, times(3)).esDeSexo("Masculino");
		verify(usuarioMockFem, times(3)).esDeSexo("Femenino");
	}

	@Test
	public void cantidadDeVeganosQueConsultanRecetasDificiles() {
		when(guisoMock.esDificil()).thenReturn(true);
		when(panchoMock.esDificil()).thenReturn(false);
		when(ensaladaMock.esDificil()).thenReturn(true);

		Collection<ObservadorConsultas> observadores = Arrays.asList(observerConsultaVeganoRecetasDificiles);
		
		vegano.notificar(observadores, recetas);
		otroVegano.notificar(observadores, recetasDePanchoYEnsalada);
		unNoVegano.notificar(observadores, recetasDeGuisoYPancho);
		assertEquals(observerConsultaVeganoRecetasDificiles.cantidadDeVeganos(), 2);

		verify(guisoMock, times(1)).esDificil();
		verify(panchoMock, times(1)).esDificil();
		verify(ensaladaMock, times(1)).esDificil();
	}
}
