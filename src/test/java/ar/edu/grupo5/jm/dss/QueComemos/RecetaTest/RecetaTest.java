package ar.edu.grupo5.jm.dss.QueComemos.RecetaTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class RecetaTest {

	private Receta polloConPureOEnsalada;
	private Receta pure;
	private Receta ensalada;
	private Receta recetaDificil;
	
	private Usuario arthas = mock(Usuario.class);
	private Usuario muradin = mock(Usuario.class);

	private Condimentacion sal = new Condimentacion("sal fina", 100);
	private Condimentacion pocaSal = new Condimentacion("sal fina", 50);
	private Condimentacion muchaSal = new Condimentacion("sal fina", 200);
	private Condimentacion pimienta = new Condimentacion("pimienta molida", 50);
	private Condimentacion nuezMoscada = new Condimentacion("nuez moscada", 20);
	private Condimentacion condimentoParaPollo = new Condimentacion("condimento P/pollo", 40);
	private Condimentacion aceite = new Condimentacion("Aceite de Maiz", 2);

	Ingrediente lechuga2kg = new Ingrediente("Lechuga 2kg");
	
	@Before
	public void setUp() {
		
		recetaDificil = new RecetaBuilder()
			.setCantCalorias(20)
			.agregarIngrediente(new Ingrediente("Ingrediente dificil"))
			.setDificultad(Dificultad.ALTA)
			.construirReceta();
			
		pure = new RecetaBuilder()
				.setNombre("Pure")
				.agregarIngrediente(new Ingrediente("papas 2kg"))
				.agregarIngrediente(new Ingrediente("manteca 200gr"))
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(pimienta)
				.agregarCondimentaciones(nuezMoscada)
				.setCantCalorias(400)
				.setDificultad(Dificultad.MEDIA)
				.construirReceta();
		
		ensalada = new RecetaBuilder()
				.setNombre("Ensalada")
				.agregarIngrediente(lechuga2kg)
				.agregarIngrediente(new Ingrediente("Cebolla 1.5kg"))
				.agregarIngrediente(new Ingrediente("Tomate 200gr"))
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(aceite)
				.setCantCalorias(40)
				.setDificultad(Dificultad.BAJA)
				.construirReceta();
		
		polloConPureOEnsalada = new RecetaBuilder()
				.setNombre("Pollo Con Pure o Ensalada")
				.agregarIngrediente(new Ingrediente("pollo mediano"))
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(condimentoParaPollo)
				.setCantCalorias(3000)
				.setDificultad(Dificultad.ALTA)
				.construirReceta();
		
	}

	@Test
	public void agregarSubrecetas() {
		polloConPureOEnsalada.agregarSubRecetas(Arrays.asList(pure, ensalada));

		assertTrue(polloConPureOEnsalada.getSubRecetas().contains(pure));
		assertTrue(polloConPureOEnsalada.getSubRecetas().contains(ensalada));
	}

	@Test
	public void esDificilUnaReceta() {
		assertTrue(recetaDificil.esDificil());
		assertFalse(pure.esDificil());
	}
	
	@Test
	public void unaRecetaSinDueñoEsPublica() {
		assertTrue(pure.esPublica());
	}
	
	@Test
	public void unaRecetaSeLeSeteaDueñoYSeCompruebaElDueño() {
		assertTrue(pure.esPublica());
		pure.setDueño(arthas);
		assertTrue(pure.esElDueño(arthas));
	}
	
	@Test
	public void unaRecetaSeLeSeteaDueñoYSeCompruebaQueNoEsOtroDueño() {
		assertTrue(pure.esPublica());
		pure.setDueño(arthas);
		assertFalse(pure.esElDueño(muradin));
	}
	
	@Test
	public void unaRecetaNoSeLeSeteaDueñoYSeCompruebaElDueño() {
		assertTrue(pure.esPublica());
		assertFalse(pure.esElDueño(arthas));
	}
	
	@Test
	public void unaRecetaSeLeSeteaDueñoYSeCompruebaQueNoEsMasPublica() {
		assertTrue(pure.esPublica());
		pure.setDueño(arthas);
		assertFalse(pure.esPublica());
	}
	
	@Test
	public void unaRecetaTieneMasDeUnaCondimentacion() {
		assertTrue(pure.tenesMasDe(pocaSal));
	}
	
	@Test
	public void unaRecetaNoTieneMasDeUnaCondimentacion() {
		assertFalse(pure.tenesMasDe(muchaSal));
		assertFalse(recetaDificil.tenesMasDe(pocaSal));
	}
	
	
	@Test
	public void unaRecetaNoTieneNingunoDeEstosCondimentos() {
		ArrayList<String> condimentosProhibidos = new ArrayList<String>();
		condimentosProhibidos.add("barbacoa");
		condimentosProhibidos.add("cheddar");
		condimentosProhibidos.add("wasabi");
		assertFalse(pure.tenesAlgoDe(condimentosProhibidos));

	}
	
	@Test
	public void unaRecetaTieneAlgunosDeEstosCondimentos() {
		ArrayList<String> condimentosProhibidos = new ArrayList<String>();
		condimentosProhibidos.add("sal fina");
		condimentosProhibidos.add("cheddar");
		condimentosProhibidos.add("wasabi");
		assertTrue(pure.tenesAlgoDe(condimentosProhibidos));

	}
	
	@Test
	public void unaRecetaTieneTodosEstosCondimentos() {
		ArrayList<String> condimentosProhibidos = new ArrayList<String>();
		condimentosProhibidos.add("sal fina");
		condimentosProhibidos.add("pimienta molida");
		condimentosProhibidos.add("nuez moscada");
		assertTrue(pure.tenesAlgoDe(condimentosProhibidos));

	}

	@Test
	public void unaRecetatieneAlgunIngredienteDeEstos() {
		ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		ingredientes.add(lechuga2kg);
		ingredientes.add(new Ingrediente("carne Humana 3kg"));
		ingredientes.add(new Ingrediente("polimorfismo 2 piscas"));
		ingredientes.add(new Ingrediente("pattern matching 5 cucharadas soperas"));
		
		assertTrue(ensalada.tenesAlgunIngredienteDeEstos(ingredientes));
	}
	

}
