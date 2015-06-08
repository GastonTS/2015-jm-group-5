package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.Collection;

import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Celiaco;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Diabetico;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Hipertenso;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Vegano;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecetaInadecuadaTest {

	private Receta recetaMock = mock(Receta.class);

	private Collection<CondicionDeSalud> condicionesInadecuadas;

	@Test
	public void InadecuadaParaDiabeticosSiTieneMasDeCondimentoProhibido() {
		when(recetaMock.tenesMasDe(Diabetico.condimentoProhibido)).thenReturn(true);

		condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(recetaMock);
		assertTrue(condicionesInadecuadas.stream().anyMatch(condicion -> condicion instanceof Diabetico));

		verify(recetaMock, times(1)).tenesMasDe(Diabetico.condimentoProhibido);
	}

	@Test
	public void InadecuadaParaVeganosSiTieneAlgunIngredienteProhibido() {
		when(recetaMock.tieneAlgunIngredienteDeEstos(Vegano.preferenciasProhibidas)).thenReturn(true);

		condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(recetaMock);
		assertTrue(condicionesInadecuadas.stream().anyMatch(condicion -> condicion instanceof Vegano));

		verify(recetaMock, times(1)).tieneAlgunIngredienteDeEstos(Vegano.preferenciasProhibidas);
	}

	@Test
	public void InadecuadaParaHipertensosSiTieneAlgunoDeLosCondimentosProhibidos() {
		when(recetaMock.tenesAlgoDe(Hipertenso.condimentosProhibidos)).thenReturn(true);

		condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(recetaMock);
		assertTrue(condicionesInadecuadas.stream().anyMatch(condicion -> condicion instanceof Hipertenso));

		verify(recetaMock, times(1)).tenesAlgoDe(Hipertenso.condimentosProhibidos);
	}

	@Test
	public void NingunaRecetaEsInadecuadaParaCeliaco() {
		condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(recetaMock);
		assertTrue(condicionesInadecuadas.stream().allMatch(condicion -> !(condicion instanceof Celiaco)));
	}

}
