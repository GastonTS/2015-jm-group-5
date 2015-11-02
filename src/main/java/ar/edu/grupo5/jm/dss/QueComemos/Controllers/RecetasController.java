package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecetasController{
	
	public ModelAndView mostrar(Request request, Response response){

		return new ModelAndView(null, "recetas.hbs");
	}

    Double minCalorias;
    Double maxCalorias;
    
	public ModelAndView listar(Request request, Response response) {
		    Collection<Receta> recetas = Recetario.instancia.getRecetasTotales();
		    
		    minCalorias = 0.0;
		    maxCalorias = 999999.9;

		    String filtroNombre = request.queryParams("nombre");
		    String filtroDificultad = request.queryParams("dificultad");
		    String filtroMinCalorias = request.queryParams("cantMinCalorias");
		    String filtroMaxCalorias = request.queryParams("cantMaxCalorias");
		    String filtroTemporada = request.queryParams("temporada");
		    
		    if(!Objects.isNull(filtroMinCalorias) && !filtroMinCalorias.isEmpty())
		    	minCalorias = Double.parseDouble(request.queryParams("cantMinCalorias"));
		    if(!Objects.isNull(filtroMaxCalorias) && !filtroMaxCalorias.isEmpty())
		    	maxCalorias = Double.parseDouble(request.queryParams("cantMaxCalorias"));
		   
		    if (!Objects.isNull(filtroNombre) && !filtroNombre.isEmpty())
		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorNombre(filtroNombre).contains(unaReceta))
		    			.collect(Collectors.toList());
		    
		    if (!Objects.isNull(filtroDificultad) && !filtroDificultad.isEmpty()){

		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorDificultad(filtroDificultad).contains(unaReceta))
		    			.collect(Collectors.toList());
		    }
		    
		    if (!Objects.isNull(filtroTemporada) && !filtroTemporada.isEmpty()){

		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorTemporada(filtroTemporada).contains(unaReceta))
		    			.collect(Collectors.toList());
		    }
		    
		    if (!Objects.isNull(filtroMinCalorias) && !Objects.isNull(filtroMaxCalorias))
		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorRangoCalorias(minCalorias, maxCalorias).contains(unaReceta))
		    			.collect(Collectors.toList());
		    
		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("recetas", recetas);
		    viewModel.put("nombre", filtroNombre);
		    viewModel.put("dificultad", filtroDificultad);
		    viewModel.put("temporada", filtroTemporada);
		    viewModel.put("cantMinCalorias", filtroMinCalorias);
		    viewModel.put("cantMaxCalorias", filtroMaxCalorias);

		    return new ModelAndView(viewModel, "recetas.hbs");
	}
	
	public ModelAndView detalle (Request request, Response response) {
		
		Receta receta = this.buscarReceta();
		Collection<Ingrediente> ingredientes = receta.getIngredientesTotales();
		
		 HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("receta", receta);
		    viewModel.put("ingredientes", ingredientes);
		return  new ModelAndView(viewModel, "detalleReceta.hbs");
	}

	private Receta buscarReceta() {
		Receta receta = new Receta("Ñoquis a la boloñesa", new ArrayList<>(),
				 new ArrayList<>(),
				 new ArrayList<>(), 123.00 , Dificultad.MEDIA, Temporada.TODOELAÑO);
		return receta;
	}
}