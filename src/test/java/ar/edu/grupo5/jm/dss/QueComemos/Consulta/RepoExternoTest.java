package ar.edu.grupo5.jm.dss.QueComemos.Consulta;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.DificultadDeRepoExternoNoValidaException;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.RepositorioExterno;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import queComemos.entrega3.repositorio.RepoRecetas;

public class RepoExternoTest {

	RepoRecetas repoRecetasMock = mock(RepoRecetas.class);
	RepositorioExterno repoExterno = new RepositorioExterno(repoRecetasMock);
	Usuario usuarioMock = mock(Usuario.class);
	
	
	@Test
	public void recibeColeccionDeRecetasDeUnJsonString() {
		String recetasFormatoJson = "[{\"nombre\":\"ensalada caesar\",\"ingredientes\":[\"lechuga\",\"croutons\","
				+"\"parmesano\"],\"tiempoPreparacion\":0,\"totalCalorias\":15,\"dificultadReceta\":\"FACIL\"," 
				+"\"autor\":\"Cesar Po\",\"anioReceta\":2015},"
				
				+"{\"nombre\":\"ensalada lechuga agridulce\",\"ingredientes\":[\"lechuga\",\"croutons\","
				+"\"parmesano\"],\"tiempoPreparacion\":0,\"totalCalorias\":15,\"dificultadReceta\":\"FACIL\"," 
				+"\"autor\":\"Cesar Po\",\"anioReceta\":2015}]";
		
		when(repoRecetasMock.getRecetas(any())).thenReturn(recetasFormatoJson);
		
		Collection<Receta> recetasGeneradas = repoExterno.getRecetas(usuarioMock);
		assertEquals(recetasGeneradas.size(),2);
		
		verify(repoRecetasMock, times(1)).getRecetas(any());
	}
	
	
	@Test
	public void recibeUnaRecetaDeUnJsonString() {
		String recetaFormatoJson = "{\"nombre\":\"ensalada caesar\",\"ingredientes\":[\"lechuga\",\"croutons\",\"parmesano\"],\"tiempoPreparacion\":0,\"totalCalorias\":15,\"dificultadReceta\":\"FACIL\",\"autor\":\"Cesar Po\",\"anioReceta\":2015}";
		Receta recetaGenerada = repoExterno.jsonObjectToReceta(JsonObject.readFrom(recetaFormatoJson));
		assertEquals(recetaGenerada.getNombre(),"ensalada caesar");
		Collection<Ingrediente> ingredientesGenerados = recetaGenerada.getIngredientesTotales();
		Collection<Ingrediente> ingredientes = Arrays.asList(new Ingrediente("lechuga"),new Ingrediente("croutons"),new Ingrediente("parmesano"));
		
		assertEquals(recetaGenerada.getNombre(),"ensalada caesar");
		assertEquals(ingredientesGenerados, ingredientes);
		assertEquals(recetaGenerada.getCantCaloriasTotales(),15,0.01);
		assertEquals(recetaGenerada.getDificultad(),Dificultad.BAJA);
	}
	
	@Test
	public void deStringIngredientesAIngredientes() {
		JsonArray ingredientesArray = JsonArray.readFrom("[\"lechuga\",\"croutons\",\"parmesano\"]");
		Collection<Ingrediente> ingredientesContenidos = Arrays.asList(new Ingrediente("lechuga"), new Ingrediente("croutons"), new Ingrediente("parmesano"));
		
		assertEquals(repoExterno.jsonArrayToStringCollection(ingredientesArray), ingredientesContenidos);
	}
	
	@Test
	public void dificultadDificilEsAlta() {
		assertTrue(repoExterno.stringToDificultad("DIFICIL").equals(Dificultad.ALTA));
	}
	
	@Test
	public void dificultadMedianaEsMedia() {
		assertTrue(repoExterno.stringToDificultad("MEDIANA").equals(Dificultad.MEDIA));
	}

	@Test
	public void dificultadFacilEsBaja() {
		assertTrue(repoExterno.stringToDificultad("FACIL").equals(Dificultad.BAJA));
	}
	
	@Test(expected = DificultadDeRepoExternoNoValidaException.class)
	public void otraDificultadLanzaExcepcion() {
		repoExterno.stringToDificultad("UNADIFICULTADDISTINTA");
	}
	
	
}
