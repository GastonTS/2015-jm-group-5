package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;

public class RecetaTest {

	private Receta recetaSinIngredientes;
	private Receta recetaConMuchasCalorias;
	private Receta polloConPureOEnsalada;
	private Receta pure;
	private Receta ensalada;

	private Condimentacion sal = new Condimentacion("sal fina", 100);
	private Condimentacion pimienta = new Condimentacion("pimienta molida", 50);
	private Condimentacion nuezMoscada = new Condimentacion("nuez moscada", 20);
	private Condimentacion condimentoParaPollo = new Condimentacion("condimento P/pollo", 40);
	private Condimentacion aceite = new Condimentacion("Aceite de Maiz", 2);

	@Before
	public void setUp() {

		recetaConMuchasCalorias = new RecetaBuilder()
				.setNombre("Muy calorica")
				.agregarIngrediente("Algo super calorico")
				.setCantCalorias(99999)
				.setDificultad(Dificultad.ALTA)
				.construirReceta();
				
		recetaSinIngredientes = new RecetaBuilder()
				.setNombre("Sin Ingredientes")
				.setCantCalorias(1000)
				.setDificultad(Dificultad.BAJA)
				.construirReceta();
		
		pure = new RecetaBuilder()
				.setNombre("Pure")
				.agregarIngrediente("papas 2kg")
				.agregarIngrediente("manteca 200gr")
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(pimienta)
				.agregarCondimentaciones(nuezMoscada)
				.setCantCalorias(400)
				.setDificultad(Dificultad.MEDIA)
				.construirReceta();
		
		ensalada = new RecetaBuilder()
				.setNombre("Ensalada")
				.agregarIngrediente("Lechuga 2kg")
				.agregarIngrediente("Cebolla 1.5kg")
				.agregarIngrediente("Tomate 200gr")
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(aceite)
				.setCantCalorias(40)
				.setDificultad(Dificultad.BAJA)
				.construirReceta();
		
		polloConPureOEnsalada = new RecetaBuilder()
				.setNombre("Pollo Con Pure o Ensalada")
				.agregarIngrediente("pollo mediano")
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(condimentoParaPollo)
				.setCantCalorias(3000)
				.setDificultad(Dificultad.ALTA)
				.construirReceta();
		
	}

	@Test
	public void recetaValida() {
		assertTrue(pure.esValida());
		assertTrue(ensalada.esValida());
		assertTrue(polloConPureOEnsalada.esValida());
	}

	@Test
	public void recetasNoValida() {
		assertFalse(recetaSinIngredientes.esValida());
		assertFalse(recetaConMuchasCalorias.esValida());
	}

	@Test
	public void agregarSubrecetas() {
		polloConPureOEnsalada.agregarSubRecetas(Arrays.asList(pure, ensalada));

		assertTrue(polloConPureOEnsalada.getSubRecetas().contains(pure));
		assertTrue(polloConPureOEnsalada.getSubRecetas().contains(ensalada));
	}

	@Test
	public void esDificilUnaReceta() {

		assertTrue(recetaConMuchasCalorias.esDificil());
		assertFalse(pure.esDificil());
		assertFalse(recetaSinIngredientes.esDificil());

	}

}
