package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.Arrays;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController  implements WithGlobalEntityManager, TransactionalOps{
	
	int crearRecetas = 0;
	
	public ModelAndView mostrar(Request request, Response response) {

	    //Como todavía no está resuelto lo de la persistencia, lo harcodeo para probar los filtrados
		//Lo sé, por esto van a decir: "Huy que feo, yo purista de la vida no le permitiré, gruño, gruño, gruño!" 
		
		if (crearRecetas == 0){
			
		    crearRecetas = 1;
		    
	    	Ingrediente pimienta = new Ingrediente("pimienta");
		    
		  		
		    		
		    withTransaction(() -> {
			    entityManager().persist(pimienta);
		    });
		    
		    withTransaction(() -> {
		    	Recetario.instancia.setRecetasTotales(Arrays.asList(new RecetaBuilder().setNombre("Receta 1")
			    		.agregarIngrediente(pimienta).setCantCalorias(1000).setDificultad(Dificultad.ALTA).construirReceta()));
		    });
		}
	    
	    return new ModelAndView(null, "home.hbs");
	}


}