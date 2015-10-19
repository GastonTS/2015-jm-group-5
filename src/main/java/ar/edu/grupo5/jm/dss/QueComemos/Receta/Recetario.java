package ar.edu.grupo5.jm.dss.QueComemos.Receta;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.ConsultorRecetas;
import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectUpdater;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Recetario extends ConsultorRecetas implements ObjectUpdater, WithGlobalEntityManager {

	public static Recetario instancia = new Recetario();
	
	public void setRecetasTotales(Collection<Receta> unasRecetas) {
		unasRecetas.stream().forEach(unaReceta -> entityManager().persist(unaReceta));
	}

	public Collection<Receta> getRecetasTotales() {
		return entityManager().createQuery("FROM Receta", Receta.class).getResultList();
	}
	
	public Receta getReceta(Receta unaReceta){
		return entityManager().find(Receta.class, unaReceta.getId());
	}

	@Override
	public Collection<Receta> getRecetas(Usuario unUsuario) {
		return listarTodasPuedeAcceder(unUsuario);
	}

	//PENSAR COMO HACERLA SIN USAR EL METODO QUE DEVUELVE LA LISTA COMPLETA
	public Collection<Receta> listarTodasPuedeAcceder(Usuario unUsuario) {
		return getRecetasTotales().stream().filter(receta -> unUsuario.puedeAccederA(receta)).collect(Collectors.toSet());
	}

	public void quitarReceta(Receta unaReceta) {
		entityManager().createQuery("DELETE Receta as r WHERE r.recetaId = :idRecetaQuitada")
			.setParameter("idRecetaQuitada", unaReceta.getId())
			.executeUpdate();
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

}
