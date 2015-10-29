package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecetasController{
	
	public ModelAndView mostrar(Request request, Response response){

		return new ModelAndView(null, "recetas.hbs");
	}


	 public ModelAndView listar(Request request, Response response) {
		    List<Receta> recetas = null;

		    String filtroNombre = request.queryParams("nombre");
		    String filtroDificultad = request.queryParams("dificultad");
		    String filtroCalorias = request.queryParams("cantCalorias");
		    
		    
		    HashMap<String, String> hm = new HashMap<>();
		   
		    if (!Objects.isNull(filtroNombre) && !filtroNombre.isEmpty())
		    	hm.put("nombre", filtroNombre);
		    
		    if (!Objects.isNull(filtroDificultad) && !filtroDificultad.isEmpty())
		    	hm.put("dificultad", filtroDificultad);
		    
		    if (!Objects.isNull(filtroCalorias) && !filtroCalorias.isEmpty())
		    	hm.put("cantCalorias", filtroCalorias);
		    
		    
			recetas = (List<Receta>) Recetario.instancia.filtrarPorStrings(hm);
		    
		    
		    
		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("recetas", recetas);
		    viewModel.put("nombre", filtroNombre);
		    viewModel.put("dificultad", filtroDificultad);
		    viewModel.put("cantCalorias", filtroCalorias);

		    return new ModelAndView(viewModel, "recetas.hbs");
	}
}