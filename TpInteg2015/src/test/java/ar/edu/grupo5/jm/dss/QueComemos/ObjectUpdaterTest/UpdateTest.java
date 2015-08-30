package ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdaterTest;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.ObjectIsFromADifferentClass;
import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.SetterIsMissingException;
import ar.edu.grupo5.jm.dss.QueComemos.ObjectUpdater.SetterIsNotPublicException;


public class UpdateTest{
	
	@Test
	public void actualizandoObjetoCorrectamente() {
		HumanoTesteableFeliz humanoFelizViejo = new HumanoTesteableFeliz();
		HumanoTesteableFeliz humanoFelizNuevo = new HumanoTesteableFeliz();
		
		humanoFelizViejo.setEdad(10);
		humanoFelizNuevo.setEdad(11);
		humanoFelizViejo.setNombre("pedrito");
		humanoFelizNuevo.setNombre("Pedro");

		humanoFelizViejo.update(humanoFelizNuevo);
		
		assertEquals(humanoFelizViejo.getEdad(),11);
		assertEquals(humanoFelizViejo.getNombre(),"Pedro");
	}

	@Test(expected = SetterIsMissingException.class)
	public void actualizandoObjetoSinUnSetter() {
		HumanoTesteableSinSetterEdad humanoViejo = new HumanoTesteableSinSetterEdad();
		HumanoTesteableSinSetterEdad humanoNuevo = new HumanoTesteableSinSetterEdad();
		
		humanoViejo.setNombre("pedrito");
		humanoNuevo.setNombre("Pedro");
		humanoViejo.ajustarEdad(10);
		humanoNuevo.ajustarEdad(11);

		humanoViejo.update(humanoNuevo);
		
	}
	
	@Ignore
	@Test(expected = SetterIsNotPublicException.class)
	public void actualizandoObjetoConDiferenteModifierEnSetterYGetter() {
		HumanoTesteableConSetEdadPrivado humanoViejo = new HumanoTesteableConSetEdadPrivado();
		HumanoTesteableConSetEdadPrivado humanoNuevo = new HumanoTesteableConSetEdadPrivado();
		
		humanoViejo.setNombre("pedrito");
		humanoNuevo.setNombre("Pedro");
		humanoViejo.ajustarEdad(10);
		humanoNuevo.ajustarEdad(11);

		humanoViejo.update(humanoNuevo);
		
	}
	
	@Test(expected = ObjectIsFromADifferentClass.class)
	public void actualizandoObjetoConOtroDeOtraClase() {
		HumanoTesteableConSetEdadPrivado humanoViejo = new HumanoTesteableConSetEdadPrivado();
		HumanoTesteableFeliz humanoNuevo = new HumanoTesteableFeliz();
		
		humanoViejo.setNombre("pedrito");
		humanoNuevo.setNombre("Pedro");
		humanoViejo.ajustarEdad(10);
		humanoNuevo.setEdad(11);

		humanoViejo.update(humanoNuevo);
		
	}

}
