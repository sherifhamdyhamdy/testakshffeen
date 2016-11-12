package com.sget.akshef.utils;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

public class Emailer {
	
	public Emailer() {

	}
	
	
public 	void sendMail(String receiver,String username)
	{
		final Properties props = new Properties();
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.socketFactory.port", "465");
//		props.put("mail.smtp.socketFactory.class",
//				"javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", "465");
//		 props.setProperty("mail.user", "lilianefahmy@gmail");
// props.setProperty("mail.password", "0121502324");

			
		
				InputStream input = null;
				try {
					input = getClass().getResourceAsStream("mail.properties");
					// load a properties file
					props.load(input);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			
			
			
			
			

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(props.getProperty("mail.user"),props.getProperty("mail.password"));
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(props.getProperty("mail.user")));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiver));
			message.setSubject("Welcome");
			message.setContent("<h4>Dear "+username+"</h4><p>Welcome to Akshffeen<br/>Thanks and Best regards", "text/html");
//			message.setText("Dear "+receiver+" ," +
//					"\n\n This is your new password : "+password);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	


public 	void sendMailForVerification(String receiver,String username,int id,int emailCount,String url)
{
	final Properties props = new Properties();
			InputStream input = null;
			try {
				input = getClass().getResourceAsStream("mail.properties");
				// load a properties file
				props.load(input);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


	Session session = Session.getDefaultInstance(props,
		new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("mail.user"),props.getProperty("mail.password"));
			}
		});

	try {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(props.getProperty("mail.user")));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(receiver));
		message.setSubject("Welcome");
		try {
			message.setContent("<h4>Dear "+username+"</h4><p>Please Active your mail<br/> Please visit this  <a href="+url+"/CodeVerification?CODE="
		+new EncryptUtils().encrypt(id+"")+"&emailCount="+new EncryptUtils().encrypt(emailCount+"")+">verification</a>     "+
		"in 48 hours to activate<br></br>"+"This link will be expire in 48 hours<br></br>"
+ "Thanks and Best regards", "text/html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		message.setText("Dear "+receiver+" ," +
//				"\n\n This is your new password : "+password);

		Transport.send(message);

		System.out.println("Done");

	} catch (MessagingException e) {
		throw new RuntimeException(e);
	}
	
}
	

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("lilianefahmy@gmail.com","0121502324");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("lilianefahmy@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("lilianefahmy@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler," +
					"\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}


	public 	void sendMailForForgetPass(String receiver,String username,int id,int emailCount,String url)
	{
		final Properties props = new Properties();
				InputStream input = null;
				try {
					input = getClass().getResourceAsStream("mail.properties");
					// load a properties file
					props.load(input);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(props.getProperty("mail.user"),props.getProperty("mail.password"));
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(props.getProperty("mail.user")));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiver));
			message.setSubject("Welcome");
			try {
				message.setContent("<h4>Dear "+username+"</h4><p>Please click this link to enter your new password<br/> Please visit this  <a href="+url+"/ForgotPassword?CODE="
			+new EncryptUtils().encrypt(id+"")+"&emailCount="+new EncryptUtils().encrypt(emailCount+"")+">Akshffeen</a>     "+
			"in 48 hours<br></br>"+"This link will be expire in 48 hours<br></br>"
	+ "Thanks and Best regards", "text/html");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			message.setText("Dear "+receiver+" ," +
//					"\n\n This is your new password : "+password);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
		



}


