package ar.edu.grupo5.jm.dss.QueComemos.MonitoreoAsincronico;

import java.util.ArrayList;
import java.util.Collection;

import ar.edu.grupo5.jm.dss.QueComemos.Usuario.Usuario;

public class EnviarConsultaPorMail extends ProcesoAsincronico {

	private Collection<Usuario> usuariosPorLosQueSeMandanMail = new ArrayList<Usuario>();
	private MailSender mailSender;

	public EnviarConsultaPorMail(Collection<Usuario> usuariosConOpcionMandarMail, MailSender mailSender) {
		usuariosPorLosQueSeMandanMail = usuariosConOpcionMandarMail;
		this.mailSender = mailSender;
	}

	@Override
	public void procesarConsulta(Consulta unaConsulta) {
		if (usuariosPorLosQueSeMandanMail.contains(unaConsulta.getUsuario())) {
			mailSender.enviarMail(unaConsulta.getUsuario().getNombre(), unaConsulta.getFiltro(),
			unaConsulta.cantidadConsultas());
		}
	}

}
