package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.Updateable;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
public class Receta {

	@Id
	ObjectId id;
	@Updateable
	private String nombre;
	@Updateable
	private Collection<String> ingredientes;
	@Updateable
	private Collection<Condimentacion> condimentaciones;
	@Updateable
	private double cantCalorias;
	@Updateable
	private Collection<Receta> subRecetas;
	private Optional<Usuario> dueño = Optional.empty();
	@Updateable
	private Dificultad dificultad;

	public enum Dificultad {
		BAJA, MEDIA, ALTA
	}

	public Receta() {

	}

	public Receta(String nombreReceta, Collection<String> unosIngredientes, Collection<Condimentacion> unasCondimentaciones,
			Collection<Receta> unasSubRecetas, double unasCantCalorias, Dificultad unaDificultad) {

		nombre = nombreReceta;
		ingredientes = unosIngredientes;
		condimentaciones = unasCondimentaciones;
		subRecetas = unasSubRecetas;
		cantCalorias = unasCantCalorias;
		dificultad = unaDificultad;
	}

	public Object getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Collection<Receta> getSubRecetas() {
		return subRecetas;
	}

	public void setDueño(Usuario unUsuario) {
		dueño = Optional.of(unUsuario);
	}

	public boolean esElDueño(Usuario unUsuario) {
		return dueño.isPresent() && dueño.get().equals(unUsuario);
	}

	public boolean esPublica() {
		return !dueño.isPresent();
	}

	public boolean tenesAlgoDe(Collection<String> condimentosProhibidos) {
		return getCondimentacionesTotales().stream().anyMatch(condimentacion -> condimentacion.tieneCondimentoUnoDe(condimentosProhibidos));
	}

	public boolean tenesMasDe(Condimentacion unaCondimentacion) {
		return getCondimentacionesTotales().stream().anyMatch(
				condimentacion -> condimentacion.mayorCantidadDeMismoCondimentoQue(unaCondimentacion));
	}

	public boolean tenesAlgunIngredienteDeEstos(Collection<String> ingredientesProhibidas) {
		return getIngredientesTotales().stream().anyMatch(ingrediente -> ingredientesProhibidas.contains(ingrediente));
	}

	private double getCantCaloriasSubRecetas() {
		return subRecetas.stream().mapToDouble(subReceta -> subReceta.getCantCaloriasTotales()).sum();
	}

	public double getCantCaloriasTotales() {
		return cantCalorias + getCantCaloriasSubRecetas();
	}

	private Stream<String> getIngredientesSubRecetas() {
		return subRecetas.stream().flatMap(subReceta -> subReceta.getIngredientesTotales().stream());
	}

	public Collection<String> getIngredientesTotales() {
		return Stream.concat(ingredientes.stream(), getIngredientesSubRecetas()).collect(Collectors.toList());
	}

	private Stream<Condimentacion> getCondimentacionesSubRecetas() {
		return subRecetas.stream().flatMap(subReceta -> subReceta.getCondimentacionesTotales().stream());
	}

	public Collection<Condimentacion> getCondimentacionesTotales() {
		return Stream.concat(condimentaciones.stream(), getCondimentacionesSubRecetas()).collect(Collectors.toList());
	}

	public void agregarSubRecetas(Collection<Receta> unasSubRecetas) {
		subRecetas.addAll(unasSubRecetas);
	}

	public boolean esDificil() {
		return dificultad == Dificultad.ALTA;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

}
