package ar.edu.grupo5.jm.dss.QueComemos.RecetasExternas;

import java.util.ArrayList;
import java.util.Collection;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

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
		Collection<Receta> recetas = new ArrayList<Receta>();
		for (JsonValue recetaValue : recetasArray) {
			recetas.add(jsonObjectToReceta(recetaValue.asObject()));
		}
		return recetas;
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
				return Dificultad.OTRA;
		}
	}
	
	public Collection<String> jsonArrayToStringCollection(JsonArray ingredientesArray) {
		Collection<String> ingredientes = new ArrayList<String>();
		for (JsonValue ingredienteValue : ingredientesArray) {
			ingredientes.add(ingredienteValue.asString());
		}
		return ingredientes;
	}

}
