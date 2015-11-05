package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ConsultadasPorUsuario;
import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores.ConsultasTotales;
import ar.edu.grupo5.jm.dss.QueComemos.Main.Bootstrap;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController implements WithGlobalEntityManager, TransactionalOps {

	public ModelAndView mostrar(Request request, Response response) {

		Usuario currentUser = new Bootstrap().currentUserHARDCODE();

		List<Receta> recetas = currentUser.getRecetasFavoritas().stream().sorted((r1, r2) -> Long.compare(r2.getId(), r1.getId())).limit(10)
				.collect(Collectors.toList());

		HashMap<String, Object> viewModel = new HashMap<>();

		if (!recetas.isEmpty()) {
			
			viewModel.put("listadoTitulo", "Ultimas Recetas marcadas como Favoritas.");
		} else {
			recetas = ConsultadasPorUsuario.instancia().masConsultadasEnOrden(currentUser);
			if(!recetas.isEmpty()) {
				viewModel.put("listadoTitulo", "Recetas más consultadas por vos.");
			} else {
				recetas = ConsultasTotales.instancia().masConsultadasEnOrden().stream().limit(10).collect(Collectors.toList());
				viewModel.put("listadoTitulo", "Recetas más consultadas a nivel global.");
			}
		}
		viewModel.put("recetas", recetas);

		return new ModelAndView(viewModel, "home.hbs");
	}
}