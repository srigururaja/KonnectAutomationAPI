package com.konnect.api.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import testExecution.APIController;


public class Send_Email  {
	private WebElement element = null;
	public WebDriver driver;

	public Send_Email(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@SuppressWarnings("unused")
	public void sending_email() {

		// getting date and time

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		String date_now = dtf.format(now);

		// recipient email id
		String to = APIController.reademailproperties("TO");
		String cc1 = APIController.reademailproperties("cc1");
		String cc2 = APIController.reademailproperties("cc2");
		String cc3 = APIController.reademailproperties("cc3");
		String cc4 = APIController.reademailproperties("cc4");
		String cc5 = APIController.reademailproperties("cc5");
		// Sender mail id
		String from = APIController.reademailproperties("from");
		final String username = APIController.reademailproperties("username");
		final String password = APIController.reademailproperties("password");
		// Sending email through host name
		String host = APIController.reademailproperties("send_host");

		Properties props = new Properties();
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", APIController.reademailproperties("smtp_port"));

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc1));
			//message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc2));
			//message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc3));
			//message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc4));
			//message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc5));
			message.setSubject("Konnect API Automation : Konnect Build Version 4.0.6 Patch 2");
			// message.setSubject("Demo mail");

			// 3) create MimeBodyPart object and set your message text
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("Dear Team,\n\n"
					+ "Please find the API automation test result executed on Konnect 4.0.6 Patch 2 md5sum(ec413f112d63c2faca29ed53cac46914) at "+date_now+" \n"
					+ "TOTAL UseCases: 105 PASSED UseCases: 102 FAILED UseCases: 3\n\n"
					+ "Click the below URL to check the results of the automated use cases.\n\n"
					+ "https://konnectautomation.kirusa.com/APIURL/index.html\n\n"

					+"Konnect-API Session wise Count:\n"
					+"\tOutBound SMS Low Priority API Use Cases - 9\n"
					+"\tOutBound SMS High Priority API Use Cases - 9\n"
					+"\tInBound SMS Create API Use Cases - 9\n"
					+"\tInBound SMS Delete API Use Cases - 5\n"
					+"\tOTP By OBD API Use Cases - 15\n"
					+"\tOutBound OBD Low Priority API Use Cases - 15\n"
					+"\tOutBound OBD High Priority API Use Cases - 15\n"
					+"\tTwoWay OBD Low Priority API Use Cases - 13\n"
					+"\tTwoWay OBD High Priority API Use Cases - 13\n"
					

					+ "Note:\n"
					+ "The server uptime is 10:30 A.M to 10:00 P.M\n"
					+ "Failed Cases Reason: Due to Create and Delete the Inbound SMS API Mysql Data Mixed Up."
					+ "The results using the above URL can be viewed between the server uptime only.\n\n\n"
					+ "Regrads\n" + "Konnect QA Team\n");

			// 4) create new MimeBodyPart object and set DataHandler object to
			// this object
			//MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			//String filename = ValueHolder.folderName + KonnectConstant.email_file;
			//DataSource source = new FileDataSource(filename);
			//messageBodyPart2.setDataHandler(new DataHandler(source));
			//messageBodyPart2.setFileName(filename);

			// 4.1) attach a file
			MimeBodyPart attachPart = new MimeBodyPart();
			//String attachFile = KonnectConstant.Attach_file_path;
			//DataSource source1 = new FileDataSource(attachFile);
			//attachPart.setDataHandler(new DataHandler(source1));
			//attachPart.setFileName(new File(attachFile).getName());

			/*
			 * // 4.2) attach a file MimeBodyPart attachPart1 = new MimeBodyPart(); String
			 * attachFile1 = KonnectConstant.Attach_extend_report;
			 * System.out.println(attachFile1); DataSource source2 = new
			 * FileDataSource(attachFile1); attachPart1.setDataHandler(new
			 * DataHandler(source2)); attachPart1.setFileName(new
			 * File(attachFile1).getName());
			 */

			// 5) create Multipart object and add MimeBodyPart objects to this
			// object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			// multipart.addBodyPart(messageBodyPart2);
			// multipart.addBodyPart(attachPart);
			// multipart.addBodyPart(attachPart1);

			// 6) set the multiplart object to the message object
			message.setContent(multipart);

			// 7) send message
			Transport.send(message);

			System.out.println("Message sent Successfully....");
			//log.info("Message(E-mail) sent successfully");
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}

	}
}
