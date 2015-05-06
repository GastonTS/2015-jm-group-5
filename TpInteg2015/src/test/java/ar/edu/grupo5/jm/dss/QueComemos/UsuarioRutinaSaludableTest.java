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
	
	private Vegano condicionVegano;
	
	private Collection<CondicionPreexistente> coleccionCondicionVegano;
	
	private Collection<String> preferenciaFruta;
	@Before
	public void setUp() {
		coleccionCondicionVegano = new ArrayList<CondicionPreexistente>();
		coleccionCondicionVegano.add(condicionVegano);
		
		preferenciaFruta = new ArrayList<String>();
		preferenciaFruta.add("fruta");
		
		juanchi = new Usuario(70, 1.85, null, null, null, null, null, null, null);
		usuarioConSobrepeso = new Usuario(120, 1.20, null, null, null, null, null, null, null);
		usuarioVegano = new Usuario(70, 1.85, null, null, preferenciaFruta, null, null, coleccionCondicionVegano, null);
		usuarioCeliaco = new Usuario(70, 1.85, null, null, preferenciaFruta, null, null, coleccionCondicionVegano, null);
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
}
