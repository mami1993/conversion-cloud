package com.cli;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailSend implements Runnable {

	public void run() {

		try {
			String host = "smtp.gmail.com";
			String user = "mouh0772019@gmail.com";
			String pass = "mohamed0779595741";
			String to = Client.clients.get(Client.clients.size() - 1).getEmail_client();
			String from = "mouh0772019@gmail.com";
			String subject = "conversion";
			String messageText = "Your Conversion Is Done ";
			boolean sessionDebug = false;
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");
			java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(messageText);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, pass);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}
}
