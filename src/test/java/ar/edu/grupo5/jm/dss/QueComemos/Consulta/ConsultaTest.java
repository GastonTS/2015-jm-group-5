package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.NoExcedeCalorias;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.SinFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
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
	Ingrediente lechuga2kg = new Ingrediente("Lechuga 2kg");
	private Vegano vegano;

	private SinFiltro sinFiltro = new SinFiltro();
	private NoExcedeCalorias excesoDeCalorias = new NoExcedeCalorias(sinFiltro);

	@Before
	public void setUp() {
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
	}

	@Test
	public void retornaParametrosDeBusquedaSegunNombresDeSuFiltro() {
		Consulta gestorConsulta = new Consulta(consultorMock, excesoDeCalorias,
				zaffaGenio);

		assertEquals("\t-> No se excede en calorías.\n",
				gestorConsulta.parametrosDeBusquedaToString());

	}

}
