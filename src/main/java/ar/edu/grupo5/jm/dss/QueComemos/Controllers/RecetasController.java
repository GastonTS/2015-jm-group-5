package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorDificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorNombre;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorRangoCalorias;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.PorTemporada;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Filtro.SinFiltro;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.RepoObservadorConsultas;
import ar.edu.grupo5.jm.dss.QueComemos.Main.Bootstrap;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.NoPuedeAccederARecetaException;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaNoValidaException;
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
		    
		    
		    int topeMaxCalorias = Recetario.instancia.topeCaloriasPuedeAcceder(currentUser());
		    Consulta consulta = new Consulta(Recetario.instancia, superFiltro, currentUser());
		    withTransaction(() -> {
		    	RepoObservadorConsultas.instancia.notificarObservadores(consulta);
		    });
		    
		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("recetas", consulta.getRecetasConsultadas());
		    viewModel.put("dificultades", Dificultad.values());
		    viewModel.put("temporadas", Temporada.values());
		    viewModel.put("nombre", filtroNombre);
		    viewModel.put("dificultad", dificultad);
		    viewModel.put("temporada", temporada);
	    	viewModel.put("cantMinCalorias", filtroMinCalorias);
	    	viewModel.put("cantMaxCalorias", filtroMaxCalorias);
		    viewModel.put("topeMinCalorias", 0);
		    viewModel.put("topeMaxCalorias", topeMaxCalorias);

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
	
	public Void agregarSubReceta(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
	    String idSubReceta = request.queryParams("idSubReceta");
	    
	    if(idSubReceta != null && !idSubReceta.isEmpty()) {
	    	modificarRecetaCon(idReceta, (receta -> {
	    		Receta subReceta = Recetario.instancia.getReceta(Long.parseLong(idSubReceta));
	    		return  new RecetaBuilder()
	    			.setPropiedadesDe(receta)
	    			.agregarSubReceta(subReceta)
	    			.construirReceta();
	    	}), response);
	    }
		return null;
	}
	
	public Void quitarSubReceta(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
	    String idSubReceta = request.queryParams("idSubReceta");
	    
	    if(idSubReceta != null && !idSubReceta.isEmpty()) {
	    	modificarRecetaCon(idReceta, (receta -> {
	    		Receta subReceta = Recetario.instancia.getReceta(Long.parseLong(idSubReceta));
	    		return  new RecetaBuilder()
	    			.setPropiedadesDe(receta)
	    			.removerSubReceta(subReceta)
	    			.construirReceta();
	    	}), response);
	    }
		return null;
	}
	
	
	public Void agregarCondimentacion(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
	    String condimento = request.queryParams("condimento");
	    String cantidad = request.queryParams("cantidad");
	    
	    
	    if(condimento != null && !condimento.isEmpty() && cantidad != null && !cantidad.isEmpty()) {
	    	modificarRecetaCon(idReceta, (receta -> {
	    		return  new RecetaBuilder()
	    			.setPropiedadesDe(receta)
	    			.agregarCondimentaciones(new Condimentacion(condimento, Double.parseDouble(cantidad)))
	    			.construirReceta();
	    	}), response);
	    }
		return null;
	}
	
	public Void quitarCondimentacion(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
		String idCondimentacion = request.queryParams("idCondimentacion");
	    
	    if(idCondimentacion != null && !idCondimentacion.isEmpty()) {
	    	modificarRecetaCon(idReceta, (receta -> {
	    		return  new RecetaBuilder()
	    			.setPropiedadesDe(receta)
	    			.removerCondimentacionDeId(Long.parseLong(idCondimentacion))
	    			.construirReceta();
	    	}), response);
	    }
		return null;
	}
	
	public Void agregarIngrediente(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
	    String ingrediente = request.queryParams("ingrediente");
	    
	    if(ingrediente != null && !ingrediente.isEmpty()) {
	    	modificarRecetaCon(idReceta, (receta -> {
	    		return  new RecetaBuilder()
	    			.setPropiedadesDe(receta)
	    			.agregarIngrediente(new Ingrediente(ingrediente))
	    			.construirReceta();
	    	}), response);
	    }
		return null;
	}
	
	public Void quitarIngrediente(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
		String idIngrediente = request.queryParams("idIngrediente");
	    
	    if(idIngrediente != null && !idIngrediente.isEmpty()) {
	    	modificarRecetaCon(idReceta, (receta -> {
	    		return  new RecetaBuilder()
	    			.setPropiedadesDe(receta)
	    			.removerIngredienteDeId(Long.parseLong(idIngrediente))
	    			.construirReceta();
	    	}), response);
	    }
		return null;
	}
	
	public Void editarBasicos(Request request, Response response) {
		Long idReceta = Long.parseLong(request.params("idReceta"));
	    String nombre = request.queryParams("nombre");
	    String url = request.queryParams("url");
	    String temporada = request.queryParams("temporada");
	    String calorias = request.queryParams("calorias");
	    String dificultad = request.queryParams("dificultad");
	    String proceso = request.queryParams("proceso");
		
	    modificarRecetaCon(idReceta, (receta -> {
	    	return this.nuevaRecetaConCamposBasicosActualizados(receta, 
	    			nombre, url, temporada, calorias, dificultad, proceso);
	    	}), response);
	    
		return null;
	}
	
	private Receta nuevaRecetaConCamposBasicosActualizados(Receta receta, String nombre, String url, String temporada, String calorias, String dificultad,
			String proceso) {
		
		RecetaBuilder builder = new RecetaBuilder().setPropiedadesDe(receta);
		
    	if(nombre != null && !nombre.isEmpty()) {
    		builder.setNombre(nombre);
    	}

    	if(url != null) {
    		builder.setUrlImagen(url);
    	}

    	if(temporada != null && !temporada.isEmpty()) {
    		builder.setTemporada(Temporada.valueOf(temporada));
    	}

    	if(calorias != null && !calorias.isEmpty()) {
    		builder.setCantCalorias(Double.parseDouble(calorias));
    	}
    	
    	if(dificultad != null && !dificultad.isEmpty()) {
    		builder.setDificultad(Dificultad.valueOf(dificultad));
    	}

    	if(proceso != null) {
    		builder.setPreparacion(proceso);
    	}
    	
    	return builder.construirReceta();
	}

	private void modificarRecetaCon(Long idReceta, Function<Receta, Receta> trasnformer, Response response) {
		try {
			withTransaction(() -> {
				Receta receta = Recetario.instancia.getReceta(idReceta);
				Receta nuevaReceta = trasnformer.apply(receta);
				Recetario.instancia.modificarReceta(receta, nuevaReceta, currentUser());
			});
			//TODO Cartel se grabaron los cambios, ver problema redirigir a nueva copia privada cuando modifico publica
			response.redirect("edit");
			
		} catch (RecetaNoValidaException ex) {
	    	//TODO no se pudo modificar la receta: la receta resultante no es válida!
	    } catch (NoPuedeAccederARecetaException ex) {
			//TODO no se pudo modificar la receta: usuario no tiene permisos para ello!
		}
	}
	
	private Usuario currentUser() {
		return new Bootstrap().currentUserHARDCODE();
	}
}