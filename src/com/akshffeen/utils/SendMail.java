package com.akshffeen.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sget.akshef.utils.Performance;
import com.sun.mail.util.MailSSLSocketFactory;

public class SendMail 
{
	private final String to;
	private final String subject;
	private final String text;
	private int try_count;

	private final Utils utils = Utils.getInstance();

	private String action = Constants.success;
	
	public SendMail(String to, String subject, String text, int try_count)
	{
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.try_count = try_count;
	}
			
//	final String username2 = "akshffeen1@gmail.com";
//	final String password2 = "mailAkshffeen";

	public String send()
	{
//			if(true)return Constants.success;		//abdooooooooooo

//		final String username = utils.getXMLkey("account_username"+try_count);
//		final String password = utils.getXMLkey("account_password"+try_count);
//		String from = utils.getXMLkey("account_from"+try_count);
//		String host = utils.getXMLkey("host"+try_count);
//		String port = utils.getXMLkey("port"+try_count);
		
//		final String username = "support@aksheffeen.com";
//		final String password = "Sget.Net123";
//		String from = "support@aksheffeen.com";
//
////		String host = "mail.aksheffeen.com";
////		String host = "mail.inabawy.com";
//		String host = "198.38.88.36";
//
//		String port = "2525";
////		String port = "25";
		
		
		final String username = "info@sget.net";
		final String password = "mN0103368366";
		String from = "info@sget.net";
			
//		String host = "smtp.office365.com";
//		String port = "587";
		
//		String host = "outlook.office365.com";
//		String port = "995";
//		
		String host = "outlook.office365.com";
		String port = "993";
		
		
//		if(try_count>=2)
//		{
//
//			from = "akshffeen1@gmail.com";
//			host = "smtp.gmail.com";
//			port = "465";
//		}
		
		System.out.println("-----------------------------");
		System.out.println("username : "+username );
		System.out.println("password : "+password);
		System.out.println("from : "+from);
		System.out.println("host : "+host);
		System.out.println("port : "+port);
		
		
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.enable", "true");  

		 MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  sf.setTrustAllHosts(true);
		  props.put("mail.smtp.ssl.socketFactory", sf);
		  
		  
		Session session = null;
//		if(try_count>=2)
//		{
//			session = Session.getInstance(props,
//			  new javax.mail.Authenticator() {
//				@Override
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(username2, password2);
//				}
//			  });
//		}
//		else
		{
			session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });	
		}
				
 
		try 
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);
 
			message.setContent(text,"text/html; charset=utf-8");
			
			Transport.send(message);
			
			Performance.releaseMemory();
			
			utils.print(to+" ------------- > success");		

			action = Constants.success;
		}
		catch (MessagingException e) 
		{
			utils.print(to+" ------------- > fail");
			utils.print(e);
			
			Performance.releaseMemory();
			
			if(try_count<5)
			{
				new SendMail(to, subject, text, ++try_count);
//				new SendMail(username, password, host, port, from_user, to_user, ++try_count);
				
				send();
			}
			else
				action = Constants.fail;
		}
		finally
		{
			Performance.releaseMemory();
						
			return action;
		}
	}

}