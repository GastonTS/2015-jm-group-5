package ar.edu.grupo5.jm.dss.QueComemos.Main;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;






import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import ar.edu.grupo5.jm.dss.QueComemos.Controllers.HomeController;
import ar.edu.grupo5.jm.dss.QueComemos.Controllers.RecetasController;
import ar.edu.grupo5.jm.dss.QueComemos.Controllers.UsuariosController;


public class Routes {

	 
	 public static void main(String[] args) {
		    System.out.println("Iniciando servidor");

		    HomeController home = new HomeController();
		    RecetasController recetas = new RecetasController();
		    UsuariosController usuarios = new UsuariosController();
		    
		    HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

		    port(8090);

		    staticFileLocation("/public");
		    
		    get("/", home::mostrar, engine);
		    get("/index.html", (request, response) -> {
		      response.redirect("/");
		      return null;
		    });
		    
		    
		    after((req, res) -> {
		    	PerThreadEntityManagers.getEntityManager();
		    	PerThreadEntityManagers.closeEntityManager();
		    });

		    get("/recetas", recetas::listar, engine);
		    get("/recetas/show", "application/json", recetas::detalle, engine);
		    get("recetas/edit", recetas::editar, engine);
		    
		    post("/recetas/favorita", recetas::cambiarFavorita);
		    
		    get("/usuarios", usuarios::listar, engine);
		    get("/perfil", usuarios::verPerfil, engine);
		    //FIXME falta el post

		  }

}

