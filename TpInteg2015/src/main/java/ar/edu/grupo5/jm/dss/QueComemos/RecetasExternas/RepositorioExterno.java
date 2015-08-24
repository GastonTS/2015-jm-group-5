package ar.edu.grupo5.jm.dss.QueComemos.RecetasExternas;

import java.util.Collection;
import java.util.stream.Collectors;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import ar.edu.grupo5.jm.dss.QueComemos.ConsultorRecetas;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import queComemos.entrega3.repositorio.RepoRecetas;
import queComemos.entrega3.repositorio.BusquedaRecetas;

;

public class RepositorioExterno implements ConsultorRecetas{
	
	private RepoRecetas repositorio;
	
	public RepositorioExterno (RepoRecetas unRepositorio){
		repositorio = unRepositorio;
	}

	@Override
	public Collection<Receta> getRecetas(Usuario unUsuario) {
		String recetasJson = repositorio.getRecetas(new BusquedaRecetas());
		return jsonStringToRecetasCollection(recetasJson);
	}
	
	public Collection<Receta> jsonStringToRecetasCollection(String recetasJson) {
		JsonArray recetasArray = JsonArray.readFrom(recetasJson);
		return recetasArray.values().stream().map((recetaValue -> jsonObjectToReceta(recetaValue.asObject()))).collect(Collectors.toList());
	}

	public Receta jsonObjectToReceta(JsonObject recetaObject) {
		JsonArray ingredientesArray = recetaObject.get("ingredientes").asArray();
		
		return 	new RecetaBuilder().setNombre(recetaObject.get("nombre").asString())
				.agregarTodosLosIngredientes(jsonArrayToStringCollection(ingredientesArray))
				.setCantCalorias(recetaObject.get("totalCalorias").asDouble())
				.setDificultad(stringToDificultad(recetaObject.get("dificultadReceta").asString()))
				.construirReceta();
	}

	public Dificultad stringToDificultad(String dificultadString) {
		switch(dificultadString) {
			case "DIFICIL": 
				return Dificultad.ALTA;
			case "MEDIANA":
				return Dificultad.MEDIA;
			case "FACIL":
				return Dificultad.BAJA;
			default:
				throw new DificultadDeRepoExternoNoValidaException(dificultadString);
		}
	}
	
	public Collection<String> jsonArrayToStringCollection(JsonArray ingredientesArray) {
		return ingredientesArray.values().stream().map((ingredienteValue -> ingredienteValue.asString())).collect(Collectors.toList());
	}

}
