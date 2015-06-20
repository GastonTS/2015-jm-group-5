package ar.edu.grupo5.jm.dss.QueComemos;

import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;

public class RecetaBuilderTest {

	
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
		.agregarIngrediente("Algo super calorico")
		.setCantCalorias(99999)
		.setDificultad(Dificultad.ALTA)
		.construirReceta();
	}
}
