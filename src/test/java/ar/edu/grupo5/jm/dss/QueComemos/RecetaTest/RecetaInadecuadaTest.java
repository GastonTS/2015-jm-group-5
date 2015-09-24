package ar.edu.grupo5.jm.dss.QueComemos.RecetaTest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Celiaco;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Diabetico;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Hipertenso;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.Vegano;

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
		when(recetaMock.tenesAlgunIngredienteDeEstos(Vegano.preferenciasProhibidas)).thenReturn(true);

		condicionesInadecuadas = CondicionDeSalud.condicionesALasQueEsInadecuada(recetaMock);
		assertTrue(condicionesInadecuadas.stream().anyMatch(condicion -> condicion instanceof Vegano));

		verify(recetaMock, times(1)).tenesAlgunIngredienteDeEstos(Vegano.preferenciasProhibidas);
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
