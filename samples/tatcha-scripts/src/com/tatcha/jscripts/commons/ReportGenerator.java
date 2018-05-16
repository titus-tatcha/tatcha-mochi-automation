package com.tatcha.jscripts.commons;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.tatcha.jscripts.dao.TestCase;

/**
 * To create email templates as Test Reports
 * @author titus
 *
 */
public class ReportGenerator {

	private final static Logger logger = Logger.getLogger(ReportGenerator.class);
	private static ReportGenerator instance = null;

	public static final ReportGenerator getInstance() {
		if (null == instance)
			instance = new ReportGenerator();
		return instance;
	}

	private ReportGenerator() {
	}

	/**
	 * Create Mail templates for each Testflows containing TC-No, MOC-No,
	 * Functionality, Pass/Fail and Remarks
	 * 
	 * @param MODULE
	 * @param tcList
	 * @return
	 */
	public boolean generateReport(String MODULE, List<TestCase> tcList) {
		logger.info("BEGIN ReportGenerator.generateReport");
		boolean flag = false;

		Properties props = new Properties();

		try {
			props.load(new FileInputStream(getClass().getResource("/SMTP.properties").getFile()));

		} catch (IOException ie) {
			logger.error("SMTP PROPERTIES FILE ERROR: " + ie.toString());
		}

		Properties emailProp = new Properties();
		try {
			emailProp.load(new FileInputStream(getClass().getResource("/EMAIL.properties").getFile()));

		} catch (IOException ie) {
			logger.error("EMAIL PROPERTIES FILE ERROR: " + ie.toString());
		}

		// Session session = Session.getInstance(props);
		String USERNAME = emailProp.getProperty("mail.uname");
		String PASSWORD = emailProp.getProperty("mail.pwd");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		MimeMessage message = new MimeMessage(session);

		String FROM_ADDRESS = emailProp.getProperty("mail.from");
		String TO_ADDRESS = emailProp.getProperty("mail.to");
		String CC_ADDRESS = emailProp.getProperty("mail.cc");
		String SUBJECT = emailProp.getProperty("mail.sub") + MODULE;
		String APP_TYPE = emailProp.getProperty("content.type");

		try {
			message.setFrom(new InternetAddress(FROM_ADDRESS));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO_ADDRESS));

			String COMA = ",";
			String[] ccAddressList = null;
			if (null != CC_ADDRESS) {
				if (CC_ADDRESS.contains(COMA)) {

					ccAddressList = CC_ADDRESS.split(COMA);
					Address[] addresses = new Address[ccAddressList.length];
					int i = 0;
					for (String eachAddress : ccAddressList) {
						addresses[i++] = new InternetAddress(eachAddress);
					}
					message.addRecipients(Message.RecipientType.CC, addresses);
				} else {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(CC_ADDRESS));
				}

			}
			message.setSubject(SUBJECT);

			/** Velocity */

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");

			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			// ve.init();

			String templatePath = "";
			String TEMPLATE = "/velocity/email.vm";

			if (null != getClass().getResource(TEMPLATE).getFile())
				templatePath = getClass().getResource(TEMPLATE).getFile();

			Properties prop = new Properties();
			// prop.put("file.resource.loader.path", templatePath);
			prop.put(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templatePath);

			ve.init(prop);

			logger.info("VE initialized");

			Template template = ve.getTemplate(TEMPLATE);
			VelocityContext vcontext = new VelocityContext();
			vcontext.put("module", MODULE);
			vcontext.put("tcList", tcList);

			StringWriter swriter = new StringWriter();
			/** Mail Template Binding */
			template.merge(vcontext, swriter);

			/** Mail BOdy */
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(swriter.toString(), APP_TYPE);
			MimeMultipart mimeMultipart = new MimeMultipart();
			mimeMultipart.addBodyPart(bodyPart);

			/** Mail Attachment */
			// bodyPart = new MimeBodyPart();
			// // String fileName = "mail-attachment-template.txt";
			// String fileName =
			// getClass().getResource("/testReport.html").getFile().toString();
			// DataSource dataSource = new FileDataSource(fileName);
			// bodyPart.setDataHandler(new DataHandler(dataSource));
			// bodyPart.setFileName("Attachement");
			// mimeMultipart.addBodyPart(bodyPart);

			// message.setContent(mimeMultipart,APP_TYPE);
			message.setContent(mimeMultipart);

			Transport.send(message);

			flag = true;
			logger.info("Mail Sent Successfully");
		} catch (MessagingException e) {
			logger.error("MessagingExceptio "+e.toString());
		}
		logger.info("END ReportGenerator.generateReport");
		return flag;
	}

}