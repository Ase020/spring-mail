package com.asejnr.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Service
public class MailService {
    private static final String NO_REPLY_EMAIL = "no_reply@email.com";

    @Autowired
    private JavaMailSender mailSender;

    public void sendTextMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(NO_REPLY_EMAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @SneakyThrows
    public void innerHtmlMail(String to, String subject) throws MessagingException, FileNotFoundException {
        String htmlBody = "<html><body><p>JUnit cheatsheet!</p><img src='cid:logo' alt='logo'/></body></html>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(NO_REPLY_EMAIL);
        helper.setTo(to);
        helper.setSubject(subject);

        helper.setText(htmlBody, true);
        helper.addAttachment("junit-cheat-sheet.pdf", ResourceUtils.getFile("classpath:templates/mail/attachments/junit-cheat-sheet.pdf"));
        helper.addInline("logo", ResourceUtils.getFile("classpath:templates/mail/attachments/logo.png"));

        mailSender.send(message);
    }
}
