package ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdaterTest;

import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectUpdater;

public class RepoHumanos implements ObjectUpdater {
	
	public Collection<Humano> humanos;
	
	public RepoHumanos(Collection<Humano> unosHumanos) {
			humanos = unosHumanos;
	}

}
