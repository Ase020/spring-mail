package com.asejnr.email.controller;

import com.asejnr.email.service.MailService;

import com.asejnr.email.service.TemplateService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateService templateService;

    @GetMapping("/text")
    public void sendTextMail(@RequestParam String to,
                             @RequestParam String subject,
                             @RequestParam String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
        mailService.sendTextMail(to, subject, text);
    }

    @GetMapping("/inline")
    public void sendInlineHtmlMessage(String to, String subject, String message) throws MessagingException, FileNotFoundException {
        String formattedMessage = "<h2>" + message + "</h2>";
        mailService.innerHtmlMail(to, subject, formattedMessage);
    }


    @GetMapping("/password-reset")
    public void sendPasswordResetHtmlMessage(String lang, String to, String subject) throws MessagingException, FileNotFoundException {
        templateService.sendPasswordResetMail(lang, to, subject);
    }
}
