package ar.edu.grupo5.jm.dss.QueComemos.Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Complexion;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;


public class UsuariosController {
	
	private ArrayList<Usuario> listU = new ArrayList<Usuario>();
	
	 public ModelAndView listar(Request request, Response response) {
		 ArrayList<Usuario>listaUsuario = this.agregar();
		 
			 HashMap<String, Object> viewModel = new HashMap<>();
			    viewModel.put("usuarios", listaUsuario);
			   	
		    return new ModelAndView(viewModel, "usuarios.hbs");
	}
	 
	public ArrayList<Usuario> agregar() {
		
		  Usuario lean = new Usuario((new DatosPersonales("leandro",Sexo.MASCULINO,LocalDate.parse("1993-11-09")))
					,new Complexion(70,150)
			, new ArrayList<Ingrediente>()
			,new ArrayList<Ingrediente>()
			, new ArrayList<CondicionDeSalud>()
			,Rutina.MEDIANA, "lean.com");
		  listU.add(lean);
		  
		return listU;
	}
	
	 public ModelAndView verPerfil(Request request, Response response) {
		 
		 Usuario usuario = this.buscarUsuario();
		 
		 Collection<Ingrediente> disgustos =usuario.getDisgustosAlimenticios();
		 Collection<Ingrediente> gustos = usuario.getPreferenciasAlimenticias();
		 Collection<Receta> recetasFavoritas = usuario.getRecetasFavoritas();
		
			 HashMap<String, Object> viewModel = new HashMap<>();
			    viewModel.put("usuario", usuario);
			    viewModel.put("disgustos", disgustos);
			    viewModel.put("gustos", gustos);
			    viewModel.put("recetasFavoritas", recetasFavoritas);  	
		    return new ModelAndView(viewModel, "perfilUsuario.hbs");
	}
	 
	 public Usuario buscarUsuario(){
		 ArrayList<Ingrediente> disgustoLean = new ArrayList<Ingrediente>();
		 disgustoLean.add(new Ingrediente("Maracuya"));
		 disgustoLean.add(new Ingrediente("Miel"));
		 
		 ArrayList<Ingrediente> gustoLean = new ArrayList<Ingrediente>();
		 gustoLean.add(new Ingrediente("Queso"));
		 gustoLean.add(new Ingrediente("Cebolla"));
		  
		  Usuario lean = new Usuario((new DatosPersonales("Crash Bandicoot",Sexo.MASCULINO,LocalDate.parse("1993-11-09")))
					,new Complexion(82,1.80)
			, gustoLean
			,disgustoLean
			, new ArrayList<CondicionDeSalud>()
			,Rutina.MEDIANA, "lean.com");
		  
			
			String preparacion = "Lorem ipsum dolor sit amet, duo cu animal eripuit moderatius, velit integre complectitur cu vis, nec at viris euismod prodesset. In mea lucilius oportere, phaedrum deserunt atomorum at est. Sea ferri facete legimus cu, elit fierent sed ad, verear referrentur ut duo. Vis te veniam quaerendum, mel ex omnes maiestatis."
					+ "Id vis falli referrentur, elit congue quidam an vim. Vis no numquam fabulas, ei sonet urbanitas constituto eos. Vel ex habeo ipsum verear, mei no quaeque adolescens. Aliquam aliquando vis ne, cu vide graece lobortis cum. Ea est veritus accumsan, qui lucilius aliquando id."
					+ "Cu vix adolescens dissentiet, quas putant laboramus per id, eius vivendum no qui. Ei mea tempor assentior. An invidunt theophrastus usu. Est cu sint partem, vix et populo antiopam prodesset."
					+ "His audiam iuvaret cu, id vis ridens moderatius, ei dictas labores ullamcorper usu. Usu fastidii nominavi te, vel ea modus fuisset theophrastus. Sint efficiantur eos an, unum possim intellegebat est ea. Ne error putant imperdiet eum. Facer scripta quo no."
					+ "Labitur quaestio salutatus id duo, ex nam choro splendide intellegebat, modo adversarium has eu. Mandamus gubergren cu sea, ut tollit virtute iracundia pri, recusabo inciderint ei pro. Graece audire scripserit mea no. An vide idque noster sit. Errem aliquando sea ut.";
			
			
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
			.setTemporada(Temporada.TODOELAÑO)
			.setPreparacion(preparacion)
			.construirReceta();
		  
		  lean.agregarAFavorita(receta);
		  
		 return lean;
	 }
	 
}
