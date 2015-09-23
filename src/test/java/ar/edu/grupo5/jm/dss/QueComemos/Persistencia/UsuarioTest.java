package ar.edu.grupo5.jm.dss.QueComemos.Persistencia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Hipertenso;

import java.time.LocalDate;

public class UsuarioTest extends AbstractPersistenceTest implements WithGlobalEntityManager {


	private Usuario gaston;
	private Ingrediente pasaDeUva = new Ingrediente("Pasa de uva");
	private Ingrediente carne = new Ingrediente("Carne");
	private Ingrediente huevo = new Ingrediente("Huevo");
	
	@Before
	public void setUp(){
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
	}
	
	@Test
	public void seGuardaUnUsuarioCorrectamente(){
		gaston.persistir();

		assertEquals(entityManager().find(Usuario.class, gaston.getId()), gaston);
	}
}
