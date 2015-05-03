package ar.edu.grupo5.jm.dss.QueComemos;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class ClasesPreexistentesTest {
	
	 private Usuario usuarioFalso;
     private Vegano vegano;
     private Hipertenso hipertenso;
     private Diabetico diabetico;
	
     @Before
	public void setUp() {
	usuarioFalso = mock(Usuario.class);	
	
	}
	
	@Test
	public void UsuarioSubsanaCondicionVegano() {
		when(usuarioFalso.tienePreferencia("fruta")).thenReturn(true);
		assert (vegano.subsanaCondicion(usuarioFalso));
	}

	@Test
	public void UsuarioSubsanaCondicionHipertenso() {
		when(usuarioFalso.tieneRutinaIntensiva()).thenReturn(true);
		assert (hipertenso.subsanaCondicion(usuarioFalso));
	}
	
	@Test
	public void UsuarioSubsanaCondicionDiabetico() {
		when(usuarioFalso.getPeso()).thenReturn(65.00);
		assert (diabetico.subsanaCondicion(usuarioFalso));
	}
	
	@Test
	public void UsuarioHipertensoValido() {
		when(usuarioFalso.tieneAlgunaPreferencia()).thenReturn(true);
		assert (hipertenso.esUsuarioValido(usuarioFalso));
	}
	
	@Test
	public void UsuarioDiabeticoValido() {
		when(usuarioFalso.indicaSexo()).thenReturn(true);
		when(usuarioFalso.tieneAlgunaPreferencia()).thenReturn(true);
		assert (diabetico.esUsuarioValido(usuarioFalso));
	}
}
