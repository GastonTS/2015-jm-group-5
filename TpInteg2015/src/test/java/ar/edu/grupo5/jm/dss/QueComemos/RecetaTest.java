package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;

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

		recetaConMuchasCalorias = new Receta("Muy calorica", Arrays.asList("Algo super calorico"), new ArrayList<Condimentacion>(),
				new ArrayList<Receta>(), 99999, Dificultad.ALTA);
		recetaSinIngredientes = new Receta("Sin Ingredientes", new ArrayList<String>(), new ArrayList<Condimentacion>(),
				new ArrayList<Receta>(), 1000, Dificultad.BAJA);
		pure = new Receta("Pure", Arrays.asList("papas 2kg", "manteca 200gr"), Arrays.asList(sal, pimienta, nuezMoscada),
				new ArrayList<Receta>(), 400, Dificultad.MEDIA);
		ensalada = new Receta("Ensalada", Arrays.asList("Lechuga 2kg", "Cebolla 1.5kg", "Tomate 200gr"), Arrays.asList(sal, aceite),
				new ArrayList<Receta>(), 40, Dificultad.BAJA);
		polloConPureOEnsalada = new Receta("Pollo Con Pure o Ensalada", Arrays.asList("pollo mediano"), Arrays.asList(sal,
				condimentoParaPollo), new ArrayList<Receta>(), 3000, Dificultad.ALTA);

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
