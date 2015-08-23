package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;

public class UsuarioBuilder {

	private Complexion complexion;
	private DatosPersonales datosPersonales;
	private Collection<String> preferenciasAlimenticias = new ArrayList<String>();
	private Collection<String> disgustosAlimenticios = new ArrayList<String>();
	private Collection<CondicionDeSalud> condicionesDeSalud = new ArrayList<CondicionDeSalud>();
	private Rutina rutina;

	public UsuarioBuilder setComplexion(Complexion complexion) {
		this.complexion = complexion;
		return this;
	}

	public UsuarioBuilder setDatosPersonales(DatosPersonales datosPersonales) {
		this.datosPersonales = datosPersonales;
		return this;
	}

	public UsuarioBuilder agregarPreferenciaAlimenticia(String unaPreferenciaAlimenticia) {
		preferenciasAlimenticias.add(unaPreferenciaAlimenticia);
		return this;
	}

	public UsuarioBuilder agregarDisgustoAlimenticio(String unDisgustoAlimenticio) {
		disgustosAlimenticios.add(unDisgustoAlimenticio);
		return this;
	}

	public UsuarioBuilder agregarTodosLosDisgustosAlimenticios(Collection<String> unosDisgustosAlimenticios) {
		disgustosAlimenticios.addAll(unosDisgustosAlimenticios);
		return this;
	}
	
	public UsuarioBuilder agregarCondicionesDeSalud(CondicionDeSalud unaCondicionDeSalud) {
		condicionesDeSalud.add(unaCondicionDeSalud);
		return this;
	}

	public UsuarioBuilder setRutina(Rutina rutina) {
		this.rutina = rutina;
		return this;
	}

	public Usuario construirUsuario() {
		Usuario usuario = new Usuario(datosPersonales, complexion, preferenciasAlimenticias, disgustosAlimenticios, condicionesDeSalud, rutina);
		if (!esUsuarioValido(usuario)) {
			throw new UsuarioNoValidoException("El Usuario no es Valido!!!");
		}
		return usuario;
	}

	private boolean esUsuarioValido(Usuario unUsuario) {
		return datosPersonales!= null && complexion != null
				&& rutina != null && esUsuarioValidoParaSusCondiciones(unUsuario);
	}

	private boolean esUsuarioValidoParaSusCondiciones(Usuario unUsuario) {
		return condicionesDeSalud.stream().allMatch(condicion -> condicion.esUsuarioValido(unUsuario));
	}
}
