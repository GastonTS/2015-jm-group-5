package ar.edu.grupo5.jm.dss.QueComemos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.DecoratorFilter.IFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico.*;

public class MonitoreoConsultasTest {

	private Consulta consultaMock = mock(Consulta.class);
	private Collection<Receta> recetasMock = new ArrayList<Receta>();
	private Receta receta1Mock = mock(Receta.class);
	private Receta receta2Mock = mock(Receta.class);
	private Receta receta3Mock = mock(Receta.class);
	private Usuario juanchi = mock(Usuario.class);
	
	private Usuario leanMock = mock(Usuario.class);
	private Usuario gusMock = mock(Usuario.class);
	private IFiltro unFiltroMock = mock(IFiltro.class);
	Collection<Usuario> usuariosConOpcionMandarMail;
	MailSender mailSenderMock = mock(MailSender.class);
	
	LogearConsultasMasDe100 monitorMayor100 = new LogearConsultasMasDe100();
	AgregarRecetasAFavoritas monitorRecetasFavoritas = new AgregarRecetasAFavoritas();
	EnviarConsultaPorMail monitorEnviarMail;

	@Before
	public void setUp() {
		recetasMock.add(receta1Mock);
		recetasMock.add(receta2Mock);
		recetasMock.add(receta3Mock);
		
		usuariosConOpcionMandarMail = Arrays.asList(gusMock, leanMock);
		monitorEnviarMail = new EnviarConsultaPorMail(usuariosConOpcionMandarMail, mailSenderMock);
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
	
	@Test
	public void enviaMailsPorConsultasDeUsuariosAsignados(){
		when(consultaMock.cantidadConsultas()).thenReturn(3);
		when(consultaMock.getUsuario()).thenReturn(leanMock);
		when(consultaMock.getUsuario().getNombre()).thenReturn("lean");
		when(consultaMock.getFiltro()).thenReturn(unFiltroMock);
		
		monitorEnviarMail.procesarConsulta(consultaMock);
		
		verify(mailSenderMock, times(1)).enviarMail("lean", unFiltroMock, 3);
	}
	
	@Test
	public void noEnviaMailsPorConsultasDeUsuariosNoAsignados(){
		when(consultaMock.cantidadConsultas()).thenReturn(3);
		when(consultaMock.getUsuario()).thenReturn(juanchi);
		when(consultaMock.getUsuario().getNombre()).thenReturn("juan");
		when(consultaMock.getFiltro()).thenReturn(unFiltroMock);
		
		monitorEnviarMail.procesarConsulta(consultaMock);
		
		verify(mailSenderMock, times(0)).enviarMail("juan", unFiltroMock, 3);
	}
}
