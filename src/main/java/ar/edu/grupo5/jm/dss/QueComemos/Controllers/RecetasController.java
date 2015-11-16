package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.Collection;
import java.util.HashMap;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorDificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorNombre;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorRangoCalorias;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorTemporada;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.SinFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Main.Bootstrap;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecetasController implements WithGlobalEntityManager, TransactionalOps {
	 
	public ModelAndView listar(Request request, Response response) {
		    SinFiltro sinFiltro = new SinFiltro();
		    String filtroNombre = request.queryParams("nombre");
		    String filtroDificultad = request.queryParams("dificultad");
		    String filtroMinCalorias = request.queryParams("cantMinCalorias");
		    String filtroMaxCalorias = request.queryParams("cantMaxCalorias");
		    String filtroTemporada = request.queryParams("temporada");
		    
		    Dificultad dificultad = (filtroDificultad!= null && !filtroDificultad.equals(""))? Dificultad.valueOf(filtroDificultad) : null;
		    Temporada temporada = (filtroTemporada!= null && !filtroTemporada.equals(""))? Temporada.valueOf(filtroTemporada) : null;
		    
		    PorNombre superFiltro = new PorNombre(new PorDificultad
		    		(new PorTemporada(new PorRangoCalorias(sinFiltro, filtroMinCalorias, filtroMaxCalorias), 
		    				filtroTemporada), filtroDificultad), filtroNombre);
		    
		    
		    //FIXME withTransaction
		    Collection<Receta> recetas = Recetario.instancia.getRecetasTotales();
		    
		    recetas = new Consulta(Recetario.instancia, superFiltro, currentUser())
		    	.getRecetasConsultadas();
		    		
		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("recetas", recetas);
		    viewModel.put("dificultades", Dificultad.values());
		    viewModel.put("temporadas", Temporada.values());
		    viewModel.put("nombre", filtroNombre);
		    viewModel.put("dificultad", dificultad);
		    viewModel.put("temporada", temporada);
		    viewModel.put("cantMinCalorias", filtroMinCalorias);
		    viewModel.put("cantMaxCalorias", filtroMaxCalorias);

		    return new ModelAndView(viewModel, "recetas.hbs");
	}
	
	public ModelAndView detalle (Request request, Response response) {
		
		String idProperty = request.params("idReceta");
		
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
	
	public ModelAndView editar (Request request, Response response) {
			
		String idProperty = request.params("idReceta");
	
		if(idProperty == null) {
			return null; //page 404
		} else {
			Receta receta = Recetario.instancia.getReceta(Long.parseLong(idProperty));
			if(receta != null) {
				return edicionReceta(receta);
			}
		}
		return null; //page 404   
		}
	
	
	public ModelAndView edicionReceta (Receta receta){
		HashMap<String, Object> viewModel = new HashMap<>();
		Collection<Ingrediente> ingredientes = receta.getIngredientes(); 
		Collection<Receta> recetas = Recetario.instancia.getRecetas(currentUser());
		recetas.remove(receta);
	    viewModel.put("receta", receta);
	    viewModel.put("recetas", recetas);
	    viewModel.put("dificultades", Dificultad.values());
	    viewModel.put("temporadas", Temporada.values());
	    viewModel.put("ingredientes", ingredientes);
	   
		return new ModelAndView(viewModel, "edicionReceta.hbs");
	}
	
	private ModelAndView showReceta(Receta receta) {
		Collection<CondicionDeSalud> condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(receta);
		
		Usuario currentUser = currentUser();
		
		HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("receta", receta);
		    viewModel.put("publica", receta.esPublica());
		    viewModel.put("autor", receta.getDueño());
		    viewModel.put("favorita", currentUser.esRecetaFavorita(receta));
		    viewModel.put("inadecuado", !condicionesInadecuadas.isEmpty());
		    viewModel.put("condicionesInadecuado", condicionesInadecuadas);
		    viewModel.put("hasPhoto", !receta.getUrlImagen().isEmpty());
		return  new ModelAndView(viewModel, "detalleReceta.hbs");
	}
		
	public Void cambiarFavorita(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
		withTransaction(() -> {
			Receta receta = Recetario.instancia.getReceta(idReceta);
		    if(request.queryParams("value").equals("true")) {
		    	currentUser().agregarAFavorita(receta);
		    } else {
		    	currentUser().quitarRecetaFavorita(receta);
		    }	
		});
	    response.redirect("show");
		return null;
	}
	
	private Usuario currentUser() {
		return new Bootstrap().currentUserHARDCODE();
	}
}