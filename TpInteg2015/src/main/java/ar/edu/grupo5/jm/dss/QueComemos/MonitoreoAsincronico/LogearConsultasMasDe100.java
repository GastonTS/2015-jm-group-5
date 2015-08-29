package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LogearConsultasMasDe100 extends ProcesoAsincronico {
	 
	private static final Logger logger = LogManager.getLogger("LoggerConsultas");
	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		if (unaConsulta.tieneMasDe100()){
			
			 logger.info("Usuario {} realizo una consulta con {} resultados", unaConsulta.getNombre() ,unaConsulta.cantidadConsultas());
		}

	}

}
