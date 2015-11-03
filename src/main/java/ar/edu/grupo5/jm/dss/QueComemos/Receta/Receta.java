package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.Updateable;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class Receta {

	@Id
	@GeneratedValue
	private Long recetaId;
	
	@Updateable
	private String nombre;
	@ManyToMany(cascade = CascadeType.ALL)
	@Updateable
	private Collection<Ingrediente> ingredientes;
	@ManyToMany(cascade = CascadeType.ALL)
	@Updateable
	private Collection<Condimentacion> condimentaciones;
	@Updateable
	private double cantCalorias;
	@ManyToMany(cascade = CascadeType.ALL)
	@Updateable
	private Collection<Receta> subRecetas;
	@ManyToOne
	private Usuario dueño;
	@Enumerated(EnumType.ORDINAL)
	@Updateable
	private Dificultad dificultad;
	@Transient
	@Updateable
	private String preparacion = "Lorem ipsum dolor sit amet, duo cu animal eripuit moderatius, velit integre complectitur cu vis, nec at viris euismod prodesset. In mea lucilius oportere, phaedrum deserunt atomorum at est. Sea ferri facete legimus cu, elit fierent sed ad, verear referrentur ut duo. Vis te veniam quaerendum, mel ex omnes maiestatis."
	+ "Id vis falli referrentur, elit congue quidam an vim. Vis no numquam fabulas, ei sonet urbanitas constituto eos. Vel ex habeo ipsum verear, mei no quaeque adolescens. Aliquam aliquando vis ne, cu vide graece lobortis cum. Ea est veritus accumsan, qui lucilius aliquando id."
	+ "Cu vix adolescens dissentiet, quas putant laboramus per id, eius vivendum no qui. Ei mea tempor assentior. An invidunt theophrastus usu. Est cu sint partem, vix et populo antiopam prodesset."
	+ "His audiam iuvaret cu, id vis ridens moderatius, ei dictas labores ullamcorper usu. Usu fastidii nominavi te, vel ea modus fuisset theophrastus. Sint efficiantur eos an, unum possim intellegebat est ea. Ne error putant imperdiet eum. Facer scripta quo no."
	+ "Labitur quaestio salutatus id duo, ex nam choro splendide intellegebat, modo adversarium has eu. Mandamus gubergren cu sea, ut tollit virtute iracundia pri, recusabo inciderint ei pro. Graece audire scripserit mea no. An vide idque noster sit. Errem aliquando sea ut.";

	public enum Dificultad {
		BAJA, MEDIA, ALTA
	}

	public Receta() {

	}

	public Receta(String nombreReceta, Collection<Ingrediente> unosIngredientes, Collection<Condimentacion> unasCondimentaciones,
			Collection<Receta> unasSubRecetas, double unasCantCalorias, Dificultad unaDificultad) {

		nombre = nombreReceta;
		ingredientes = unosIngredientes;
		condimentaciones = unasCondimentaciones;
		subRecetas = unasSubRecetas;
		cantCalorias = unasCantCalorias;
		dificultad = unaDificultad;
	}

	public void setDueño(Usuario unUsuario) {
		dueño = unUsuario;
	}

	public Long getId() {
		return recetaId;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getPreparacion() {
		return preparacion;
	}

	private Stream<Ingrediente> getIngredientesSubRecetas() {
		return subRecetas.stream().flatMap(subReceta -> subReceta.getIngredientesTotales().stream());
	}

	public Collection<Ingrediente> getIngredientesTotales() {
		return Stream.concat(ingredientes.stream(), getIngredientesSubRecetas()).collect(Collectors.toList());
	}

	private Stream<Condimentacion> getCondimentacionesSubRecetas() {
		return subRecetas.stream().flatMap(subReceta -> subReceta.getCondimentacionesTotales().stream());
	}

	public Collection<Condimentacion> getCondimentacionesTotales() {
		return Stream.concat(condimentaciones.stream(), getCondimentacionesSubRecetas()).collect(Collectors.toList());
	}

	private double getCantCaloriasSubRecetas() {
		return subRecetas.stream().mapToDouble(subReceta -> subReceta.getCantCaloriasTotales()).sum();
	}

	public double getCantCaloriasTotales() {
		return cantCalorias + getCantCaloriasSubRecetas();
	}

	public Collection<Receta> getSubRecetas() {
		return subRecetas;
	}

	public Usuario getDueño() {
		return dueño;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public boolean esElDueño(Usuario unUsuario) {
		return dueño != null && dueño.equals(unUsuario);
	}

	public boolean esPublica() {
		return getDueño() == null;
	}

	public boolean esDificil() {
		return dificultad == Dificultad.ALTA;
	}

	public boolean tenesAlgoDe(Collection<String> condimentosProhibidos) {
		return getCondimentacionesTotales().stream().anyMatch(condimentacion -> condimentacion.tieneCondimentoUnoDe(condimentosProhibidos));
	}

	public boolean tenesMasDe(Condimentacion unaCondimentacion) {
		return getCondimentacionesTotales().stream().anyMatch(
				condimentacion -> condimentacion.mayorCantidadDeMismoCondimentoQue(unaCondimentacion));
	}

	public boolean tenesAlgunIngredienteDeEstos(Collection<Ingrediente> ingredientesProhibidas) {
		return getIngredientesTotales().stream().anyMatch(ingrediente -> ingredientesProhibidas.contains(ingrediente));
	}

	public void agregarSubRecetas(Collection<Receta> unasSubRecetas) {
		subRecetas.addAll(unasSubRecetas);
	}

}
