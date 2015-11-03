package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class RecetasController{
	
	public ModelAndView mostrar(Request request, Response response){

		return new ModelAndView(null, "recetas.hbs");
	}

    Double minCalorias;
    Double maxCalorias;
    
	public ModelAndView listar(Request request, Response response) {
		    Collection<Receta> recetas = Recetario.instancia.getRecetasTotales();
		    
		    minCalorias = 0.0;
		    maxCalorias = 999999.9;

		    String filtroNombre = request.queryParams("nombre");
		    String filtroDificultad = request.queryParams("dificultad");
		    String filtroMinCalorias = request.queryParams("cantMinCalorias");
		    String filtroMaxCalorias = request.queryParams("cantMaxCalorias");
		    
		    if(!Objects.isNull(filtroMinCalorias) && !filtroMinCalorias.isEmpty())
		    	minCalorias = Double.parseDouble(request.queryParams("cantMinCalorias"));
		    if(!Objects.isNull(filtroMaxCalorias) && !filtroMaxCalorias.isEmpty())
		    	maxCalorias = Double.parseDouble(request.queryParams("cantMaxCalorias"));
		   
		    if (!Objects.isNull(filtroNombre) && !filtroNombre.isEmpty())
		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorNombre(filtroNombre).contains(unaReceta))
		    			.collect(Collectors.toList());
		    
		    if (!Objects.isNull(filtroDificultad) && !filtroDificultad.isEmpty()){

		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorDificultad(filtroDificultad).contains(unaReceta))
		    			.collect(Collectors.toList());
		    }
		    
		    if (!Objects.isNull(filtroMinCalorias) && !Objects.isNull(filtroMaxCalorias))
		    	recetas = recetas.stream().filter(
		    			unaReceta -> Recetario.instancia.filtrarPorRangoCalorias(minCalorias, maxCalorias).contains(unaReceta))
		    			.collect(Collectors.toList());
		    
		    HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("recetas", recetas);
		    viewModel.put("nombre", filtroNombre);
		    viewModel.put("dificultad", filtroDificultad);
		    viewModel.put("cantMinCalorias", filtroMinCalorias);
		    viewModel.put("cantMaxCalorias", filtroMaxCalorias);

		    return new ModelAndView(viewModel, "recetas.hbs");
	}
	
	public ModelAndView detalle (Request request, Response response) {
		
		long id = Long.parseLong(request.params(":id"));
		Receta receta = this.buscarReceta(id);

		Collection<Dificultad> dificultades = Arrays.asList(Dificultad.values())
				.stream().filter(dificultad -> dificultad != receta.getDificultad())
				.collect(Collectors.toList());
		
		Collection<CondicionDeSalud> condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(receta);
		
		HashMap<String, Object> viewModel = new HashMap<>();
		    viewModel.put("receta", receta);
		    viewModel.put("dificultades", dificultades);
		    viewModel.put("publica", receta.esPublica());
		    viewModel.put("autor", receta.getDueño());
		    viewModel.put("favorita", false);
		    viewModel.put("inadecuado", !condicionesInadecuadas.isEmpty());
		    viewModel.put("condicionesInadecuado", condicionesInadecuadas);
		    
		return  new ModelAndView(viewModel, "detalleReceta.hbs");
	}

	private Receta buscarReceta(long idReceta) {
	
		Receta gnoquis = new RecetaBuilder()
		.setCantCalorias(150)
		.setNombre("Ñoquis")
		.agregarIngrediente(new Ingrediente("Papa"))
		.agregarIngrediente(new Ingrediente("Leche"))
		.agregarIngrediente(new Ingrediente("Harina"))
		.setDificultad(Dificultad.MEDIA)
		.agregarCondimentaciones(new Condimentacion("Sal", 50))
		.agregarCondimentaciones(new Condimentacion("Pimienta", 20))
		.agregarCondimentaciones(new Condimentacion("Ajo Molido", 6))
		.construirReceta();

		Receta receta = new RecetaBuilder()
		.setCantCalorias(150)
		.setNombre("Ñoquis a la boloñesa")
		.agregarIngrediente(new Ingrediente("Carne Picada"))
		.agregarIngrediente(new Ingrediente("Salsa de Tomate"))
		.setDificultad(Dificultad.MEDIA)
		.agregarCondimentaciones(new Condimentacion("sal", 100))
		.agregarCondimentaciones(new Condimentacion("Azucar", 200))
		.agregarCondimentaciones(new Condimentacion("Pimienta", 40))
		.agregarCondimentaciones(new Condimentacion("Ajo molido", 10))
		.agregarSubReceta(gnoquis)
		.construirReceta();
		
		Usuario gustavo = new UsuarioBuilder()
			.setEstatura(1.83)
			.setPeso(76)
			.setDatosPersonales(new DatosPersonales("Gustavo",Sexo.MASCULINO,LocalDate.parse("1994-02-25")))
			.construirUsuario();

		receta.setDueño(gustavo);
		return receta;
	}
}