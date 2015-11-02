package ar.edu.grupo5.jm.dss.QueComemos.Main;

import java.util.Arrays;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Vegano;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  public static void main(String[] args) {
    new Bootstrap().run();
  }

  public void run() {
    	
    	Vegano asd = new Vegano();
    	
    	persist(asd);
    	
    	
//    	Ingrediente pimienta = new Ingrediente("pimienta");
//	    
//	    entityManager().persist(pimienta);
//    
//    	Recetario.instancia.setRecetasTotales(Arrays.asList(new RecetaBuilder().setNombre("Receta 1")
//	    		.agregarIngrediente(pimienta).setCantCalorias(1000).setDificultad(Dificultad.ALTA).construirReceta()));
  }

}
