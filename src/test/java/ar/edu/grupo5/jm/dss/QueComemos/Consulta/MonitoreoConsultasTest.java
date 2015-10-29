package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
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
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Vegano;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class MonitoreoConsultasTest extends AbstractPersistenceTest implements
		WithGlobalEntityManager {

	private SinFiltro sinFiltro;
	private NoEsInadecuadaParaUsuario segunCondicionesDelusuario;
	private Consulta consulta;

	private Consulta consultaMock = mock(Consulta.class);

	private Usuario juanchi = mock(Usuario.class);
	private Usuario zaffa;
	private Vegano vegano;
	private Complexion complexionZaffa;
	private DatosPersonales datosPersonalesZaffa;
	private Ingrediente pomelo = new Ingrediente("pomelo");
	private Ingrediente fruta = new Ingrediente("fruta");

	private ConsultorRecetas consultor = Recetario.instancia;

	private Usuario leanMock = mock(Usuario.class);
	private Usuario gusMock = mock(Usuario.class);
	Collection<Usuario> usuariosConOpcionMandarMail;
	MailSender mailSenderMock = mock(MailSender.class);
//	LogearConsultasMasDe100 procesoAsincronicoLog = mock(LogearConsultasMasDe100.class);
//	AgregarRecetasConsultadasAFavoritas procesoAsincronicoFavs = mock(AgregarRecetasConsultadasAFavoritas.class);

	LogearConsultasMasDe100 monitorMayor100 = new LogearConsultasMasDe100();
	AgregarRecetasConsultadasAFavoritas monitorRecetasFavoritas = new AgregarRecetasConsultadasAFavoritas();
	EnviarConsultaPorMail monitorEnviarMail;

	@Before
	public void setUp() {

		usuariosConOpcionMandarMail = Arrays.asList(gusMock, leanMock);
		monitorEnviarMail = new EnviarConsultaPorMail(
				usuariosConOpcionMandarMail, mailSenderMock);

		vegano = new Vegano();
		complexionZaffa = new Complexion(73, 1.83);
		datosPersonalesZaffa = new DatosPersonales("Pepito", Sexo.MASCULINO,
				LocalDate.parse("2000-01-01"));
		zaffa = new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesZaffa)
				.setComplexion(complexionZaffa)
				.agregarPreferenciaAlimenticia(fruta)
				.agregarDisgustoAlimenticio(pomelo)
				.agregarCondicionesDeSalud(vegano).setRutina(Rutina.ALTA)
				.construirUsuario();

		sinFiltro = new SinFiltro();
		segunCondicionesDelusuario = new NoEsInadecuadaParaUsuario(sinFiltro);
		entityManager().persist(zaffa);
		consulta = new Consulta(consultor, segunCondicionesDelusuario, zaffa);

		
		BufferDeConsultas.instancia.agregarConsulta(consulta);
	}
	@After
	public void after() {
		BufferDeConsultas.instancia.limpiarConsultas();
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
		when(consultaMock.parametrosDeBusquedaToString())
				.thenReturn(parametros);

		monitorEnviarMail.procesarConsulta(consultaMock);

		verify(mailSenderMock, times(1)).send(eq(destinatario), eq(titulo),
				contains(nombre));
		verify(mailSenderMock, times(1)).send(eq(destinatario), eq(titulo),
				contains(cantidad));
		verify(mailSenderMock, times(1)).send(eq(destinatario), eq(titulo),
				contains(parametros));
	}

	@Test
	public void noEnviaMailsPorConsultasDeUsuariosNoAsignados() {
		when(consultaMock.getUsuario()).thenReturn(juanchi);
		monitorEnviarMail.procesarConsulta(consultaMock);

		verify(mailSenderMock, times(0)).send(anyString(), anyString(),
				anyString());
	}

}
