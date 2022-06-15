package br.com.carv.mail.util;

import org.junit.jupiter.api.Test;

public class SendEmailTest {
	
	@Test
	public void should_send_email() {
		
		
		StringBuilder message = new StringBuilder();
		
		message.append("<h3>Olá! Como você está?</h3>");
		message.append("<p>Esse é um email enviado através de uma aplicação Java!<p>");
		message.append("<p>O repositório do código fonte dessa aplicação pode ser acessada através do link: <p>");
		message.append("<br><a target='_blank' href='www.github.com/JoaoGabrielCarvalhoL' style='color:#2525a7;text-decoration: none;'>Repositório GitHub</a>");
		message.append("<br><br><span>Atenciosamente, <br><br>João Gabriel Carvalho<br><br></span>");
		
		SendEmail sendEmail = new SendEmail();
		
		sendEmail.sendEmailAttachment("emailvalido@gmail.com", "Aplicação Java!", message.toString(), "João Gabriel Carvalho", true);
		
		
	}

}
