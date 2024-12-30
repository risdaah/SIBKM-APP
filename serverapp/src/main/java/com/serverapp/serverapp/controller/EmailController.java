package com.serverapp.serverapp.controller;

import com.serverapp.serverapp.model.request.EmailRequest;
import com.serverapp.serverapp.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller
@AllArgsConstructor // Generates a constructor with all class fields as parameters
@RequestMapping("/email") // Maps all requests to the "/email" endpoint
public class EmailController {

    private final EmailService emailService; // Dependency injection of EmailService

    // Endpoint to send a simple email
    @PostMapping("/simple") // Maps POST requests to "/email/simple"
    public String sendSimpleEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendSimpleEmail(emailRequest); // Calling the service to send a simple email
        return "Simple email sent successfully!"; // Returning a success message
    }

    // Endpoint to send an email with an attachment
    @PostMapping("/attach") // Maps POST requests to "/email/attach"
    public String sendEmailWithAttachment(@RequestBody EmailRequest emailRequest) {
        // Calling the service to send an email with attachment and getting the response
        // body
        String response = emailService.sendEmailWithAttachment(emailRequest).getBody();
        // Returning the response or a default success message
        return response != null ? response : "Email with attachment sent successfully!";
    }

    // Endpoint to send a notice email
    @PostMapping("/notice") // Maps POST requests to "/email/notice"
    public String sendNoticeEmail(@RequestBody EmailRequest emailRequest) {
        return emailService.sendNoticeEmail(emailRequest); // Calling the service to send a notice email
    }
}