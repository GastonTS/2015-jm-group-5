package ar.edu.grupo5.jm.dss.QueComemos.Main;

import java.time.LocalDate;
import java.util.Arrays;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Condimentacion;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.RecetaBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Dificultad;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta.Temporada;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Recetario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.RepoUsuarios;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.UsuarioBuilder;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;


public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

	public static void main(String[] args) {
		new Bootstrap().run();
	}
	
	public void run() {
		Receta receta = createRecetaExample();
		Usuario usuario = createUsuarioExample();
		new RepoUsuarios(Arrays.asList(usuario));
		Recetario.instancia.crearReceta(receta, usuario);
		usuario.agregarAFavorita(receta);
	}

	public Usuario createUsuarioExample(){

		Usuario lean = new UsuarioBuilder()
			.setNombre("Leandro")
			.setSexo(Sexo.MASCULINO)
			.setFechaDeNacimiento(LocalDate.parse("1993-11-09"))
			.setPeso(82)
			.setEstatura(1.80)
			.agregarDisgustoAlimenticio(new Ingrediente("Maracuya"))
			.agregarDisgustoAlimenticio(new Ingrediente("Miel"))
			.agregarPreferenciaAlimenticia(new Ingrediente("Queso"))
			.agregarPreferenciaAlimenticia(new Ingrediente("Cebolla"))
			.construirUsuario();
		  
		 return lean;
	 }
	
	
	private Receta createRecetaExample() {
		
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
		return receta;
	}
  
}
