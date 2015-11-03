package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController  implements WithGlobalEntityManager, TransactionalOps{
	
	
	public ModelAndView mostrar(Request request, Response response) {
	    return new ModelAndView(null, "home.hbs");
	}


}