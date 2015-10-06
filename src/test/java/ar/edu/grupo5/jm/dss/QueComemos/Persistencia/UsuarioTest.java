package ar.edu.grupo5.jm.dss.QueComemos.Persistencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Hipertenso;

public class UsuarioTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	private Usuario gaston;
	private Usuario gastonDB;

	private Ingrediente pasaDeUva = new Ingrediente("Pasa de uva");
	private Ingrediente carne = new Ingrediente("Carne");
	private Ingrediente huevo = new Ingrediente("Huevo");

	private Condimentacion sal = new Condimentacion("sal fina", 100);

	private Receta huevoFrito;

	@Before
	public void setUp() {

		huevoFrito = new RecetaBuilder().setNombre("Huevo Frito")
					 					.agregarIngrediente(huevo)
										.agregarCondimentaciones(sal)
										.setCantCalorias(200)
										.setDificultad(Dificultad.BAJA)
										.construirReceta();
		
		gaston = new UsuarioBuilder().setNombre("gaston")
									 .setSexo(Sexo.MASCULINO)
									 .setFechaDeNacimiento(LocalDate.parse("1993-10-15"))
									 .setEstatura(1.68)
									 .setPeso(65)
									 .agregarDisgustoAlimenticio(pasaDeUva)
									 .agregarPreferenciaAlimenticia(carne)
									 .agregarPreferenciaAlimenticia(huevo)
									 .agregarCondicionesDeSalud(new Hipertenso())
									 .setMail("gastonsantoalla@gmail.com")
									 .setRutina(Rutina.MEDIANA)
									 .construirUsuario();
		
		gaston.agregarAFavorita(huevoFrito);
		gaston.aceptar();

		gaston.persistir();

		gastonDB = entityManager().find(Usuario.class, gaston.getId());
	}

	@Test
	public void seGuardaUnUsuarioCorrectamente() {
		assertEquals(gastonDB, gaston);
	}
	
	@Test
	public void seGuardaSuEntradaAlSistema() {
		assertTrue(gastonDB.fueAceptado());
		assertEquals(gastonDB.fueAceptado(), gaston.fueAceptado());
	}

	@Test
	public void seGuardaElIdCorrectamente() {
		assertEquals(gastonDB.getId(), gaston.getId());
	}

	@Test
	public void seGuardaElNombreCorrectamente() {
		assertEquals(gastonDB.getNombre(), gaston.getNombre());
	}

	@Test
	public void seGuardaElSexoCorrectamente() {
		assertEquals(gastonDB.getSexo(), gaston.getSexo());
	}

	@Test
	public void seGuardaLaFechaDeNacimientoCorrectamente() {
		assertEquals(gastonDB.getFechaDeNacimiento(), gaston.getFechaDeNacimiento());
	}

	@Test
	public void seGuardaLaEstaturaCorrectamente() {
		assertEquals(gastonDB.getEstatura(), gaston.getEstatura(), 0.01);
	}

	@Test
	public void seGuardaElPesoCorrectamente() {
		assertEquals(gastonDB.getPeso(), gaston.getPeso(), 0.01);
	}

	@Test
	public void seGuardanLasCondicionesDeSaludCorrectamente() {
		assertEquals(gastonDB.getCondicionesDeSalud(), gaston.getCondicionesDeSalud());
	}

	@Test
	public void seGuardanLasPreferenciasAlimenticiasCorrectamente() {
		assertEquals(gastonDB.getPreferenciasAlimenticias(), gaston.getPreferenciasAlimenticias());
	}

	@Test
	public void seGuardanLosDisgustosAlimenticiosCorrectamente() {
		assertEquals(gastonDB.getDisgustosAlimenticios(), gaston.getDisgustosAlimenticios());
	}

	@Test
	public void seGuardanLosGruposCorrectamente() {
		assertEquals(gastonDB.getGrupos(), gaston.getGrupos());
	}

	@Test
	public void seGuardaLaRutinaCorrectamente() {
		assertEquals(gastonDB.getRutina(), gaston.getRutina());
	}

	@Test
	public void seGuardanLasRecetasFavoritasCorrectamente() {
		assertEquals(gastonDB.getRecetasFavoritas(), gaston.getRecetasFavoritas());
	}

	@Test
	public void seGuardaElMailCorrectamente() {
		assertEquals(gastonDB.getMail(), gaston.getMail());
	}

}
