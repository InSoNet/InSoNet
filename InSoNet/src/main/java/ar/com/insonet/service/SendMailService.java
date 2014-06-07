package ar.com.insonet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendMailService
{
	private JavaMailSender mailSender;
	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void sendMailConfirm(String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		String msg = "Gracias por registrarse como nuevo usuario Insonet." +
				"Para Confirmar su registro acceda al siguiente enlace. http://localhost:8080/InSoNet/confirm";
		message.setFrom("insonetproject@gmail.com");
		message.setTo(to);
		message.setSubject("Confirmación de registro como usuario Insonet");
		message.setText(msg);
		mailSender.send(message);
		
	}
	
	public void sendMail(String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		String msg = "Se han modificado sus datos de perfil de su cuenta insonet." +
				"Si usted no lo hizo comuniquese con el administrador del sitio.";
		message.setFrom("insonetproject@gmail.com");
		message.setTo(to);
		message.setSubject("Actualización de datos de perfil");
		message.setText(msg);
		try {
			mailSender.send(message);
		} catch (MailException ex) {
			//TODO Log
			System.err.println(ex.getMessage());
		}

	}
	
	public void sendMail(String from, String to, String subject, String msg) {
 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}
}
