package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecetasController{
	
	public ModelAndView mostrar(Request request, Response response){

		return new ModelAndView(null, "recetas.hbs");
	}

    Dificultad dificultad = null;
    Double minCalorias = 0.0;
    Double maxCalorias = 0.0;
    
	public ModelAndView listar(Request request, Response response) {
		    Collection<Receta> recetas = Recetario.instancia.getRecetasTotales();

		    String filtroNombre = request.queryParams("nombre");
		    String filtroDificultad = request.queryParams("dificultad");
		    String filtroMinCalorias = request.queryParams("cantMinCalorias");
		    String filtroMaxCalorias = request.queryParams("cantMaxCalorias");
		    
		    if(!Objects.isNull(filtroMinCalorias) && !filtroMinCalorias.isEmpty())
		    	minCalorias = Double.parseDouble(request.queryParams("cantMinCalorias"));
		    else
		    	minCalorias = 0.0;
		    
		    if(!Objects.isNull(filtroMaxCalorias) && !filtroMaxCalorias.isEmpty())
		    	maxCalorias = Double.parseDouble(request.queryParams("cantMaxCalorias"));
		    else
		    	maxCalorias = 999999.9;
		   
		    if (!Objects.isNull(filtroNombre) && !filtroNombre.isEmpty())
		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorNombre(filtroNombre).contains(unaReceta))
		    			.collect(Collectors.toList());
		    
		    if (!Objects.isNull(filtroDificultad) && !filtroDificultad.isEmpty()){
		    	
		    	if(filtroDificultad == "ALTA")
		    		dificultad = Dificultad.ALTA;
		    	else if(filtroDificultad == "MEDIA")
		    		dificultad = Dificultad.MEDIA;
		    	else if(filtroDificultad == "BAJA")
		    		dificultad = Dificultad.BAJA;
		    	
		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorDificultad(dificultad).contains(unaReceta))
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
		    viewModel.put("cantMinCalorias", filtroMinCalorias);
		    viewModel.put("cantMaxCalorias", filtroMaxCalorias);

		    return new ModelAndView(viewModel, "recetas.hbs");
	}
}