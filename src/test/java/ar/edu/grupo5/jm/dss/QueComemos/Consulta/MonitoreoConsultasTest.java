package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import jdk.nashorn.internal.ir.annotations.Ignore;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.NoEsInadecuadaParaUsuario;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.SinFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.AgregarRecetasConsultadasAFavoritas;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.BufferDeConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.EnviarConsultaPorMail;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.LogearConsultasMasDe100;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.MailSender;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico.ProcesoAsincronico;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class MonitoreoConsultasTest extends AbstractPersistenceTest implements
WithGlobalEntityManager {

	private SinFiltro sinFiltro;
	private NoEsInadecuadaParaUsuario segunCondicionesDelusuario;
	private Consulta consulta;
	private Collection<Receta> recetasMock = new ArrayList<Receta>();
	private Receta receta1Mock = mock(Receta.class);
	private Receta receta2Mock = mock(Receta.class);
	private Receta receta3Mock = mock(Receta.class);
	private Usuario juanchi = mock(Usuario.class);
	private Usuario usuario;
	private ConsultorRecetas consultor = Recetario.instancia;

	private Usuario leanMock = mock(Usuario.class);
	private Usuario gusMock = mock(Usuario.class);
	Collection<Usuario> usuariosConOpcionMandarMail;
	MailSender mailSenderMock = mock(MailSender.class);
	LogearConsultasMasDe100 procesoAsincronicoLog = new LogearConsultasMasDe100();
	AgregarRecetasConsultadasAFavoritas procesoAsincronicoFavs = new AgregarRecetasConsultadasAFavoritas();
	

	LogearConsultasMasDe100 monitorMayor100 = new LogearConsultasMasDe100();
	AgregarRecetasConsultadasAFavoritas monitorRecetasFavoritas = new AgregarRecetasConsultadasAFavoritas();
	EnviarConsultaPorMail monitorEnviarMail;

	@Before
	public void setUp() {

//		usuariosConOpcionMandarMail = Arrays.asList(gusMock, leanMock);
//		monitorEnviarMail = new EnviarConsultaPorMail(usuariosConOpcionMandarMail, mailSenderMock);
		
		usuario = new UsuarioBuilder().setNombre("usuario")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1993-10-15"))
				.setEstatura(1.68)
				.setPeso(65)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
		
		sinFiltro = new SinFiltro();
		segunCondicionesDelusuario = new NoEsInadecuadaParaUsuario(sinFiltro);
			
//		consulta = new Consulta(consultor,segunCondicionesDelusuario,usuario); //TODO

//		BufferDeConsultas.instancia.limpiarConsultas();
//		BufferDeConsultas.instancia.agregarConsulta(consulta);
	}

	@Ignore
	@Test
	public void bufferDeConsultasLlamaAsusProcesosAlPedirleProcesar() {
		//		BufferDeConsultas.instancia.agregarProceso(procesoAsincronicoLog);
//		BufferDeConsultas.instancia.agregarProceso(procesoAsincronicoFavs);
		//		BufferDeConsultas.instancia.procesarConsultas();

		//		verify(procesoAsincronicoLog, times(1)).procesarConsultas(Arrays.asList(consulta));
//		verify(procesoAsincronicoFavs, times(1)).procesarConsultas(Arrays.asList(consulta));
	}
/*
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
	}*/
}
