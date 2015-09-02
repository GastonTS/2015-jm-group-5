package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.time.LocalDate;

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
	
	Receta conCositas = new RecetaBuilder()
									.setNombre("Comida Con Cositas")
									.agregarIngrediente("Cositas")
									.agregarCondimentaciones(new Condimentacion("cositas", 23))
									.setCantCalorias(100)
									.setDificultad(Dificultad.ALTA)
									.construirReceta();
	
	Receta sinCositas = new RecetaBuilder()
									.setNombre("Comida Sin Cositas")
									.agregarIngrediente("No Cositas")
									.agregarCondimentaciones(new Condimentacion("no cosa", 2))
									.setCantCalorias(100)
									.setDificultad(Dificultad.ALTA)
									.construirReceta();
	
	@Before
	public void setUp() {
		GestorMorphia.instancia.getDatastore().save(conCositas);
		GestorMorphia.instancia.getDatastore().save(sinCositas);
		
		gaston.agregarAFavorita(conCositas);
		GestorMorphia.instancia.actualizarUsuario(gaston);
	}

	@Test
	public void seGuardanCorrectamenteLasRecetas() {
		assertEquals(conCositas, GestorMorphia.instancia.getDatastore().get(conCositas));
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
