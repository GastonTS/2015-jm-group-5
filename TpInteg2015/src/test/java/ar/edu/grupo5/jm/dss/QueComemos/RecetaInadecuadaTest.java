package ar.edu.grupo5.jm.dss.QueComemos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecetaInadecuadaTest {

	private Receta recetaParaNoVeganos;
	private Receta recetaParaTodos;
	private Receta recetaParaNoDiabeticos;
	private Receta recetaParaNoHipertensos;

	private Condimentacion sal = new Condimentacion("sal", 200);
	private Condimentacion mayonesa = new Condimentacion("mayonesa", 200);;
	private Condimentacion azucar = new Condimentacion("Azucar", 300);

	private Collection<CondicionDeSalud> condicionesInadecuadas;

	@Before
	public void setUp() {
		recetaParaNoVeganos = new Receta("No Veganos", Arrays.asList("chori", "lechuga"),
				Arrays.asList(mayonesa), null, 0);
		recetaParaTodos = new Receta("Para Todos y Todas", Arrays.asList("lechuga"),
				Arrays.asList(mayonesa), null, 0);
		recetaParaNoDiabeticos = new Receta("No Diabeticos", Arrays.asList("lechuga"),
				Arrays.asList(mayonesa, azucar), null, 0);
		recetaParaNoHipertensos = new Receta("No Hipertensos", Arrays.asList("lechuga"),
				Arrays.asList(mayonesa, sal), null, 0);
	}

	@Test
	public void recetaParaNoDiabeticosInadecuadaSoloParaDiabeticos() {
		Collection<CondicionDeSalud> coleccionConDiabetico;
		coleccionConDiabetico = CondicionDeSalud.condicionesExistentes.stream()
				.filter(condicion -> condicion.getClass() == Diabetico.class)
				.collect(Collectors.toList());

		condicionesInadecuadas = recetaParaNoDiabeticos
				.condicionesALasQueEsInadecuada();
		assertEquals(coleccionConDiabetico, condicionesInadecuadas);
	}

	@Test
	public void recetaParaNoVeganosInadecuadaSoloParaVeganos() {
		Collection<CondicionDeSalud> condicionesConVegano;
		condicionesConVegano = CondicionDeSalud.condicionesExistentes.stream()
				.filter(condicion -> condicion.getClass() == Vegano.class)
				.collect(Collectors.toList());

		condicionesInadecuadas = recetaParaNoVeganos
				.condicionesALasQueEsInadecuada();
		assertEquals(condicionesConVegano, condicionesInadecuadas);
	}

	@Test
	public void recetaParaNoHipertensosInadecuadaSoloParaHipertensos() {
		Collection<CondicionDeSalud> condicionesConHipertenso;
		condicionesConHipertenso = CondicionDeSalud.condicionesExistentes
				.stream()
				.filter(condicion -> condicion.getClass() == Hipertenso.class)
				.collect(Collectors.toList());

		condicionesInadecuadas = recetaParaNoHipertensos
				.condicionesALasQueEsInadecuada();
		assertEquals(condicionesConHipertenso, condicionesInadecuadas);
	}

	@Test
	public void recetaParaTodosInadecuadaParaNadie() {
		Collection<CondicionDeSalud> condicionesVacia = new ArrayList<CondicionDeSalud>();

		condicionesInadecuadas = recetaParaTodos
				.condicionesALasQueEsInadecuada();
		assertEquals(condicionesVacia, condicionesInadecuadas);
	}

}
