package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorDificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorNombre;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorRangoCalorias;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorTemporada;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.SinFiltro;
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

    SinFiltro sinFiltro = new SinFiltro();
	Usuario usuario = new Bootstrap().currentUserHARDCODE();
	 
	public ModelAndView mostrar(Request request, Response response){

		return new ModelAndView(null, "recetas.hbs");
	}
    
	public ModelAndView listar(Request request, Response response) {
		    Collection<Receta> recetas = Recetario.instancia.getRecetasTotales();

		    String filtroNombre = request.queryParams("nombre");
		    String filtroDificultad = request.queryParams("dificultad");
		    String filtroMinCalorias = request.queryParams("cantMinCalorias");
		    String filtroMaxCalorias = request.queryParams("cantMaxCalorias");
		    String filtroTemporada = request.queryParams("temporada");
		    
		    PorNombre superFiltro = new PorNombre(new PorDificultad
		    		(new PorTemporada(new PorRangoCalorias(sinFiltro, filtroMinCalorias, filtroMaxCalorias), 
		    				filtroTemporada), filtroDificultad), filtroNombre);
		    
		    recetas = new Consulta(Recetario.instancia, superFiltro, new Bootstrap().currentUserHARDCODE())
		    	.getRecetasConsultadas();
		    		
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
			return null; //page 404
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
		    viewModel.put("hasPhoto", !receta.getUrlImagen().isEmpty());
		return  new ModelAndView(viewModel, "detalleReceta.hbs");
	}
}