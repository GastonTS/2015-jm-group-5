package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class ObserverTest {

	private Usuario usuarioMock = mock(Usuario.class);

	private Receta guisoMock = mock(Receta.class);
	private Receta ensaladaMock = mock(Receta.class);
	private Receta panchoMock = mock(Receta.class);
	private Receta vegetarianaMock = mock(Receta.class);

	private Collection<Receta> recetas = new ArrayList<Receta>();
	private Collection<Receta> recetasDeGuisoYPancho = new ArrayList<Receta>();
	private Collection<Receta> recetasDePanchoYEnsalada = new ArrayList<Receta>();

	PorHoraDelDia observerPorHoraDelDia = new PorHoraDelDia();
	int horaActual, horaSiguiente;
	MasConsultada observerRecetaMasConsultada = new MasConsultada();

	SegunSexo observerSegunSexo = new SegunSexo();

	@Before
	public void setUp() {
		recetas.add(guisoMock);
		recetas.add(ensaladaMock);
		recetas.add(panchoMock);
		recetas.add(vegetarianaMock);
		recetasDeGuisoYPancho.add(guisoMock);
		recetasDeGuisoYPancho.add(panchoMock);
		recetasDePanchoYEnsalada.add(panchoMock);
		recetasDePanchoYEnsalada.add(ensaladaMock);

		horaActual = Calendar.HOUR_OF_DAY;
		if (horaActual == 23)
			horaSiguiente = 0;
		else
			horaSiguiente = horaActual + 1;
	}

	@Test
	public void agregaCantidadDeConsultasALaHoraEnQueSeRealizan() {
		observerPorHoraDelDia.notificar(usuarioMock, recetas);
		observerPorHoraDelDia.notificar(usuarioMock, recetas);
		assertEquals(
				observerPorHoraDelDia.getConsultasPorHoraDelDia(horaActual), 2);
		assertEquals(
				observerPorHoraDelDia.getConsultasPorHoraDelDia(horaSiguiente),
				0);
	}

	@Test
	public void devuelveNombreYCantidadDeConsultasDeRecetaMasConsultada() {
		when(guisoMock.getNombre()).thenReturn("guiso");
		when(ensaladaMock.getNombre()).thenReturn("ensalada");
		when(panchoMock.getNombre()).thenReturn("pancho");
		when(vegetarianaMock.getNombre()).thenReturn("vegetariana");
		
		observerRecetaMasConsultada.notificar(usuarioMock, recetas);
		observerRecetaMasConsultada.notificar(usuarioMock, recetasDeGuisoYPancho);
		observerRecetaMasConsultada.notificar(usuarioMock, recetasDePanchoYEnsalada);
		
		assertEquals(observerRecetaMasConsultada.nombreRecetaMasConsultada(), Optional.of("pancho"));
		//assertEquals(observerRecetaMasConsultada.cantidadDeConsultasDeRecetaMAsConsultada(), 3);
		
		verify(guisoMock, times(2)).getNombre();
		verify(ensaladaMock, times(2)).getNombre();
		verify(panchoMock, times(3)).getNombre();
		verify(vegetarianaMock, times(1)).getNombre();
	}

	@Test
	public void usuarioMasculinoConsulta4Recetas() {
		when(usuarioMock.esDeSexo("Masculino")).thenReturn(true);
		observerSegunSexo.notificar(usuarioMock, recetas);
		assertEquals(observerSegunSexo.getConsultasMasculinas(), 4);
		assertEquals(observerSegunSexo.getConsultasFemeninas(), 0);
		verify(usuarioMock, times(1)).esDeSexo("Masculino");
	}
}
