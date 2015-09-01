package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;

public class MorphiaTest {

	Receta conCositas;
	Receta sinCositas;

	@Before
	public void setUp() {

		conCositas = new RecetaBuilder()
							.setNombre("Comida Con Cositas")
							.agregarIngrediente("Cositas")
							.agregarCondimentaciones(new Condimentacion("cositas", 23))
							.setCantCalorias(100)
							.setDificultad(Dificultad.ALTA)
							.construirReceta();

		sinCositas = new RecetaBuilder()
							.setNombre("Comida Sin Cositas")
							.agregarIngrediente("No Cositas")
							.agregarCondimentaciones(new Condimentacion("no cosa", 2))
							.setCantCalorias(100)
							.setDificultad(Dificultad.ALTA)
							.construirReceta();
		
		GestorMorphia.instancia.getDatastore().save(conCositas);
		GestorMorphia.instancia.getDatastore().save(sinCositas);
	}

	@Test
	public void seGuardanCorrectamenteLasRecetas() {
		assertEquals(conCositas.getId(), GestorMorphia.instancia.getDatastore().get(conCositas).getId());
	}

	@After
	public void borrarDB() {
		GestorMorphia.instancia.getDatastore().delete(conCositas);
		GestorMorphia.instancia.getDatastore().delete(sinCositas);
	}
}
