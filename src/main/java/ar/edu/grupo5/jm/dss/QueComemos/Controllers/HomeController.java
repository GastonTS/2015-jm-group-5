package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
	
	 public ModelAndView mostrar(Request request, Response response) {
		    return new ModelAndView(null, "home.hbs");
		  }


}
