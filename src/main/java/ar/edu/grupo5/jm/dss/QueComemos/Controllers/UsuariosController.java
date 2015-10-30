package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;


public class UsuariosController {
	
	private ArrayList<Usuario> listU = new ArrayList<Usuario>();
	
	 public ModelAndView listar(Request request, Response response) {
		 ArrayList<Usuario>listaUsuario = this.agregar();
		 
			 HashMap<String, Object> viewModel = new HashMap<>();
			    viewModel.put("usuarios", listaUsuario);
			   	
		    return new ModelAndView(viewModel, "usuarios.hbs");
	}
	 
	public ArrayList<Usuario> agregar() {
		
		  Usuario lean = new Usuario((new DatosPersonales("leandro",Sexo.MASCULINO,LocalDate.parse("1993-11-09")))
					,new Complexion(70,150)
			, new ArrayList<Ingrediente>()
			,new ArrayList<Ingrediente>()
			, new ArrayList<CondicionDeSalud>()
			,Rutina.MEDIANA, "lean.com");
		  listU.add(lean);
		  
		return listU;
	}
	
	 public ModelAndView verPerfil(Request request, Response response) {
		 
		 Usuario usuario = this.buscarUsuario();
		 
		 Collection<Ingrediente> disgustos =usuario.getDisgustosAlimenticios();
		 Collection<Ingrediente> gustos = usuario.getPreferenciasAlimenticias();
		 Collection<Receta> recetasFavoritas = usuario.getRecetasFavoritas();
		
			 HashMap<String, Object> viewModel = new HashMap<>();
			    viewModel.put("usuario", usuario);
			    viewModel.put("disgustos", disgustos);
			    viewModel.put("gustos", gustos);
			    viewModel.put("recetasFavoritas", recetasFavoritas);  	
		    return new ModelAndView(viewModel, "perfilUsuario.hbs");
	}
	 
	 public Usuario buscarUsuario(){
		 ArrayList<Ingrediente> disgustoLean = new ArrayList<Ingrediente>();
		 disgustoLean.add(new Ingrediente("Maracuya"));
		 disgustoLean.add(new Ingrediente("Miel"));
		 
		 ArrayList<Ingrediente> gustoLean = new ArrayList<Ingrediente>();
		 gustoLean.add(new Ingrediente("Queso"));
		 gustoLean.add(new Ingrediente("Cebolla"));
		  
		  Usuario lean = new Usuario((new DatosPersonales("Crash Bandicoot",Sexo.MASCULINO,LocalDate.parse("1993-11-09")))
					,new Complexion(82,1.80)
			, gustoLean
			,disgustoLean
			, new ArrayList<CondicionDeSalud>()
			,Rutina.MEDIANA, "lean.com");
		  
		  lean.agregarAFavorita(new Receta("Ñoquis a la boloñesa", new ArrayList<>(),
					 new ArrayList<>(),
					 new ArrayList<>(), 123.00 , Dificultad.MEDIA));
		  
		 return lean;
	 }
	 
}
