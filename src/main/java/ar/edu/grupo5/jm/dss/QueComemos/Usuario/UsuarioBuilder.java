package ar.edu.grupo5.jm.dss.QueComemos.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Ingrediente;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario.Rutina;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.CondicionDeSalud.CondicionDeSalud;

public class UsuarioBuilder {

	private String nombre;
	private Sexo sexo;
	private LocalDate fechaDeNacimiento;

	private double peso;
	private double estatura;

	private DatosPersonales datosPersonales;
	private Complexion complexion;

	private Collection<Ingrediente> preferenciasAlimenticias = new ArrayList<Ingrediente>();
	private Collection<Ingrediente> disgustosAlimenticios = new ArrayList<Ingrediente>();
	private Collection<CondicionDeSalud> condicionesDeSalud = new ArrayList<CondicionDeSalud>();
	private Rutina rutina = Rutina.MEDIANA;

	private String mail;

	public UsuarioBuilder setDatosPersonales(DatosPersonales datosPersonales) {
		this.datosPersonales = datosPersonales;
		return this;
	}

	public UsuarioBuilder setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public UsuarioBuilder setSexo(Sexo sexo) {
		this.sexo = sexo;
		return this;
	}

	public UsuarioBuilder setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
		this.fechaDeNacimiento = fechaDeNacimiento;
		return this;
	}

	public UsuarioBuilder setComplexion(Complexion complexion) {
		this.complexion = complexion;
		return this;
	}

	public UsuarioBuilder setPeso(double peso) {
		this.peso = peso;
		return this;
	}

	public UsuarioBuilder setEstatura(double estatura) {
		this.estatura = estatura;
		return this;
	}

	public UsuarioBuilder agregarPreferenciaAlimenticia(Ingrediente unaPreferenciaAlimenticia) {
		preferenciasAlimenticias.add(unaPreferenciaAlimenticia);
		return this;
	}

	public UsuarioBuilder agregarTodasLasPreferenciasAlimenticias(Collection<Ingrediente> unosDisgustosAlimenticios) {
		disgustosAlimenticios.addAll(unosDisgustosAlimenticios);
		return this;
	}

	public UsuarioBuilder agregarDisgustoAlimenticio(Ingrediente unDisgustoAlimenticio) {
		disgustosAlimenticios.add(unDisgustoAlimenticio);
		return this;
	}

	public UsuarioBuilder agregarTodosLosDisgustosAlimenticios(Collection<Ingrediente> unosDisgustosAlimenticios) {
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

	public UsuarioBuilder setMail(String mail) {
		this.mail = mail;
		return this;
	}

	public Usuario construirUsuario() {
		DatosPersonales datosPersonales = (this.datosPersonales == null) ? new DatosPersonales(nombre, sexo, fechaDeNacimiento)
				: this.datosPersonales;
		Complexion complexion = (this.complexion == null) ? new Complexion(peso, estatura) : this.complexion;
		Usuario usuario = new Usuario(datosPersonales, complexion, preferenciasAlimenticias, disgustosAlimenticios, condicionesDeSalud, rutina,
				mail);
		if (!esUsuarioValido(usuario)) {
			throw new UsuarioNoValidoException("El Usuario no es Valido!!!");
		}
		return usuario;
	}

	private boolean esUsuarioValido(Usuario unUsuario) {
		return rutina != null && esUsuarioValidoParaSusCondiciones(unUsuario);
	}

	private boolean esUsuarioValidoParaSusCondiciones(Usuario unUsuario) {
		return condicionesDeSalud.stream().allMatch(condicion -> condicion.esUsuarioValido(unUsuario));
	}
}
