package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

public class RepoObservadorConsultas implements WithGlobalEntityManager {

	public static RepoObservadorConsultas instancia = new RepoObservadorConsultas();

	public void quitarObservador(ObservadorConsultas unObservador) {
		entityManager().remove(unObservador);
	}

	public void agregarObservador(ObservadorConsultas unObservador) {
		entityManager().persist(unObservador);
	}

	public Collection<ObservadorConsultas> getObservadores() {
		return entityManager().createQuery("FROM ObservadorConsultas", ObservadorConsultas.class).getResultList();
	}

	public void notificarObservadores(Consulta unaConsulta) {
		this.getObservadores().forEach(observador -> observador.notificarConsulta(unaConsulta));
	}

	public <T extends ObservadorConsultas> T getObservador(Class<T> tipoObservador) {
		List<T> observadores = entityManager().createQuery("FROM " + tipoObservador.getName(), tipoObservador).getResultList();
		Optional<T> observador = observadores.stream().findFirst();
	
		try {
			return observador.get();
		} catch (NoSuchElementException error) {
			throw new InstanciaDeObservadorConsultaNoExisteException("El observador de consulta buscado no se encuentra persistido");
		} 
	}
	
}
