package ar.edu.grupo5.jm.dss.QueComemos.RecetaTest;

import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaNoValidaException;

public class RecetaBuilderTest {

	@Test
	public void recetaValida() {
		new RecetaBuilder()
		.setNombre("Valida")
		.agregarIngrediente(new Ingrediente("Algo super normal"))
		.setCantCalorias(23)
		.setDificultad(Dificultad.ALTA)
		.construirReceta();
	}
	
	@Test(expected = RecetaNoValidaException.class)
	public void recetaNoValidaPorNoTenerIngredientes() {
		new RecetaBuilder()
		.setNombre("Sin Ingredientes")
		.setCantCalorias(1000)
		.setDificultad(Dificultad.BAJA)
		.construirReceta();
	}
	
	@Test(expected = RecetaNoValidaException.class)
	public void recetaNoValidaPorTenerDemasiadasCalorias() {
		new RecetaBuilder()
		.setNombre("Muy calorica")
		.agregarIngrediente(new Ingrediente("Algo super calorico"))
		.setCantCalorias(99999)
		.setDificultad(Dificultad.ALTA)
		.construirReceta();
	}
}
