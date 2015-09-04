package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Celiaco;

public class MorphiaTest {

	Usuario gaston = new UsuarioBuilder()
									.setDatosPersonales(new DatosPersonales("gaston", Sexo.MASCULINO, LocalDate.parse("1993-10-15")))
									.setComplexion(new Complexion(70, 1.65))
									.agregarCondicionesDeSalud(new Celiaco())
									.setRutina(Rutina.MEDIANA)
									.construirUsuario();

	Receta sinCositas = new RecetaBuilder()
									.setNombre("Comida Sin Cositas")
									.agregarIngrediente("No Cositas")
									.agregarCondimentaciones(new Condimentacion("no cosa", 2))
									.setCantCalorias(100)
									.setDificultad(Dificultad.ALTA)
									.construirReceta();
		
	Receta conCositas = new RecetaBuilder()
									.setNombre("Comida Con Cositas")
									.agregarIngrediente("Cositas")
									.agregarCondimentaciones(new Condimentacion("cositas", 23))
									.setCantCalorias(100)
									.setDificultad(Dificultad.ALTA)
									.construirReceta();
		
	@Before
	public void setUp() {
		GestorMorphia.instancia.actualizarReceta(conCositas);
		GestorMorphia.instancia.actualizarReceta(sinCositas);

		gaston.agregarAFavorita(conCositas);
		GestorMorphia.instancia.actualizarUsuario(gaston);
	}

	@Test
	public void seGuardaCorrectamenteLaReceta() {
		assertEquals(conCositas, GestorMorphia.instancia.getDatastore().get(conCositas));
	}

	@Test
	public void seGuardaCorrectamenteElNombreDeLaReceta() {
		assertEquals(conCositas.getNombre(), GestorMorphia.instancia.getDatastore().get(conCositas).getNombre());
	}

	@Test
	public void seGuardaCorrectamenteLosIngredientesDeLaReceta() {
		assertEquals(conCositas.getIngredientesTotales(), GestorMorphia.instancia.getDatastore().get(conCositas).getIngredientesTotales());
	}

	@Test
	public void seGuardaCorrectamenteLasCondimentacionesDeLaReceta() {
		assertEquals(conCositas.getCondimentacionesTotales(), GestorMorphia.instancia.getDatastore().get(conCositas)
				.getCondimentacionesTotales());
	}

	@Test
	public void seGuardaCorrectamenteLasSubRecetasDeLaRecetaSinSubrecetas() {
		assertEquals(conCositas.getSubRecetas(), GestorMorphia.instancia.getDatastore().get(conCositas).getSubRecetas());
	}

	@Test
	public void seGuardaCorrectamenteLasSubRecetasDeLaRecetaConSubrecetas() {
		conCositas.agregarSubRecetas(Arrays.asList(sinCositas));
		GestorMorphia.instancia.actualizarReceta(conCositas);

		assertEquals(conCositas.getSubRecetas(), GestorMorphia.instancia.getDatastore().get(conCositas).getSubRecetas());
	}

	@Test
	public void seGuardaCorrectamenteLasCaloriasDeLaReceta() {
		assertEquals(conCositas.getCantCaloriasTotales(), GestorMorphia.instancia.getDatastore().get(conCositas).getCantCaloriasTotales(), 0.01);
	}

	@Test
	public void seGuardaCorrectamenteLaDificultadDeLaReceta() {
		assertEquals(conCositas.getDificultad(), GestorMorphia.instancia.getDatastore().get(conCositas).getDificultad());
	}

	@Test
	public void seGuardaCorrectamenteSiLaRecetaEsPublicaDeLaReceta() {
		assertEquals(conCositas.esPublica(), GestorMorphia.instancia.getDatastore().get(conCositas).esPublica());
	}

	@Test
	public void seGuardaCorrectamenteElDueñoDeLaReceta() {
		conCositas.setDueño(gaston);
		GestorMorphia.instancia.actualizarReceta(conCositas);

		assertEquals(conCositas.getDueño().getId(), GestorMorphia.instancia.getDatastore().get(conCositas).getDueño().getId());
	}

	@Test
	public void seGuardanCorrectamenteUsuarios() {
		assertEquals(gaston.getId(), GestorMorphia.instancia.getDatastore().get(gaston).getId());
	}

	@Test
	public void unUsuarioConservaSusRecetasFavoritas() {
		assertEquals(gaston.getRecetasFavoritas(), GestorMorphia.instancia.getDatastore().get(gaston).getRecetasFavoritas());
	}

	@Test
	public void siSeAgregaUnaRecetaSeGuardaEnLaBD() {
		gaston.agregarAFavorita(sinCositas);
		GestorMorphia.instancia.actualizarUsuario(gaston);

		assertEquals(gaston.getRecetasFavoritas(), GestorMorphia.instancia.getDatastore().get(gaston).getRecetasFavoritas());
	}

	@Test
	public void siSeQuitaUnaRecetaSeQuitaEnLaBD() {
		gaston.quitarRecetaFavorita(conCositas);
		GestorMorphia.instancia.actualizarUsuario(gaston);

		assertEquals(gaston.getRecetasFavoritas(), GestorMorphia.instancia.getDatastore().get(gaston).getRecetasFavoritas());
	}

	@After
	public void borrarDB() {
		GestorMorphia.instancia.getDatastore().delete(conCositas);
		GestorMorphia.instancia.getDatastore().delete(sinCositas);
		GestorMorphia.instancia.getDatastore().delete(gaston);
	}
}
