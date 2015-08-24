package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.Filtro;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.MasConsultada;
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.PorHoraDelDia;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.StrategyFilter.StGestorDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class ConsultaTest {

	ConsultorRecetas consultorMock = mock(ConsultorRecetas.class);
	GestorDeConsultas consulta = new GestorDeConsultas(consultorMock);
	
	private Usuario gaston = mock(Usuario.class);
	
	private MasConsultada masConsultadaMock = mock(MasConsultada.class);
	private PorHoraDelDia porHoraDelDiaMock = mock(PorHoraDelDia.class);
	
	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	
	private Filtro filtroMock = mock(Filtro.class);
	private StGestorDeConsultas filtroStMock = mock(StGestorDeConsultas.class);
	
	@Before
	public void setUp() {
		consulta.agregarObservador(masConsultadaMock);
		consulta.agregarObservador(porHoraDelDiaMock);
	}

	@Test
	public void consultaRecetasDecorador() {
		Collection<Receta> recetasAConsultar = Arrays.asList(panchoMock, recetaMock, ensaladaMock);
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock, recetaMock);
		when(consultorMock.getRecetas(gaston)).thenReturn(recetasAConsultar);
		when(filtroMock.filtrarRecetas(recetasAConsultar, gaston)).thenReturn(resultadoConsulta);

		assertEquals(consulta.consultarRecetas(filtroMock, gaston), resultadoConsulta);

		verify(consultorMock, times(1)).getRecetas(gaston);
		verify(filtroMock, times(1)).filtrarRecetas(recetasAConsultar, gaston);
		verify(masConsultadaMock, times(1)).notificarConsulta(gaston, resultadoConsulta);
		verify(porHoraDelDiaMock, times(1)).notificarConsulta(gaston, resultadoConsulta);
	}


	@Test
	public void consultaRecetasStrategy() {
		Collection<Receta> recetasAConsultar = Arrays.asList(panchoMock, recetaMock, ensaladaMock);
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock, recetaMock);
		when(consultorMock.getRecetas(gaston)).thenReturn(recetasAConsultar);
		when(filtroStMock.aplicarFiltros(recetasAConsultar, gaston)).thenReturn(resultadoConsulta);

		assertEquals(consulta.consultarRecetasSt(filtroStMock, gaston), resultadoConsulta);

		verify(consultorMock, times(1)).getRecetas(gaston);
		verify(filtroStMock, times(1)).aplicarFiltros(recetasAConsultar, gaston);
	}
	
}
