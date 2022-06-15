package br.com.carv.mail.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class SendEmail {

	private String email = "inserir seu email aqui";
	private String password = "gerar a sua senha no provedor do email desejado";

	public SendEmail() {
		
	}
	
	public void sendEmail(String senders, String subject, String text, String nameUser, boolean isHtml) {

		try {

			Properties prop = new Properties();
			prop.put("mail.smtp.ssl.trust", "*");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls", "true");
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "465");
			prop.put("mail.smtp.socketFactory.port", "465");
			prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// TODO Auto-generated method stub
					return new PasswordAuthentication(email, password);
				}

			});

			System.out.println(session);

			Address[] toUser = InternetAddress
					.parse(senders);

			//
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email, nameUser));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(subject);
			
			if (isHtml) {
				message.setContent(text, "text/html; charset=utf-8");
			} else {
				message.setText(text);
			}
			

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private FileInputStream simulationPdf() throws Exception {
		
		Document document = new Document(); 
		File file = new File("file.pdf");
		file.createNewFile();
		
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteúdo do Email"));
		document.close();
		
		return new FileInputStream(file);
	}
	
	public void sendEmailAttachment(String senders, String subject, String text, String nameUser, boolean isHtml) {

		try {

			Properties prop = new Properties();
			prop.put("mail.smtp.ssl.trust", "*");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls", "true");
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "465");
			prop.put("mail.smtp.socketFactory.port", "465");
			prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// TODO Auto-generated method stub
					return new PasswordAuthentication(email, password);
				}

			});

			System.out.println(session);

			Address[] toUser = InternetAddress
					.parse(senders);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email, nameUser));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(subject);
			
			MimeBodyPart emailBody = new MimeBodyPart();
			
			if (isHtml) {
				emailBody.setContent(text, "text/html; charset=utf-8");
			} else {
				emailBody.setText(text);
			}
			
			MimeBodyPart emailAttachment = new MimeBodyPart();
			emailAttachment.setDataHandler(new DataHandler(new ByteArrayDataSource(simulationPdf(), "application/pdf")));
			emailAttachment.setFileName("test.pdf");
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(emailBody);
			multipart.addBodyPart(emailAttachment);
			
			message.setContent(multipart);
				
		

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
