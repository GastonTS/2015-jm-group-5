package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.Collection;
import java.util.List;
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

	public <TipoObservador> Optional<TipoObservador> getObservador(String discriminante, Class<TipoObservador> tipo) {
		List<TipoObservador> observadores = entityManager().createQuery("FROM ObservadorConsultas WHERE DTYPE = :discriminante", tipo)
				.setParameter("discriminante", discriminante).getResultList();

		return (observadores.isEmpty()) ? Optional.empty() : Optional.of(observadores.get(0));
	}
}
