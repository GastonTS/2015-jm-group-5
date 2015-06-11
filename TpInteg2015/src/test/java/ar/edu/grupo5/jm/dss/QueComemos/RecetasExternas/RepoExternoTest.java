package ar.edu.grupo5.jm.dss.QueComemos.RecetasExternas;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import queComemos.entrega3.repositorio.RepoRecetas;

public class RepoExternoTest {

	RepoRecetas repoRecetasMock = mock(RepoRecetas.class);
	RepositorioExterno repoExterno = new RepositorioExterno(repoRecetasMock);
	
	
	@Test
	public void recibeRecetasDeUnJsonString() {
		String recetaFormatoJson = "{\"nombre\":\"ensalada caesar\",\"ingredientes\":[\"lechuga\",\"croutons\",\"parmesano\"],\"tiempoPreparacion\":0,\"totalCalorias\":15,\"dificultadReceta\":\"FACIL\",\"autor\":\"Cesar Po\",\"anioReceta\":2015}";
		Receta recetaGenerada = repoExterno.jsonObjectToReceta(JsonObject.readFrom(recetaFormatoJson));
		assertEquals(recetaGenerada.getNombre(),"ensalada caesar");
		Collection<String> ingredientesGenerados = recetaGenerada.getIngredientesTotales();
		Collection<String> ingredientes = Arrays.asList("lechuga","croutons","parmesano");
		assertEquals(recetaGenerada.getNombre(),"ensalada caesar");
		assertTrue(ingredientesGenerados.containsAll(ingredientes));
		assertTrue(ingredientes.containsAll(ingredientesGenerados));
		assertEquals(recetaGenerada.getCantCaloriasTotales(),15,0.01);
		assertEquals(recetaGenerada.getDificultad(),Dificultad.BAJA);
	}
	
	@Test
	public void deStringIngredientesAIngredientes() {
		JsonArray ingredientesArray = JsonArray.readFrom("[\"lechuga\",\"croutons\",\"parmesano\"]");
		Collection<String> ingredientesContenidos = Arrays.asList("lechuga","croutons","parmesano");
		
		assertTrue(repoExterno.jsonArrayToStringCollection(ingredientesArray).containsAll(ingredientesContenidos));
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
	
	@Test
	public void otraDificultadEsOtra() {
		assertTrue(repoExterno.stringToDificultad("UNADIFICULTADDISTINTA").equals(Dificultad.OTRA));
	}
	
	
}
