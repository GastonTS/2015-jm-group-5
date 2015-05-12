package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class DatosPersonalesTest {

	private DatosPersonales datosPersonalesValidos;
	private DatosPersonales nombreCorto;
	private DatosPersonales sinSexo;
	private DatosPersonales nacidoHoy;

	@Before
	public void setUp() {
		datosPersonalesValidos = new DatosPersonales("Pepito", "Masculino",
				LocalDate.parse("2000-01-01"));
		nombreCorto = new DatosPersonales("PP", "Masculino",
				LocalDate.parse("2000-01-01"));
		sinSexo = new DatosPersonales("Pepito", "",
				LocalDate.parse("2000-01-01"));
		nacidoHoy = new DatosPersonales("Pepito", "Masculino", LocalDate.now());

	}

	@Test
	public void sonValidos() {
		assertTrue(datosPersonalesValidos.sonValidos());
	}

	@Test
	public void esNombreCorto() {
		assertTrue(nombreCorto.tieneNombreCorto());
	}

	@Test
	public void noIndicaSexo() {
		assertFalse(sinSexo.indicaSexo());
	}

	@Test
	public void noTieneFechaDeNacimientoAnteriorAHoy() {
		assertFalse(nacidoHoy.fechaDeNacimientoAnteriorAHoy());
	}

}
