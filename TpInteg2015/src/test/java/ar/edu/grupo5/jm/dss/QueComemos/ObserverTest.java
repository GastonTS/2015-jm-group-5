package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class ObserverTest {

	private Usuario usuarioMock = mock(Usuario.class);

	private Receta guisoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta vegetarianaMock = mock(Receta.class);

	private Collection<Receta> recetas = new ArrayList<Receta>();

	PorHoraDelDia observerPorHoraDelDia = new PorHoraDelDia();

	@Before
	public void setUp() {
		recetas.add(guisoMock);
		recetas.add(ensaladaMock);
		recetas.add(panchoMock);
		recetas.add(vegetarianaMock);
	}

	@Test
	public void agregaCantidadDeConsultasALaHoraEnQueSeRealizan() {
		observerPorHoraDelDia.notificar(usuarioMock, recetas);
		observerPorHoraDelDia.notificar(usuarioMock, recetas);
		assertEquals(
				observerPorHoraDelDia
						.getConsultasPorHoraDelDia(Calendar.HOUR_OF_DAY),
				2);
		assertEquals(
				observerPorHoraDelDia
						.getConsultasPorHoraDelDia(Calendar.HOUR_OF_DAY + 1),
				0);
	}
}
