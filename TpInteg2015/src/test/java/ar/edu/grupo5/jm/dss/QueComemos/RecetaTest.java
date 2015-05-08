package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class RecetaTest {

	private Receta recetaSinIngredientes;
	private Receta recetaConMuchasCalorias;
	private Receta polloConPureOEnsalada;
	private Receta pure;
	private Receta ensalada;
	
	private Condimentacion sal = new Condimentacion("sal fina",100); 
	private Condimentacion pimienta = new Condimentacion("pimienta molida",50);
	private Condimentacion nuezMoscada = new Condimentacion("nuez moscada",20);
	private Condimentacion condimentoParaPollo = new Condimentacion("condimento P/pollo",40);
	private Condimentacion aceite = new Condimentacion("Aceite de Maiz",2);
	
	Collection<String> ingredientesVacia = new ArrayList<String>();
	
	private Usuario gustavo;
	
	private Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasGustavo;
	
	@Before
	public void setUp() {
		
		recetaConMuchasCalorias = new Receta(null, Arrays.asList("Algo super calorico"), null, null, 99999);
		recetaSinIngredientes = new Receta(null, ingredientesVacia, null, null, 1000);
		pure = new Receta("Pure", Arrays.asList("papas 2kg","manteca 200gr"), Arrays.asList(sal,pimienta,nuezMoscada), new ArrayList<Receta>(), 400);
    	ensalada = new Receta("Ensalada", Arrays.asList("Lechuga 2kg","Cebolla 1.5kg","Tomate 200gr"), Arrays.asList(sal,aceite), new ArrayList<Receta>(), 40);
    	polloConPureOEnsalada = new Receta("Pollo c/pure", Arrays.asList("pollo mediano"), Arrays.asList(sal,condimentoParaPollo), new ArrayList<Receta>(), 3000);

    	recetasPublicas = Arrays.asList(pure); 
    	recetasGustavo = new ArrayList<Receta>();
    	Usuario.recetasPublicas(recetasPublicas);
    	
    	gustavo = new Usuario("Gustavo", null, 73, 1.83, null, recetasGustavo, null, "Mediano");
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
	public void gustavoCreaRecetaDelPolloConPapasYEnsalada() {
		gustavo.crearRecetaConSubRecetas(polloConPureOEnsalada,Arrays.asList(pure,ensalada));
		assertTrue(gustavo.esRecetaPropia(polloConPureOEnsalada));
		assertTrue(polloConPureOEnsalada.subrecetasIncluye(pure));
		assertTrue(polloConPureOEnsalada.subrecetasIncluye(ensalada));
	}
	
}
