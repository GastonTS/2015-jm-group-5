package ar.edu.grupo5.jm.dss.QueComemos.Persistencia;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;

public class RecetaTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	private Receta bifeConHuevoFrito;
	private Receta bifeConHuevoFritoDB;
	private Receta huevoFrito;
	
	private Ingrediente carne = new Ingrediente("Carne");
	private Ingrediente huevo = new Ingrediente("Huevo");
	
	private Condimentacion sal = new Condimentacion("sal fina", 100);
	
	private Usuario gaston;
	
	@Before
	public void setUp(){
		
		huevoFrito = new RecetaBuilder().setNombre("Huevo Frito")
					 					.agregarIngrediente(huevo)
										.agregarCondimentaciones(sal)
										.setCantCalorias(200)
										.setDificultad(Dificultad.BAJA)
										.construirReceta();
				
		bifeConHuevoFrito = new RecetaBuilder().setNombre("Bife con Huevo Frito")
				   						 .agregarIngrediente(carne)
										 .agregarCondimentaciones(sal)
										 .setCantCalorias(400)
										 .setDificultad(Dificultad.BAJA)
										 .agregarSubReceta(huevoFrito)
										 .construirReceta();
		
		gaston = new UsuarioBuilder().setNombre("gaston")
				 .setSexo(Sexo.MASCULINO)
				 .setFechaDeNacimiento(LocalDate.parse("1993-10-15"))
				 .setEstatura(1.68)
				 .setPeso(65)
				 .setRutina(Rutina.MEDIANA)
				 .construirUsuario();
		
		bifeConHuevoFrito.setDueño(gaston);
		bifeConHuevoFrito.persistir();
		bifeConHuevoFritoDB = entityManager().find(Receta.class, bifeConHuevoFrito.getId());
	}

	@Test
	public void seGuardaUnaRecetaCorrectamente(){
		assertEquals(bifeConHuevoFritoDB, bifeConHuevoFrito);
	}
	

	@Test
	public void seGuardaElIdCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getId(), bifeConHuevoFrito.getId());
	}
	

	@Test
	public void seGuardanLasCaloriasCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getCantCaloriasTotales(), bifeConHuevoFrito.getCantCaloriasTotales(), 0.01);
	}
	

	@Test
	public void seGuardanLasCondimentacionesCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getCondimentacionesTotales(), bifeConHuevoFrito.getCondimentacionesTotales());
	}
	

	@Test
	public void seGuardaLaDificultadCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getDificultad(), bifeConHuevoFrito.getDificultad());
	}
	

	@Test
	public void seGuardaElNombreCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getNombre(), bifeConHuevoFrito.getNombre());
	}
	

	@Test
	public void seGuardanLosIngredientesCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getIngredientesTotales(), bifeConHuevoFrito.getIngredientesTotales());
	}
	

	@Test
	public void seGuardanLasSubRecetasCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getSubRecetas(), bifeConHuevoFrito.getSubRecetas());
	}

	@Test
	public void seGuardaElDueñoCorrectamente(){
		assertEquals(bifeConHuevoFritoDB.getDueño(), bifeConHuevoFrito.getDueño());
	}
	
}
