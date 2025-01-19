package com.asejnr.email.controller;

import com.asejnr.email.service.MailService;

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

    @GetMapping("/text")
    public void sendTextMail(@RequestParam String to,
                             @RequestParam String subject,
                             @RequestParam String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
        mailService.sendTextMail(to, subject, text);
    }

    @GetMapping("/inline")
    public void sendInlineHtmlMessage(String to, String message) throws MessagingException, FileNotFoundException {
        mailService.innerHtmlMail(to, message);
    }
}
