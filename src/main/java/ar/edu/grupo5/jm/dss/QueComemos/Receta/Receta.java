package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
	@Enumerated
	@Updateable
	private Dificultad dificultad;

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
