package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Main.Bootstrap;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
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
		
		String idProperty = request.queryParams("id");
		
		if(idProperty == null) {
			//show 404 page
		} else {
			Receta receta = Recetario.instancia.getReceta(Long.parseLong(idProperty));
			if(receta != null) {
				return showReceta(receta);
			}
		}
		
		return null; //page 404
	}

	private ModelAndView showReceta(Receta receta) {
		Collection<Dificultad> dificultades = Arrays.asList(Dificultad.values())
				.stream().filter(dificultad -> dificultad != receta.getDificultad())
				.collect(Collectors.toList());
		
		Collection<CondicionDeSalud> condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(receta);
		
		Usuario currentUser = new Bootstrap().currentUserHARDCODE();
		
		HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("receta", receta);
		    viewModel.put("dificultades", dificultades);
		    viewModel.put("publica", receta.esPublica());
		    viewModel.put("autor", receta.getDueño());
		    viewModel.put("favorita", currentUser.esRecetaFavorita(receta));
		    viewModel.put("inadecuado", !condicionesInadecuadas.isEmpty());
		    viewModel.put("condicionesInadecuado", condicionesInadecuadas);
		    
		return  new ModelAndView(viewModel, "detalleReceta.hbs");
	}
}