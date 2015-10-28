package ar.edu.grupo5.jm.dss.QueComemos.Main;

import static spark.Spark.get;
//import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;
import spark.template.handlebars.HandlebarsTemplateEngine;
import ar.edu.grupo5.jm.dss.QueComemos.Controllers.HomeController;


public class Routes {

	 public static void main(String[] args) {
		    System.out.println("Iniciando servidor");

		    HomeController home = new HomeController();
		   //ConsultorasController consultoras = new ConsultorasController(); 
		    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

		    port(8090);

		    staticFileLocation("/public");

		    get("/", home::mostrar, engine);
		    get("/index.html", (request, response) -> {
		      response.redirect("/");
		      return null;
		    });
		  /*  get("/consultoras", consultoras::listar, engine);
		    post("/consultoras", consultoras::crear);
		    get("/consultoras/new", consultoras::nuevo, engine);
		    get("/consultoras/:id", consultoras::mostrar, engine);*/

		  }

}
