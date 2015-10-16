package ar.edu.grupo5.jm.dss.QueComemos.Consulta.MonitoreoAsincronico;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ar.edu.grupo5.jm.dss.QueComemos.Consulta.Consulta;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LogearConsultasMasDe100 extends ProcesoAsincronico {

	private static final Logger logger = LogManager.getLogger("LoggerConsultas");

	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		if (unaConsulta.cantidadConsultadas() > 100) {
			logger.info("Usuario {} realizo una consulta con {} resultados", unaConsulta.getNombre(), unaConsulta.cantidadConsultadas());
		}

	}

}
