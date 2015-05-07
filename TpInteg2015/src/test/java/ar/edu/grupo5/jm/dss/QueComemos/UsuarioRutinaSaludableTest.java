package ar.edu.grupo5.jm.dss.QueComemos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class UsuarioRutinaSaludableTest {

	private Usuario juanchi;
	private Usuario usuarioVegano;
	private Usuario usuarioConSobrepeso;
	private Usuario usuarioCeliaco;
	private Usuario usuarioDiabetico;
	private Usuario usuarioDiabeticoRutinaAlata;
	
	private Vegano condicionVegano;
	private Celiaco condicionCeliaco;
	private Diabetico condicionDiabetico;
	
	private Collection<CondicionPreexistente> coleccionCondicionVegano;
	private Collection<CondicionPreexistente> coleccionCondicionCeliaco;
	private Collection<CondicionPreexistente> coleccionCondicionDiabetico;
	
	private Collection<String> preferenciaFruta;
	@Before
	public void setUp() {
		coleccionCondicionVegano = new ArrayList<CondicionPreexistente>();
		coleccionCondicionVegano.add(condicionVegano);
		
		coleccionCondicionCeliaco = new ArrayList<CondicionPreexistente>();
		coleccionCondicionCeliaco.add(condicionCeliaco);
		
		coleccionCondicionDiabetico = new ArrayList<CondicionPreexistente>();
		coleccionCondicionDiabetico.add(condicionDiabetico);
		
		preferenciaFruta = new ArrayList<String>();
		preferenciaFruta.add("fruta");
		
		juanchi = new Usuario(70, 1.85, null, null, null, null, null, null, null);
		usuarioConSobrepeso = new Usuario(120, 1.20, null, null, null, null, null, null, null);
		usuarioVegano = new Usuario(70, 1.85, null, null, preferenciaFruta, null, null, coleccionCondicionVegano, null);
		usuarioCeliaco = new Usuario(70, 1.85, null, null, preferenciaFruta, null, null, coleccionCondicionCeliaco, null);
		usuarioDiabetico = new Usuario(50, 1.20, null, null, null, null, null, coleccionCondicionDiabetico, null);
		usuarioDiabeticoRutinaAlata = new Usuario(71, 1.70, null, null, null, null, null, coleccionCondicionDiabetico, "Alta");
	}
	
	@Test
	public void usuarioEstaEnRangoIMCYNoTieneCondiciones(){
		assert(juanchi.sigueRutinaSaludable());
	}
	
	@Test
	public void usuarioNoCumpleIMC(){
		assertFalse(usuarioConSobrepeso.sigueRutinaSaludable());
	}

	@Test
	public void veganoQueLeGustanLAsFrutas(){
		assert(usuarioVegano.sigueRutinaSaludable());
	}
	
	@Test
	public void celiacoQueLeGustanLAsFrutas(){
		assert(usuarioCeliaco.sigueRutinaSaludable());
	}
	
	@Test
	public void diabeticoCumpleIMCPesaMenosDe70(){
		assert(usuarioDiabetico.sigueRutinaSaludable());
	}
	
	@Test
	public void diabeticoCumpleIMCConRutinaAlta(){
		assert(usuarioDiabeticoRutinaAlata.sigueRutinaSaludable());
	}
}
