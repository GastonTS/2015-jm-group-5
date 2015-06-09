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

import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ConsultaVeganoRecetasDificles;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.MasConsultada;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.PorHoraDelDia;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.SegunSexo;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Vegano;

public class ObserverTest {

	private Usuario usuarioMock = mock(Usuario.class);
	private Usuario usuarioMockFem = mock(Usuario.class);
	private Usuario vegano;
	private Usuario otroVegano;
	
	private Receta guisoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta vegetarianaMock = mock(Receta.class);

	private Collection<Receta> recetas = new ArrayList<Receta>();
	private Collection<Receta> recetasDeGuisoYPancho = new ArrayList<Receta>();
	private Collection<Receta> recetasDePanchoYEnsalada = new ArrayList<Receta>();
	private Collection<Receta> recetaExtraEnsalada = new ArrayList<Receta>();

	private Vegano condicionMock = mock(Vegano.class);
	private Collection<CondicionDeSalud> condicionesDeSalud = Arrays.asList(condicionMock);
	
	PorHoraDelDia observerPorHoraDelDia = new PorHoraDelDia();
	MasConsultada observerRecetaMasConsultada = new MasConsultada();
	SegunSexo observerSegunSexo = new SegunSexo();
	ConsultaVeganoRecetasDificles observerConsultaVeganoRecetasDificiles = new ConsultaVeganoRecetasDificles();

	@Before
	public void setUp() {
		recetas = Arrays.asList(guisoMock, ensaladaMock, panchoMock, vegetarianaMock);
		recetasDeGuisoYPancho = Arrays.asList(guisoMock, panchoMock);
		recetasDePanchoYEnsalada = Arrays.asList(panchoMock, ensaladaMock);
		recetaExtraEnsalada = Arrays.asList(ensaladaMock, ensaladaMock);
		
		vegano = new Usuario(null, null, new ArrayList<String>(), new ArrayList<String>(), condicionesDeSalud, null);
		otroVegano = new Usuario(null, null, new ArrayList<String>(), new ArrayList<String>(), condicionesDeSalud, null);
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
		when(condicionMock.deboNotificar()).thenReturn(true);

		observerConsultaVeganoRecetasDificiles.notificar(vegano, recetas);
		observerConsultaVeganoRecetasDificiles.notificar(otroVegano, recetasDeGuisoYPancho);
		observerConsultaVeganoRecetasDificiles.notificar(usuarioMock, recetasDePanchoYEnsalada);

		assertEquals(observerConsultaVeganoRecetasDificiles.cantidadDeVeganos(), 2);

		verify(guisoMock, times(2)).esDificil();
		verify(panchoMock, times(1)).esDificil();
		verify(ensaladaMock, times(1)).esDificil();
		verify(condicionMock, times(2)).deboNotificar();
	}
}
