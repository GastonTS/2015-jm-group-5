package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class Receta {

	private String nombre;
	private Collection<String> ingredientes;
	private Collection<Condimentacion> condimentaciones;
	private double cantCalorias;
	private Collection<Receta> subRecetas;
	private Optional<Usuario> dueño = Optional.empty();
	private Dificultad dificultad;

	public enum Dificultad {
		BAJA, MEDIA, ALTA
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
		return getCondimentacionesTotales().stream().anyMatch(
				condimentacion -> condimentacion.tieneCondimentoUnoDe(condimentosProhibidos));
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

	private Collection<Condimentacion> getCondimentacionesTotales() {
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
