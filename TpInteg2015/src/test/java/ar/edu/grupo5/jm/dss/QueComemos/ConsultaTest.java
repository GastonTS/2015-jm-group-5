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
import ar.edu.grupo5.jm.dss.QueComemos.Oberserver.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class ConsultaTest {

	ConsultorRecetas consultorMock = mock(ConsultorRecetas.class);
	GestorDeConsultas consulta = new GestorDeConsultas(consultorMock);

	private Usuario gaston = mock(Usuario.class);

	private ObservadorConsultas ObserverConsultaMock = mock(ObservadorConsultas.class);
	private ObservadorConsultas ObserverConsultaMock2 = mock(ObservadorConsultas.class);

	private Receta recetaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);

	private Filtro filtroMock = mock(Filtro.class);

	@Before
	public void setUp() {
		consulta.agregarObservador(ObserverConsultaMock);
		consulta.agregarObservador(ObserverConsultaMock2);
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
		verify(ObserverConsultaMock, times(1)).notificarConsulta(gaston, resultadoConsulta);
		verify(ObserverConsultaMock2, times(1)).notificarConsulta(gaston, resultadoConsulta);
	}
}
