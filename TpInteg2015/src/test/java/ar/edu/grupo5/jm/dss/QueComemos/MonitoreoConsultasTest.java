package ar.edu.grupo5.jm.dss.QueComemos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.*;

public class MonitoreoConsultasTest {

	private Consulta consultaMock = mock(Consulta.class);
	private Collection<Receta> recetasMock = new ArrayList<Receta>();
	private Receta receta1Mock = mock(Receta.class);
	private Receta receta2Mock = mock(Receta.class);
	private Receta receta3Mock = mock(Receta.class);
	private Usuario juanchi = mock(Usuario.class);

	LogearConsultasMasDe100 monitorMayor100 = new LogearConsultasMasDe100();
	AgregarRecetasAFavoritas monitorRecetasFavoritas = new AgregarRecetasAFavoritas();

	@Before
	public void setUp() {
		recetasMock.add(receta1Mock);
		recetasMock.add(receta2Mock);
		recetasMock.add(receta3Mock);
	}

	@Test
	public void leanConsultomuchasRecetas() {

		when(consultaMock.getNombre()).thenReturn("leandro");
		when(consultaMock.cantidadConsultas()).thenReturn(125);
		when(consultaMock.tieneMasDe100()).thenReturn(true);

		monitorMayor100.procesarConsulta(consultaMock);

		verify(consultaMock, times(1)).getNombre();
		verify(consultaMock, times(1)).cantidadConsultas();
		verify(consultaMock, times(1)).tieneMasDe100();
	}

	@Test
	public void juanchiAgrega3AfavoritasQueHabiaConsultado() {

		when(consultaMock.getRecetasConsultadas()).thenReturn(recetasMock);
		when(consultaMock.getUsuario()).thenReturn(juanchi);

		monitorRecetasFavoritas.procesarConsulta(consultaMock);

		verify(juanchi, times(1)).agregarAFavorita(receta1Mock);
		verify(juanchi, times(1)).agregarAFavorita(receta2Mock);
		verify(juanchi, times(1)).agregarAFavorita(receta3Mock);

	}
}
