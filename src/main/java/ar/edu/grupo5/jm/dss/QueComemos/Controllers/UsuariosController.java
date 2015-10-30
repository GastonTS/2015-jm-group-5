package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;


public class UsuariosController {
	
	 public ModelAndView listar(Request request, Response response) {
		 Collection<Usuario> listaUsuario = new ArrayList<Usuario>();
		 
		  Usuario lean = new Usuario((new DatosPersonales("leandro",Sexo.MASCULINO,LocalDate.parse("1993-11-09")))
					,new Complexion(70,150)
			, new ArrayList<Ingrediente>()
			,new ArrayList<Ingrediente>()
			, new ArrayList<CondicionDeSalud>()
			,Rutina.MEDIANA, "lean.com");
		   
		  listaUsuario.add(lean);
			
			 HashMap<String, Object> viewModel = new HashMap<>();
			    viewModel.put("usuarios", listaUsuario);
			   	
		    return new ModelAndView(viewModel, "usuarios.hbs");
	}
}
