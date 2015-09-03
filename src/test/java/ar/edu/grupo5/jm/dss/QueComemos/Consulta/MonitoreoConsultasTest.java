package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.*;

public class MonitoreoConsultasTest {

	private Consulta consultaMock = mock(Consulta.class);
	private Collection<Receta> recetasMock = new ArrayList<Receta>();
	private Receta receta1Mock = mock(Receta.class);
	private Receta receta2Mock = mock(Receta.class);
	private Receta receta3Mock = mock(Receta.class);
	private Usuario juanchi = mock(Usuario.class);

	private Usuario leanMock = mock(Usuario.class);
	private Usuario gusMock = mock(Usuario.class);
	Collection<Usuario> usuariosConOpcionMandarMail;
	MailSender mailSenderMock = mock(MailSender.class);
	ProcesoAsincronico unProcesoAsincronico = mock(ProcesoAsincronico.class);
	ProcesoAsincronico otroProcesoAsincronico = mock(ProcesoAsincronico.class);

	LogearConsultasMasDe100 monitorMayor100 = new LogearConsultasMasDe100();
	AgregarRecetasConsultadasAFavoritas monitorRecetasFavoritas = new AgregarRecetasConsultadasAFavoritas();
	EnviarConsultaPorMail monitorEnviarMail;

	@Before
	public void setUp() {
		recetasMock.add(receta1Mock);
		recetasMock.add(receta2Mock);
		recetasMock.add(receta3Mock);

		usuariosConOpcionMandarMail = Arrays.asList(gusMock, leanMock);
		monitorEnviarMail = new EnviarConsultaPorMail(usuariosConOpcionMandarMail, mailSenderMock);

		BufferDeConsultas.instancia.limpiarConsultas();
		BufferDeConsultas.instancia.agregarProceso(unProcesoAsincronico);
		BufferDeConsultas.instancia.agregarProceso(otroProcesoAsincronico);
		BufferDeConsultas.instancia.agregarConsulta(consultaMock);
	}

	@Test
	public void bufferDeConsultasLlamaAsusProcesosAlPedirleProcesar() {

		BufferDeConsultas.instancia.procesarConsultas();

		verify(unProcesoAsincronico, times(1)).procesarConsultas(Arrays.asList(consultaMock));
		verify(otroProcesoAsincronico, times(1)).procesarConsultas(Arrays.asList(consultaMock));
	}

	@Test
	public void leanConsultomuchasRecetas() {

		when(consultaMock.getNombre()).thenReturn("leandro");
		when(consultaMock.cantidadConsultadas()).thenReturn(125);

		monitorMayor100.procesarConsulta(consultaMock);

		verify(consultaMock, times(1)).getNombre();
		verify(consultaMock, times(2)).cantidadConsultadas();
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
	public void enviaMailsPorConsultasDeUsuariosAsignados() {
		String destinatario = "gusMock@foo.com";
		String titulo = "Has realizado una nueva consulta en nuestro sistema!";
		String nombre = "Gustavo";
		String cantidad = "6";
		String parametros = "parametros aquí\n";
		
		when(consultaMock.getUsuario()).thenReturn(gusMock);
		when(consultaMock.cantidadConsultadas()).thenReturn(6);
		when(consultaMock.getNombre()).thenReturn(nombre);
		when(consultaMock.getDestinatario()).thenReturn(destinatario);
		when(consultaMock.parametrosDeBusquedaToString()).thenReturn(parametros);
		
		monitorEnviarMail.procesarConsulta(consultaMock);

		verify(mailSenderMock, times(1)).send(eq(destinatario), eq(titulo), contains(nombre));
		verify(mailSenderMock, times(1)).send(eq(destinatario), eq(titulo), contains(cantidad));
		verify(mailSenderMock, times(1)).send(eq(destinatario), eq(titulo), contains(parametros));
	}

	@Test
	public void noEnviaMailsPorConsultasDeUsuariosNoAsignados() {
		when(consultaMock.getUsuario()).thenReturn(juanchi);
		monitorEnviarMail.procesarConsulta(consultaMock);

		verify(mailSenderMock, times(0)).send(anyString(), anyString(), anyString());
	}
}
