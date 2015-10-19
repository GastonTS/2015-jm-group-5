package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import jdk.nashorn.internal.ir.annotations.Ignore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.Filtro;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class ConsultaTest {

	ConsultorRecetas consultorMock = mock(ConsultorRecetas.class);
	private Usuario gaston = mock(Usuario.class);

	private ObservadorConsultas ObserverConsultaMock = mock(ObservadorConsultas.class);
	private ObservadorConsultas ObserverConsultaMock2 = mock(ObservadorConsultas.class);

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);

	private Filtro filtroMock = mock(Filtro.class);
	
	@Before
	public void setUp() {
		Consulta.agregarObservador(ObserverConsultaMock);
		Consulta.agregarObservador(ObserverConsultaMock2);
	}
	
	@After
	public void after() {
		Consulta.quitarObservador(ObserverConsultaMock);
		Consulta.quitarObservador(ObserverConsultaMock2);
	}

	@org.junit.Ignore
	@Test
	public void consultaRecetas() {
		Collection<Receta> recetasAConsultar = Arrays.asList(panchoMock, recetaMock, ensaladaMock);
		Collection<Receta> resultadoConsulta = Arrays.asList(panchoMock, recetaMock);
		when(consultorMock.getRecetas(gaston)).thenReturn(recetasAConsultar);
		when(filtroMock.filtrarRecetas(recetasAConsultar, gaston)).thenReturn(resultadoConsulta);

		Consulta gestorConsulta = new Consulta(consultorMock, filtroMock, gaston);
		
		assertEquals(gestorConsulta.getRecetasConsultadas(), resultadoConsulta);

		verify(consultorMock, times(1)).getRecetas(gaston);
		verify(filtroMock, times(1)).filtrarRecetas(recetasAConsultar, gaston);
		verify(ObserverConsultaMock, times(1)).notificarConsulta(gaston, resultadoConsulta);
		verify(ObserverConsultaMock2, times(1)).notificarConsulta(gaston, resultadoConsulta);
	}

	@org.junit.Ignore//TODO
	@Test
	public void retornaParametrosDeBusquedaSegunNombresDeSuFiltro() {
		Consulta gestorConsulta = new Consulta(consultorMock, filtroMock, gaston);
		
		when(filtroMock.getNombresFiltros()).thenReturn(Stream.of("parámetro de busqueda 1", "parámetro de busqueda 2"));
		assertEquals("\t-> parámetro de busqueda 1.\n\t-> parámetro de busqueda 2.\n", gestorConsulta.parametrosDeBusquedaToString());
		
		verify(filtroMock, times(1)).getNombresFiltros();
	}
	
} 
