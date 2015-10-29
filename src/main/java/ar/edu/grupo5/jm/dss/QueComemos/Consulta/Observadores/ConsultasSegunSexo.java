package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ConsultasSegunSexo extends AcumuladorConsultas {
	@ManyToMany
	Collection<Receta> consultasHombres = new ArrayList<Receta>();
	@ManyToMany
	Collection<Receta> consultasMujeres = new ArrayList<Receta>();

	@Override
	public Collection<Receta> getRecetasConsultadas(Usuario unUsuario) {
		return getConsultasSegunSexo(unUsuario.getSexo());
	}

	public Collection<Receta> getConsultasSegunSexo(Sexo unSexo) {
		if (unSexo.equals(Sexo.MASCULINO)) {
			return consultasHombres;
		} else
			return consultasMujeres;
	}

	public int cantidadRecetaMasConsultadaPor(Sexo unSexo) {
		return cantidadDeElementoMasRepetidoEn(getConsultasSegunSexo(unSexo));
	}

	public Optional<Receta> recetaMasConsultadaPor(Sexo unSexo) {
		return elementoQueMasSeRepiteEn(getConsultasSegunSexo(unSexo));
	}

}
