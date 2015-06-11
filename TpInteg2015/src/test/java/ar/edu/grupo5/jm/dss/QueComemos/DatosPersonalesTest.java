package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonalesNoValidosException;

public class DatosPersonalesTest {

	private DatosPersonales datosPersonalesValidos;
	private DatosPersonales sinSexo;

	@Before
	public void setUp() {
		datosPersonalesValidos = new DatosPersonales("Pepito", "Masculino", LocalDate.parse("2000-01-01"));
		sinSexo = new DatosPersonales("Pepito", "", LocalDate.parse("2000-01-01"));
	}

	@Test
	public void sonValidos() {
		assertTrue(datosPersonalesValidos.sonValidos());
	}

	@Test(expected = DatosPersonalesNoValidosException.class)
	public void esNombreCorto() {
		new DatosPersonales("PP", "Masculino", LocalDate.parse("2000-01-01"));
	}

	@Test
	public void noIndicaSexo() {
		assertFalse(sinSexo.indicaSexo());
	}

	@Test(expected = DatosPersonalesNoValidosException.class)
	public void noTieneFechaDeNacimientoAnteriorAHoy() {
		new DatosPersonales("Pepito", "Masculino", LocalDate.now());
	}

}
