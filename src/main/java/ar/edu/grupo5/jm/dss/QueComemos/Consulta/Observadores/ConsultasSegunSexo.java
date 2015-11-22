package ar.edu.grupo5.jm.dss.QueComemos.Consulta.Observadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;
import ar.edu.grupo5.jm.dss.QueComemos.Receta.Receta;
import ar.edu.grupo5.jm.dss.QueComemos.Usuario.DatosPersonales.Sexo;

@Entity
public class ConsultasSegunSexo extends AcumuladorConsultas {
	@ManyToMany @JoinTable(name = "RecetasHombresXAcumulador")
	Collection<Receta> consultasHombres = new ArrayList<Receta>();
	@ManyToMany @JoinTable(name = "RecetasMujeresXAcumulador")
	Collection<Receta> consultasMujeres = new ArrayList<Receta>();

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

	public void notificarConsulta(Consulta unaConsulta) {
		this.getConsultasSegunSexo(unaConsulta.getSexo()).addAll(unaConsulta.getRecetasConsultadas());
	}

}
