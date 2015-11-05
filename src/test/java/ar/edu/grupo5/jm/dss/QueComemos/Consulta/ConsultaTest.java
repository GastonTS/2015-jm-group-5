package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.NoExcedeCalorias;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.SinFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.RepoObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Vegano;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class ConsultaTest {

	ConsultorRecetas consultorMock = mock(ConsultorRecetas.class);
	// cosas usuarios
	private Usuario zaffaGenio;
	private Complexion complexionZaffa;
	private DatosPersonales datosPersonalesZaffa;
	private Ingrediente pomelo = new Ingrediente("pomelo");
	private Ingrediente fruta = new Ingrediente("fruta");
	// cosas recetas
	private Receta polloConPureOEnsalada;
	private Receta pure;
	private Receta ensalada;
	private Condimentacion sal = new Condimentacion("sal fina", 100);
	private Condimentacion pimienta = new Condimentacion("pimienta molida", 50);
	private Condimentacion nuezMoscada = new Condimentacion("nuez moscada", 20);
	private Condimentacion condimentoParaPollo = new Condimentacion(
			"condimento P/pollo", 40);
	private Condimentacion aceite = new Condimentacion("Aceite de Maiz", 2);
	Ingrediente lechuga2kg = new Ingrediente("Lechuga 2kg");
	private Vegano vegano;

	private ObservadorConsultas ObserverConsultaMock = mock(ObservadorConsultas.class);
	private ObservadorConsultas ObserverConsultaMock2 = mock(ObservadorConsultas.class);

	private SinFiltro sinFiltro = new SinFiltro();
	private NoExcedeCalorias excesoDeCalorias = new NoExcedeCalorias(sinFiltro);

	@Before
	public void setUp() {
		RepoObservadorConsultas.instancia.agregarObservador(ObserverConsultaMock);
		RepoObservadorConsultas.instancia.agregarObservador(ObserverConsultaMock2);
		// cosas usuarios
		vegano = new Vegano();
		complexionZaffa = new Complexion(73, 1.83);
		datosPersonalesZaffa = new DatosPersonales("Pepito", Sexo.MASCULINO,
				LocalDate.parse("2000-01-01"));
		zaffaGenio = new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesZaffa)
				.setComplexion(complexionZaffa)
				.agregarPreferenciaAlimenticia(fruta)
				.agregarDisgustoAlimenticio(pomelo)
				.agregarCondicionesDeSalud(vegano).setRutina(Rutina.ALTA)
				.construirUsuario();
		// cosas recetas
		pure = new RecetaBuilder().setNombre("Pure")
				.agregarIngrediente(new Ingrediente("papas 2kg"))
				.agregarIngrediente(new Ingrediente("manteca 200gr"))
				.agregarCondimentaciones(sal).agregarCondimentaciones(pimienta)
				.agregarCondimentaciones(nuezMoscada).setCantCalorias(400)
				.setDificultad(Dificultad.MEDIA).construirReceta();

		ensalada = new RecetaBuilder().setNombre("Ensalada")
				.agregarIngrediente(lechuga2kg)
				.agregarIngrediente(new Ingrediente("Cebolla 1.5kg"))
				.agregarIngrediente(new Ingrediente("Tomate 200gr"))
				.agregarCondimentaciones(sal).agregarCondimentaciones(aceite)
				.setCantCalorias(40).setDificultad(Dificultad.BAJA)
				.construirReceta();

		polloConPureOEnsalada = new RecetaBuilder()
				.setNombre("Pollo Con Pure o Ensalada")
				.agregarIngrediente(new Ingrediente("pollo mediano"))
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(condimentoParaPollo)
				.setCantCalorias(3000).setDificultad(Dificultad.ALTA)
				.construirReceta();
	}

	@After
	public void after() {
		RepoObservadorConsultas.instancia.quitarObservador(ObserverConsultaMock);
		RepoObservadorConsultas.instancia.quitarObservador(ObserverConsultaMock2);
	}

	@Test
	@Ignore
	public void consultaRecetas() {
		Collection<Receta> recetasAConsultar = Arrays.asList(pure,
				polloConPureOEnsalada, ensalada);
		Collection<Receta> resultadoConsulta = Arrays.asList(pure, ensalada);
		when(consultorMock.getRecetas(zaffaGenio))
				.thenReturn(recetasAConsultar);

		Consulta gestorConsulta = new Consulta(consultorMock, excesoDeCalorias,
				zaffaGenio);
		Collection<Receta> resultadoConsultaPosta = gestorConsulta
				.getRecetasConsultadas();
		
		assertEquals(resultadoConsulta, resultadoConsultaPosta);

		verify(consultorMock, times(1)).getRecetas(zaffaGenio);
		verify(ObserverConsultaMock, times(1)).notificarConsulta(gestorConsulta);
		verify(ObserverConsultaMock2, times(1)).notificarConsulta(gestorConsulta);
	}

	@Test
	@Ignore
	public void retornaParametrosDeBusquedaSegunNombresDeSuFiltro() {
		Consulta gestorConsulta = new Consulta(consultorMock, excesoDeCalorias,
				zaffaGenio);

		assertEquals("\t-> No se excede en calorías.\n",
				gestorConsulta.parametrosDeBusquedaToString());

	}

}
