package ar.edu.grupo5.jm.dss.QueComemos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.*;


public class MonitoreoConsultasTest {

	Consulta consultaMock = mock(Consulta.class);
	LogearConsultasMasDe100 monitorMayor100 = new LogearConsultasMasDe100();
	
	
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
	
}
