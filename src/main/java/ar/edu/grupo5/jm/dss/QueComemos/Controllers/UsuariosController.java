package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;


public class UsuariosController {

	 public ModelAndView listar(Request request, Response response) {
		   
		    return new ModelAndView(null, "usuarios.hbs");
	}
}
