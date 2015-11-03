package ar.edu.grupo5.jm.dss.QueComemos.RecetaTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeAccederARecetaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeEliminarRecetaExeption;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;

public class RecetarioTest extends AbstractPersistenceTest implements
		WithGlobalEntityManager {

	private Usuario gaston;
	private Usuario juanchi;
	private Usuario poe;
	private Usuario gustavo = mock(Usuario.class);

	private Collection<Receta> recetasTotales = new ArrayList<Receta>();

	private Receta panchoMock = mock(Receta.class);

	private Receta polloConPure;
	private Receta bifeConHuevoFrito;
	private Receta huevoFrito;
	private Receta pure;

	private Ingrediente carne = new Ingrediente("Carne");
	private Ingrediente huevo = new Ingrediente("Huevo");

	private Condimentacion pimienta = new Condimentacion("pimienta molida", 50);
	private Condimentacion nuezMoscada = new Condimentacion("nuez moscada", 20);
	private Condimentacion sal = new Condimentacion("sal fina", 100);
	private Condimentacion condimentoParaPollo = new Condimentacion("condimento P/pollo", 40);
	
	@Before
	public void setUp() {

		pure = new RecetaBuilder().setNombre("Pure")
				.agregarIngrediente(new Ingrediente("papas 2kg"))
				.agregarIngrediente(new Ingrediente("manteca 200gr"))
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(pimienta)
				.agregarCondimentaciones(nuezMoscada)
				.setCantCalorias(400)
				.setDificultad(Dificultad.MEDIA)
				.construirReceta();

		huevoFrito = new RecetaBuilder()
				.setNombre("Huevo Frito")
				.agregarIngrediente(huevo)
				.agregarCondimentaciones(sal)
				.setCantCalorias(200)
				.setDificultad(Dificultad.BAJA)
				.construirReceta();

		bifeConHuevoFrito = new RecetaBuilder()
				.setNombre("Bife con Huevo Frito")
				.agregarIngrediente(carne)
				.agregarCondimentaciones(sal)
				.setCantCalorias(400)
				.setDificultad(Dificultad.BAJA)
				.agregarSubReceta(huevoFrito)
				.construirReceta();

		polloConPure = new RecetaBuilder()
				.setNombre("Pollo Con Pure o Ensalada")
				.agregarIngrediente(new Ingrediente("pollo mediano"))
				.agregarCondimentaciones(sal)
				.agregarCondimentaciones(condimentoParaPollo)
				.setCantCalorias(3000)
				.setDificultad(Dificultad.ALTA)
				.construirReceta();

		gaston = new UsuarioBuilder().setNombre("gaston")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1993-10-15"))
				.setEstatura(1.68)
				.setPeso(65)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
		
		poe = new UsuarioBuilder().setNombre("Edgar Allan Poe")
				.setSexo(Sexo.MASCULINO)
				.setFechaDeNacimiento(LocalDate.parse("1809-01-19"))
				.setEstatura(1.73)
				.setPeso(75)
				.setRutina(Rutina.LEVE)
				.construirUsuario();

		entityManager().persist(poe); //requiere que el usuario esté persistido para decirle a la receta su dueño.
		
		recetasTotales.add(huevoFrito);
		Recetario.instancia.setRecetasTotales(recetasTotales);
	}

	@Test
	public void listarTodasPuedeAccederTest() {
		Recetario.instancia.crearReceta(bifeConHuevoFrito, poe);
		
		Collection<Receta> recetasQuePuedeAcceder = Recetario.instancia
				.listarTodasPuedeAcceder(gaston);

		assertTrue(recetasQuePuedeAcceder.contains(huevoFrito));
		assertFalse(recetasQuePuedeAcceder.contains(pure));
		assertFalse(recetasQuePuedeAcceder.contains(bifeConHuevoFrito));
	}

	@Test
	public void creaRecetaExitosa() {
		Recetario.instancia.crearReceta(pure, juanchi);
		assertTrue(Recetario.instancia.getRecetasTotales().contains(pure));
		
		assertTrue(Recetario.instancia.getReceta(pure).getDueño() == juanchi);
		assertFalse(Recetario.instancia.getReceta(pure).getDueño() == gaston);
	}
	
	@Test
	public void quitaUnaRecetaExitosa(){
		assertEquals(Recetario.instancia.getRecetasTotales(), Arrays.asList(huevoFrito));
		Recetario.instancia.quitarReceta(huevoFrito);
		assertEquals(Recetario.instancia.getRecetasTotales(), Arrays.asList());
	}

	@Test
	public void gastonCreaRecetaConSubrecetas() {
		Recetario.instancia.crearRecetaConSubRecetas(polloConPure,
				Arrays.asList(pure), gaston);
		
		assertTrue(Recetario.instancia.getReceta(polloConPure).getSubRecetas().contains(pure));
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void noPuedeCrearRecetaConSubRecetasSinAccesoAEllas() {
		when(gustavo.puedeAccederA(huevoFrito)).thenReturn(false);
		
		Recetario.instancia.crearRecetaConSubRecetas(pure,
				Arrays.asList(huevoFrito), gustavo);
		
		verify(gustavo, times(1)).puedeAccederA(huevoFrito);
	}

	@Test
	public void eliminarUnaReceta() {
		huevoFrito.setDueño(poe);
		poe.agregarAFavorita(huevoFrito);
		assertTrue(poe.getRecetasFavoritas().contains(huevoFrito));
		
		Recetario.instancia.eliminarReceta(huevoFrito, poe);

		assertFalse(poe.getRecetasFavoritas().contains(huevoFrito));
	}

	@Test(expected = NoPuedeEliminarRecetaExeption.class)
	public void noPuedeEliminarUnaRecetaQueNoCreo() {
		when(panchoMock.esElDueño(gaston)).thenReturn(false);
		Recetario.instancia.eliminarReceta(panchoMock, gaston);
	}

	@Test
	public void poeModificaReceta() {
		Recetario.instancia.crearReceta(pure, poe);
		
		Recetario.instancia.modificarReceta(pure, polloConPure,
				poe);

		assertEquals(pure.getNombre(), polloConPure.getNombre());
	}

	@Test(expected = NoPuedeAccederARecetaException.class)
	public void gastonNoPuedeModificarUnaRecetaDeOtro() {
		Recetario.instancia.crearReceta(pure, poe);
		
		Recetario.instancia.modificarReceta(pure, polloConPure,
				gaston);
	}

	@Test
	public void recetaEsModificadaCorrectamente() {
		// testIntegral
		Usuario pepita = new UsuarioBuilder().setNombre("pepita")
				.setFechaDeNacimiento(LocalDate.parse("2000-01-01")).setPeso(1)
				.setEstatura(1).setRutina(Rutina.INTENSIVA).construirUsuario();

		Condimentacion sal = new Condimentacion("sal fina", 100);
		Condimentacion pocaSal = new Condimentacion("sal fina", 50);
		Condimentacion aceite = new Condimentacion("Aceite de Maiz", 2);

		Receta ensaladaVieja = new RecetaBuilder().setNombre("Ensalada")
				.agregarIngrediente(new Ingrediente("Lechuga 2kg"))
				.agregarIngrediente(new Ingrediente("Cebolla 1.5kg"))
				.agregarIngrediente(new Ingrediente("Tomate 200gr"))
				.agregarCondimentaciones(sal).agregarCondimentaciones(aceite)
				.setCantCalorias(40).setDificultad(Dificultad.BAJA)
				.setTemporada(Temporada.VERANO)
				.setPreparacion("unaPreparacion")
				.construirReceta();

		Receta ensaladaNueva = new RecetaBuilder().setNombre("Ensalada")
				.agregarIngrediente(new Ingrediente("Lechuga 3kg"))
				.agregarIngrediente(new Ingrediente("Cebolla 1kg"))
				.agregarIngrediente(new Ingrediente("Tomate 300gr"))
				.agregarCondimentaciones(pocaSal)
				.agregarCondimentaciones(aceite).setCantCalorias(40)
				.setDificultad(Dificultad.BAJA)				
				.setTemporada(Temporada.VERANO)
				.setPreparacion("otraPreparacion")
				.construirReceta();

		Recetario.instancia.crearReceta(ensaladaNueva, pepita);
		Recetario.instancia.crearReceta(ensaladaVieja, pepita);

		assertFalse(Recetario.instancia.sonObjetosActualizados(ensaladaVieja,
				ensaladaNueva));

		Recetario.instancia.modificarReceta(ensaladaNueva, ensaladaVieja,
				pepita);

		assertTrue(Recetario.instancia.sonObjetosActualizados(ensaladaVieja,
				ensaladaNueva));

	}

}
