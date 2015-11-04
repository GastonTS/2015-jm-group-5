package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.grupo5.jm.dss.QueComemos.Main.Bootstrap;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.RepoUsuarios;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;


public class UsuariosController {
	
	public ModelAndView listar(Request request, Response response) {
		Collection<Usuario>listaUsuario = RepoUsuarios.instancia.getUsuariosAceptados();
	 
		HashMap<String, Object> viewModel = new HashMap<>();
	    viewModel.put("usuarios", listaUsuario);
	    return new ModelAndView(viewModel, "usuarios.hbs");
	}

	public ModelAndView verPerfil(Request request, Response response) {
		 Usuario usuario = new Bootstrap().currentUserHARDCODE();
		 
		 Collection<Ingrediente> disgustos =usuario.getDisgustosAlimenticios();
		 Collection<Ingrediente> gustos = usuario.getPreferenciasAlimenticias();
		 Collection<Receta> recetasFavoritas = usuario.getRecetasFavoritas();
		 
		  LocalDate date = usuario.getFechaDeNacimiento();
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		  String text = date.format(formatter);
	
		
			 HashMap<String, Object> viewModel = new HashMap<>();
			    viewModel.put("usuario", usuario);
			    viewModel.put("fecha", text);
			    viewModel.put("disgustos", disgustos);
			    viewModel.put("gustos", gustos);
			    viewModel.put("recetasFavoritas", recetasFavoritas);  	
		    return new ModelAndView(viewModel, "perfilUsuario.hbs");
	}	 
}
