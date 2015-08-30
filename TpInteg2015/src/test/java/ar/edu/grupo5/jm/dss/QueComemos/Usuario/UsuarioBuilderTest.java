package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class UsuarioBuilderTest {

	private DatosPersonales datosPersonalesMock = mock(DatosPersonales.class);

	private Complexion complexionMock = mock(Complexion.class);

	private CondicionDeSalud hippie = mock(CondicionDeSalud.class);
	private CondicionDeSalud corporativo = mock(CondicionDeSalud.class);
	
	@Test
	public void esValidoSiTieneDatosPersonalesComplexionCumpleConSusCondicionesYTieneRutinaValida() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);

		new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.agregarCondicionesDeSalud(hippie)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
	}
	
	@Test(expected = UsuarioNoValidoException.class)
	public void noEsValidoSiNoTieneDatosPersonales() {
		new UsuarioBuilder()
				.setComplexion(complexionMock)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
	}
	
	@Test(expected = UsuarioNoValidoException.class)
	public void noEsValidoSiNoTieneComplexion() {
		new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
	}


	@Test(expected = UsuarioNoValidoException.class)
	public void noEsValidoSiSusCondicionesNoLoPermiten() {
		when(hippie.esUsuarioValido(any(Usuario.class))).thenReturn(true);
		when(corporativo.esUsuarioValido(any(Usuario.class))).thenReturn(false);

		new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.agregarCondicionesDeSalud(hippie)
				.agregarCondicionesDeSalud(corporativo)
				.setRutina(Rutina.MEDIANA)
				.construirUsuario();
	}

	@Test(expected = UsuarioNoValidoException.class)
	public void siNoTieneRutinaEsInvalidos() {
		new UsuarioBuilder()
				.setDatosPersonales(datosPersonalesMock)
				.setComplexion(complexionMock)
				.construirUsuario();
	}
	
}
