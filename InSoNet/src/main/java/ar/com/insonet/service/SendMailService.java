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
		
		String msg = "<html><body>Gracias por registrarse como nuevo usuario Insonet. <br/>" +
				"Para Confirmar su registro acceda al siguiente enlace. <a href='http://localhost:8080/InSoNet/confirm'>Confirmar</a></body></html>";
		message.setFrom("insonetproject@gmail.com");
		message.setTo(to);
		message.setSubject("Confirmación de registro como usuario Insonet");
		message.setText(msg);
		try {
			mailSender.send(message);
		} catch (MailException ex) {
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
