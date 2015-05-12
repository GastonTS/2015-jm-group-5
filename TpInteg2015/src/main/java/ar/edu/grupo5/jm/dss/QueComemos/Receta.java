package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class Receta {
	private Collection<String> ingredientes;
	private Collection<Condimentacion> condimentaciones;
	private double cantCalorias;
	private Collection<Receta> subRecetas;

	public Receta(Collection<String> unosIngredientes,
			Collection<Condimentacion> unasCondimentaciones,
			Collection<Receta> unasSubRecetas, double unasCantCalorias) {
		ingredientes = unosIngredientes;
		condimentaciones = unasCondimentaciones;
		subRecetas = unasSubRecetas;
		cantCalorias = unasCantCalorias;

	}

	public Collection<Receta> getSubRecetas() {
		return subRecetas;
	}

	public boolean esValida() {
		return tieneAlMenosUnIngrediente() && totalCaloriasEntre(10, 5000);
	}

	private boolean tieneAlMenosUnIngrediente() {
		return !ingredientes.isEmpty();
	}

	private boolean totalCaloriasEntre(int minimo, int maximo) {
		return cantCalorias >= minimo && cantCalorias <= maximo;
	}

	public boolean tenesAlgoDe(Collection<String> condimentosProhibidos) {
		return condimentaciones.stream().anyMatch(
				condimentacion -> condimentacion
						.tieneCondimentoUnoDe(condimentosProhibidos));
	}

	public boolean tenesMasDe(Condimentacion unaCondimentacion) {
		return condimentaciones.stream().anyMatch(
				condimentacion -> condimentacion
						.mayorCantidadDeMismoCondimentoQue(unaCondimentacion));
	}

	public boolean tieneAlgunIngredienteDeEstos(
			Collection<String> ingredientesProhibidas) {
		return ingredientesProhibidas.stream().anyMatch(
				ingProhibido -> ingredientes.contains(ingProhibido));
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
