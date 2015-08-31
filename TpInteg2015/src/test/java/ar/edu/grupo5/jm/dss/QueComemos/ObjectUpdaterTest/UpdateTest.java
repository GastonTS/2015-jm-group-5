package ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdaterTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectIsFromADifferentClass;
import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.PropertyFailInSetAccessible;


public class UpdateTest{
	ArrayList<Humano> humanos = new ArrayList<Humano>();
	RepoHumanos repoHumanos;
	
	@Test
	public void actualizandoObjetoCorrectamente() {
		HumanoTesteableFeliz humanoFelizViejo = new HumanoTesteableFeliz();
		HumanoTesteableFeliz humanoFelizNuevo = new HumanoTesteableFeliz();
		
		humanoFelizViejo.setEdad(10);
		humanoFelizNuevo.setEdad(11);
		humanoFelizViejo.setNombre("pedrito");
		humanoFelizNuevo.setNombre("Pedro");
		
		humanos.add(humanoFelizViejo);
		repoHumanos = new RepoHumanos(humanos);
		repoHumanos.update(humanoFelizViejo, humanoFelizNuevo);
		
		assertEquals(humanoFelizViejo.getEdad(),11);
		assertEquals(humanoFelizViejo.getNombre(),"Pedro");
	}
	@Ignore//Aca deberia hacer un securityManager para prohibir el setAccesible pero fiaca
	@Test(expected = PropertyFailInSetAccessible.class)
	public void actualizandoObjetoConDiferenteModifierEnSetterYGetter() {
		HumanoTesteableConSetEdadPrivado humanoViejo = new HumanoTesteableConSetEdadPrivado();
		HumanoTesteableConSetEdadPrivado humanoNuevo = new HumanoTesteableConSetEdadPrivado();
		
		humanoViejo.setNombre("pedrito");
		humanoNuevo.setNombre("Pedro");
		humanoViejo.ajustarEdad(10);
		humanoNuevo.ajustarEdad(11);

		humanos.add(humanoViejo);
		repoHumanos = new RepoHumanos(humanos);
		repoHumanos.update(humanoViejo, humanoNuevo);

		
	}
	
	@Test(expected = ObjectIsFromADifferentClass.class)
	public void actualizandoObjetoConOtroDeOtraClase() {
		HumanoTesteableConSetEdadPrivado humanoViejo = new HumanoTesteableConSetEdadPrivado();
		HumanoTesteableFeliz humanoNuevo = new HumanoTesteableFeliz();
		
		humanoViejo.setNombre("pedrito");
		humanoNuevo.setNombre("Pedro");
		humanoViejo.ajustarEdad(10);
		humanoNuevo.setEdad(11);


		humanos.add(humanoViejo);
		repoHumanos = new RepoHumanos(humanos);
		repoHumanos.update(humanoViejo, humanoNuevo);

		
	}
	
	public void actualizandoObjetoConUnAtributoEnNull() {
		HumanoTesteableFeliz humanoViejo = new HumanoTesteableFeliz();
		HumanoTesteableFeliz humanoNuevo = new HumanoTesteableFeliz();
		
		humanoViejo.setNombre("pedrito");
		humanoNuevo.setNombre("Pedro");
		humanoViejo.setEdad(10);


		humanos.add(humanoViejo);
		repoHumanos = new RepoHumanos(humanos);
		repoHumanos.update(humanoViejo, humanoNuevo);

		assertEquals(humanoViejo.getEdad(),10);
		assertEquals(humanoViejo.getNombre(),"Pedro");
		
	}
	
	@Test
	public void ObjetosSinActualizarCorrectamente() {
		HumanoTesteableFeliz humanoFelizViejo = new HumanoTesteableFeliz();
		HumanoTesteableFeliz humanoFelizNuevo = new HumanoTesteableFeliz();
		
		humanoFelizViejo.setEdad(10);
		humanoFelizNuevo.setEdad(11);
		humanoFelizViejo.setNombre("pedrito");
		humanoFelizNuevo.setNombre("Pedro");
		
		humanos.add(humanoFelizViejo);
		repoHumanos = new RepoHumanos(humanos);
		
		assertFalse(repoHumanos.sonObjetosActualizados(humanoFelizViejo, humanoFelizNuevo));
		repoHumanos.update(humanoFelizViejo, humanoFelizNuevo);
		assertTrue(repoHumanos.sonObjetosActualizados(humanoFelizViejo, humanoFelizNuevo));
		
	}


}
