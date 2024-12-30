package com.serverapp.serverapp.service;

import com.serverapp.serverapp.model.request.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage; // Importing class for sending simple emails
import org.springframework.mail.javamail.JavaMailSender; // Importing interface for sending emails
import org.springframework.mail.javamail.MimeMessageHelper; // Importing helper class for easier MIME email creation
import org.springframework.stereotype.Service; // Importing annotation to mark the class as a service
import org.thymeleaf.TemplateEngine; // Importing class for processing email templates
import org.thymeleaf.context.Context; // Importing class for Thymeleaf context

@Service // Marks this class as a Spring service
@Slf4j // Provides logging capabilities
@AllArgsConstructor // Generates a constructor with all class fields as parameters
public class EmailService {

    private final JavaMailSender javaMailSender; // Object for sending emails
    private final TemplateEngine templateEngine; // Object for processing email templates

    // Method to send a notice email
    public String sendNoticeEmail(EmailRequest emailRequest) {
        Context context = new Context(); // Creating a context for Thymeleaf
        // Storing variables for the template
        context.setVariable("subject", emailRequest.getSubject());
        context.setVariable("message", emailRequest.getBody());

        // Processing the template for the email content
        String emailContent = templateEngine.process("notice-email", context);

        try {
            // Creating a MIME message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // Setting recipient and subject
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailContent, true); // Setting email content as HTML

            // Sending an attachment if provided
            if (emailRequest.getAttachment() != null && !emailRequest.getAttachment().isEmpty()) {
                FileSystemResource file = new FileSystemResource(new File(emailRequest.getAttachment()));
                helper.addAttachment(file.getFilename(), file); // Adding attachment
            }

            javaMailSender.send(mimeMessage); // Sending the email
            log.info("Notice email sent successfully to {}", emailRequest.getTo());
            return "Email sent successfully to " + emailRequest.getTo(); // Returning success message
        } catch (Exception e) {
            log.error("Failed to send notice email: {}", e.getMessage()); // Logging error
            return "Failed to send email: " + e.getMessage(); // Returning error message
        }
    }

    // Method to send a simple email
    public EmailRequest sendSimpleEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage(); // Creating a simple email message
        message.setTo(emailRequest.getTo()); // Setting recipient
        message.setSubject(emailRequest.getSubject()); // Setting subject
        message.setText(emailRequest.getBody()); // Setting email body
        javaMailSender.send(message); // Sending the email

        return emailRequest; // Returning the email request object
    }

    // Method to send an email with an attachment
    public EmailRequest sendEmailWithAttachment(EmailRequest emailRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage(); // Creating a MIME message
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // Creating helper

            helper.setTo(emailRequest.getTo()); // Setting recipient
            helper.setSubject(emailRequest.getSubject()); // Setting subject
            helper.setText(emailRequest.getBody()); // Setting email body

            // Setting up the attachment
            FileSystemResource file = new FileSystemResource(new File(emailRequest.getAttachment()));
            helper.addAttachment(file.getFilename(), file); // Adding the attachment

            javaMailSender.send(message); // Sending the email
            log.info("Successfully sent email with attachment!"); // Logging success
        } catch (Exception e) {
            log.error("Failed to send email with attachment: {}", e.getMessage()); // Logging error
        }
        return emailRequest; // Returning the email request object
    }
}