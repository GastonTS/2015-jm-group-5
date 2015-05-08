package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;
import java.util.stream.Collectors;

public class Receta {
	private String nombre;
	private Collection<String> ingredientes;
	private Collection<Condimentacion> condimentos;
	private double cantCalorias;
	private Collection<Receta> subRecetas;

	public Receta(String unNombre, Collection<String> unosIngredientes,
			Collection<Condimentacion> unosCondimentos,
			Collection<Receta> unassubRecetas, double unasCantCalorias) {
		nombre = unNombre;
		ingredientes = unosIngredientes;
		condimentos = unosCondimentos;
		subRecetas = unassubRecetas;
		cantCalorias = unasCantCalorias;

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

	public Collection<Condimentacion> getCondimentos() {
		return condimentos;
	}

	public boolean tenesAlgoDe(Collection<String> condimentosProhibidos) {
		return condimentos.stream().anyMatch(
				condimentacion -> condimentacion
						.tieneCondimentoUnoDe(condimentosProhibidos));
	}

	public boolean tenesMasDe(Condimentacion unCondimento) {
		return condimentos.stream().anyMatch(
				condimentacion -> condimentacion
						.mayorCantidadDeMismoCondimentoQue(unCondimento));
	}

	public boolean tenesAlgunIngredienteDeEstos(
			Collection<String> ingredientesProhibidas) {
		return ingredientesProhibidas.stream().anyMatch(
				ingProhibido -> ingredientes.contains(ingProhibido));
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void agregarSubRecetas(Collection<Receta> unasSubRecetas) {
		subRecetas.addAll(unasSubRecetas);
	}

	public boolean subrecetasIncluye(Receta unaReceta) {
		return subRecetas.contains(unaReceta);
	}

	// Punto 3.b
	public Collection<CondicionDeSalud> condicionesALasQueEsInadecuada() {
		return CondicionDeSalud.condicionesExistentes.stream()
				.filter(condicion -> condicion.esInadecuada(this))
				.collect(Collectors.toList());
	}
}
