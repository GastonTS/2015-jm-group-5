package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class RecetasConSubRecetaTest {

	private Condimento sal = new Condimento("sal fina",100); 
	private Condimento pimienta = new Condimento("pimienta molida",50);
	private Condimento nuezMoscada = new Condimento("nuez moscada",20);
	private Condimento condimentoParaPollo = new Condimento("condimento P/pollo",40);
	private Condimento aceite = new Condimento("Aceite de Maiz",2);
	
	private Receta polloConPure;
	private Receta pure;
	private Receta ensalada;

	
	private Usuario gustavo;
	
	private Collection<Receta> recetasPublicas;
	private Collection<Receta> recetasGustavo;
	
    @Before
	public void setUp() {
    	
    	pure = new Receta("Pure", Arrays.asList("papas 2kg","manteca 200gr"), Arrays.asList(sal,pimienta,nuezMoscada), new ArrayList<Receta>(), "-Hervir las Papas -Pisar el Puré -Fin", "Todo el año", "Fácil", 400);
    	ensalada = new Receta("Ensalada", Arrays.asList("Lechuga 2kg","Cebolla 1.5kg","Tomate 200gr"), Arrays.asList(sal,aceite), new ArrayList<Receta>(), "-Cortar los Ingredientes -Sasonar -Fin", "Todo el año", "Fácil", 400000);
    	
    	polloConPure = new Receta("Pollo c/pure", Arrays.asList("pollo mediano"), Arrays.asList(sal,condimentoParaPollo), new ArrayList<Receta>(), "-Cortar el Pollo -Cocinarlo -Fin", "Todo el año", "Fácil", 3000);

    	
    	recetasPublicas = Arrays.asList(pure); 
    	recetasGustavo = new ArrayList<Receta>();
    	
    	Usuario.recetasPublicas(recetasPublicas);
    	
    	gustavo = new Usuario(73, 1.83, "Gustavo", null, null, null, recetasGustavo, null, "Mediano");
	}
    
	@Test
	public void gustavoCreaRecetaDelPolloConPapasYEnsalada() {
		gustavo.crearRecetaConSubRecetas(polloConPure,Arrays.asList(pure,ensalada));
		assertTrue(gustavo.esRecetaPropia(polloConPure));
		assertTrue(polloConPure.subrecetasIncluye(pure));
		assertTrue(polloConPure.subrecetasIncluye(ensalada));
	}
	
}
