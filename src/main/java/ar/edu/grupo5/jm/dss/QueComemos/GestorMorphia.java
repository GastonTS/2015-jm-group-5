package ar.edu.grupo5.jm.dss.QueComemos;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

import com.mongodb.MongoClient;

public class GestorMorphia {

	public static GestorMorphia instancia = new GestorMorphia();
	private Datastore datastore;
	Morphia morphia = new Morphia();

	public GestorMorphia() {
		morphia.mapPackage("Usuario");
		morphia.mapPackage("Receta");
		datastore = morphia.createDatastore(new MongoClient(), "QueComemos");
	}

	public Datastore getDatastore() {
		return datastore;
	}
	
	public void actualizarUsuario(Usuario unUsuario){
		unUsuario.getRecetasFavoritas().forEach(unaReceta -> datastore.save(unaReceta));
		datastore.save(unUsuario);
	}
	
	public void actualizarReceta(Receta unaReceta){
		unaReceta.getSubRecetas().forEach(receta -> datastore.save(receta));
		datastore.save(unaReceta);
	}
}
