package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.ConsultorRecetas;
import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectUpdater;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class Recetario extends ConsultorRecetas implements ObjectUpdater, WithGlobalEntityManager {
	
	public static Recetario instancia = new Recetario();
	
	public void setRecetasTotales(Collection<Receta> unasRecetas) {
		unasRecetas.stream().forEach(unaReceta -> entityManager().persist(unaReceta));
	}

	public Collection<Receta> getRecetasTotales() {
		return entityManager().createQuery("FROM Receta", Receta.class).getResultList();
	}
	
	public Receta getReceta(Long unaRecetaId){
		return entityManager().find(Receta.class, unaRecetaId);
	}

	@Override
	public Collection<Receta> getRecetas(Usuario unUsuario) {
		return listarTodasPuedeAcceder(unUsuario);
	}

	//PENSAR COMO HACERLA SIN USAR EL METODO QUE DEVUELVE LA LISTA COMPLETA}
	//XXX para hacer esto tendrian que implementar la consulta en memoria
	public Collection<Receta> listarTodasPuedeAcceder(Usuario unUsuario) {
		return getRecetasTotales().stream().filter(receta -> unUsuario.puedeAccederA(receta)).collect(Collectors.toSet());
	}

	public void quitarReceta(Receta unaReceta) {
		entityManager().remove(unaReceta);
	}

	public void crearReceta(Receta unaReceta, Usuario unUsuario) {
		unaReceta.setDueño(unUsuario);
		entityManager().persist(unaReceta);
	}

	public void crearRecetaConSubRecetas(Receta unaReceta, Collection<Receta> unasSubRecetas, Usuario unUsuario) {
		if (unasSubRecetas.stream().anyMatch(unaSubReceta -> !unUsuario.puedeAccederA(unaSubReceta))) {
			throw new NoPuedeAccederARecetaException("No puede agregar subrecetas a las que no tenga permiso de acceder");
		}
		unaReceta.agregarSubRecetas(unasSubRecetas);
		crearReceta(unaReceta, unUsuario);
	}

	public void eliminarReceta(Receta unaReceta, Usuario unUsuario) {
		if (!unaReceta.esElDueño(unUsuario)) {
			throw new NoPuedeEliminarRecetaExeption("No puede eliminar una receta que no creó");

		}
		unUsuario.quitarRecetaFavorita(unaReceta);
	}

	public void modificarReceta(Receta viejaReceta, Receta nuevaReceta, Usuario unUsuario) {
		if (!unUsuario.puedeAccederA(viejaReceta)) {
			throw new NoPuedeAccederARecetaException("No tiene permiso para acceder a esa receta");
		}

		if (viejaReceta.esElDueño(unUsuario)) {
			this.update(viejaReceta, nuevaReceta);
		} else {
			crearReceta(nuevaReceta, unUsuario);
		}
	}

	public int topeCaloriasPuedeAcceder(Usuario unUsuario) {
		Collection<Receta> recetas = this.listarTodasPuedeAcceder(unUsuario);
		
		Optional<Receta> recetaMasCalorica = recetas.stream().max((receta1, receta2) -> 
				Double.compare(receta1.getCantCaloriasTotales(),
						receta2.getCantCaloriasTotales()));

		if(recetaMasCalorica.isPresent()) {
			return ((int) ((recetaMasCalorica.get().getCantCaloriasTotales() + 10d)/10)) * 10;
		} else {
			return 0;
		}
	}
}
