package com.syscolab.qe.core.util.email;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.google.common.io.Files;
import com.syscolab.qe.core.api.jackson.JacksonUtil;
import com.syscolab.qe.core.api.restassured.RestUtil;
import com.syscolab.qe.core.api.util.Headers;
import com.syscolab.qe.core.common.LoggerUtil;
import com.syscolab.qe.core.data.EmailUtilAuthData;
import com.syscolab.qe.core.data.EmailUtilData;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Properties;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SesException;

import static com.syscolab.qe.core.common.SyscoLabCoreConstants.*;

/**
 * This is the Util class for Email related functions
 * @author  Sandeep Perera
 */
public class EmailUtil {
    private EmailUtil(){}
    private static Properties commonProperties = null;
    public static FileInputStream fileInputStream;
    /**
     * This is method for sending email
     * @param subject Subject of the email
     * @param toList Recipient list
     * @param reportName filename of the attachment
     */
    public static void send(String subject, String toList, String reportName) {

        final String username = SYSCO_QCENTER_EMAIL_USERNAME;
        final String password = SYSCO_QCENTER_EMAIL_PW;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toList));
            message.setSubject(subject);

            StringWriter writer = new StringWriter();
            fileInputStream = new FileInputStream(new File("Report.html"));
            IOUtils.copy((fileInputStream), writer);

            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setContent(writer.toString(), "text/html");

            BodyPart messageBodyPart2 = new MimeBodyPart();
            String filename = reportName + ".html";
            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);
            Transport.send(message);

            LoggerUtil.logINFO("Email Report has been sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is method for sending email using AWS SES
     * @param from Sender of the email. Sender has to be registered in AWS SES
     * @param to Recipient
     * @param subject Subject of the email
     * @param body Email body
     * @param htmlBody HTML body content
     */
    public static void sendViaSES(String from, String subject, String body, String htmlBody, String... to){
        SendEmailRequest request;
        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.US_EAST_1).build();
            if (htmlBody == null){
                request = new SendEmailRequest()
                        .withDestination(
                                new Destination().withToAddresses(to))
                        .withMessage(new com.amazonaws.services.simpleemail.model.Message()
                                .withBody(new Body()
                                        .withText(new Content()
                                                .withCharset("UTF-8").withData(body)))
                                .withSubject(new Content()
                                        .withCharset("UTF-8").withData(subject)))
                        .withSource(from);
            }else {
                request = new SendEmailRequest()
                        .withDestination(
                                new Destination().withToAddresses(to))
                        .withMessage(new com.amazonaws.services.simpleemail.model.Message()
                                .withBody(new Body()
                                        .withHtml(new Content()
                                                .withCharset("UTF-8").withData(htmlBody))
                                        .withText(new Content()
                                                .withCharset("UTF-8").withData(body)))
                                .withSubject(new Content()
                                        .withCharset("UTF-8").withData(subject)))
                        .withSource(from);
            }
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }

    public static void sendViaSESWithAttachment(String sender,String recipient,String subject,String bodyHTML,String fileLocation) throws MessagingException, IOException {
        Region region = Region.US_EAST_1;
        SesClient client = SesClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        java.io.File theFile = new java.io.File(fileLocation);
        byte[] fileContent = java.nio.file.Files.readAllBytes(theFile.toPath());

        Session session = Session.getDefaultInstance(new Properties());

        // Create a new MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Add subject, from and to lines.
        message.setSubject(subject, "UTF-8");
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

        // Create a multipart/alternative child container.
        MimeMultipart msgBody = new MimeMultipart("alternative");

        // Create a wrapper for the HTML and text parts.
        MimeBodyPart wrap = new MimeBodyPart();

        // Define the text part.
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent("", "text/plain; charset=UTF-8");

        // Define the HTML part.
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");

        // Add the text and HTML parts to the child container.
        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        // Add the child container to the wrapper object.
        wrap.setContent(msgBody);

        // Create a multipart/mixed parent container.
        MimeMultipart msg = new MimeMultipart("mixed");

        // Add the parent container to the message.
        message.setContent(msg);
        msg.addBodyPart(wrap);

        // Define the attachment.
        MimeBodyPart att = new MimeBodyPart();
        DataSource fds = new ByteArrayDataSource(fileContent, "text/HTML");
        att.setDataHandler(new DataHandler(fds));

        String reportName = fileLocation + ".html";
        att.setFileName(reportName);

        // Add the attachment to the message.
        msg.addBodyPart(att);

        try {
            System.out.println("Attempting to send an email through Amazon SES");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);

            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .build();

            client.sendRawEmail(rawEmailRequest);

        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        System.out.println("Email sent using SesClient with attachment");
    }

    public static void sendViaInternalMailServer(String subject, String toList) {
        String content = "";
        EmailUtilData emailUtilData = new EmailUtilData();
        emailUtilData.from = new EmailUtilAuthData().user;
        emailUtilData.to = toList;
        emailUtilData.subject = subject;
        emailUtilData.html = "";

        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = "http://52.1.201.177/nodemailer";
        RestUtil.send(Headers.getHeader(), JacksonUtil.getAsString(emailUtilData), "", "POST");
    }

    public static void sendViaInternalMailServer(String subject, String toList, String reportName) {
        String content = "";
        EmailUtilData emailUtilData = new EmailUtilData();
        emailUtilData.from = new EmailUtilAuthData().user;
        emailUtilData.to = toList;
        emailUtilData.subject = subject;
        String filename = reportName + ".html";
        try {
            content = Files.toString(new File(filename), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        emailUtilData.html = content;

        RestUtil.BASE_PATH = "";
        RestUtil.API_HOST = "http://52.1.201.177/nodemailer";
        RestUtil.send(Headers.getHeader(), JacksonUtil.getAsString(emailUtilData), "", "POST");
    }

    private static void loadCommonProperties() {
        commonProperties = new Properties();
        InputStream input;
        try {
            input = EmailUtil.class.getResourceAsStream("/qecore.properties");
            commonProperties.load(input);
        } catch (IOException e) {
            LoggerUtil.logERROR(e.getMessage(), e);
        }
    }

    public static String getCommonProperty(String key) {
        if (commonProperties == null)
            loadCommonProperties();

        String p = System.getProperty(key);
        return p != null ? p : commonProperties.getProperty(key);
    }

}
