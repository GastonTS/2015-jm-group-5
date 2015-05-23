package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Receta {
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	private Collection<String> ingredientes;
	private Collection<Condimentacion> condimentaciones;
	private double cantCalorias;

	public double getCantCalorias() {
		return cantCalorias;
	}

	private Collection<Receta> subRecetas;

	public Receta(String nombreReceta, Collection<String> unosIngredientes,
			Collection<Condimentacion> unasCondimentaciones,
			Collection<Receta> unasSubRecetas, double unasCantCalorias) {
		if (nombreReceta != null) {
			nombre = nombreReceta;
		} else {
			nombre = "";
		}

		if (unosIngredientes != null) {
			ingredientes = unosIngredientes;
		} else {
			ingredientes = new ArrayList<String>();
		}

		if (unasCondimentaciones != null) {
			condimentaciones = unasCondimentaciones;
		} else {
			condimentaciones = new ArrayList<Condimentacion>();
		}

		if (unasSubRecetas != null) {
			subRecetas = unasSubRecetas;
		} else {
			subRecetas = new ArrayList<Receta>();
		}

		cantCalorias = unasCantCalorias;

	}

	public Collection<Receta> getSubRecetas() {
		return subRecetas;
	}

	public boolean esValida() {
		return tieneAlMenosUnIngrediente() && totalCaloriasEntre(10, 5000);
	}

	private boolean tieneAlMenosUnIngrediente() {
		return !getIngredientesTotales().isEmpty();
	}

	private boolean totalCaloriasEntre(int minimo, int maximo) {
		return cantCalorias >= minimo && cantCalorias <= maximo;
	}

	public boolean tenesAlgoDe(Collection<String> condimentosProhibidos) {
		return getCondimentacionesTotales().stream().anyMatch(
				condimentacion -> condimentacion
						.tieneCondimentoUnoDe(condimentosProhibidos));
	}

	public boolean tenesMasDe(Condimentacion unaCondimentacion) {
		return getCondimentacionesTotales().stream().anyMatch(
				condimentacion -> condimentacion
						.mayorCantidadDeMismoCondimentoQue(unaCondimentacion));
	}

	public boolean tieneAlgunIngredienteDeEstos(
			Collection<String> ingredientesProhibidas) {

		return getIngredientesTotales().stream().anyMatch(
				ingrediente -> ingredientesProhibidas.contains(ingrediente));
	}

	private Collection<String> getIngredientesTotales() {
		Collection<String> auxIngredientes = new ArrayList<String>();
		subRecetas.stream().forEach(
				subReceta -> auxIngredientes.addAll(subReceta
						.getIngredientesTotales()));
		auxIngredientes.addAll(ingredientes);
		return auxIngredientes;
	}

	private Collection<Condimentacion> getCondimentacionesTotales() {
		Collection<Condimentacion> auxCondimentaciones = new ArrayList<Condimentacion>();
		subRecetas.stream().forEach(
				subReceta -> auxCondimentaciones.addAll(subReceta
						.getCondimentacionesTotales()));
		auxCondimentaciones.addAll(condimentaciones);
		return auxCondimentaciones;
	}

	public void agregarSubRecetas(Collection<Receta> unasSubRecetas) {
		subRecetas.addAll(unasSubRecetas);
	}

	// Punto 3.b
	public Collection<CondicionDeSalud> condicionesALasQueEsInadecuada() {
		return CondicionDeSalud.condicionesExistentes.stream()
				.filter(condicion -> condicion.esInadecuada(this))
				.collect(Collectors.toList());
	}
}
