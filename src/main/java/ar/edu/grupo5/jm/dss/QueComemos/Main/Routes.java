package ar.edu.grupo5.jm.dss.QueComemos.Main;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;


import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

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
		    get("/recetas/:idReceta/show", recetas::detalle, engine);
		    get("recetas/:idReceta/edit", recetas::editar, engine);
		    
		    post("/recetas/:idReceta/favorita", recetas::cambiarFavorita);
		    
		    post("/recetas/:idReceta/editDatosBasicos", recetas::editarBasicos);
		    
		    post("/recetas/:idReceta/agregarIngrediente", recetas::agregarIngrediente);
		    post("/recetas/:idReceta/quitarIngrediente", recetas::quitarIngrediente);
		    
		    post("/recetas/:idReceta/agregarCondimentacion", recetas::agregarCondimentacion);
		    post("/recetas/:idReceta/quitarCondimentacion", recetas::quitarCondimentacion);
		    
		    post("/recetas/:idReceta/agregarSubReceta", recetas::agregarSubReceta);
		    post("/recetas/:idReceta/quitarSubReceta", recetas::quitarSubReceta);
		    
		    get("/usuarios", usuarios::listar, engine);
		    get("/perfil", usuarios::verPerfil, engine);

		  }

}

