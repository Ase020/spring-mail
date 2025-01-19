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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public void innerHtmlMail(String to, String subject, String html) throws MessagingException, FileNotFoundException {
        String htmlBody = "<html><body><p>JUnit cheatsheet!</p><img src='cid:logo' alt='logo'/>" + html + "</body></html>";
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

    public record MailResource(String name, File file)  {}

    public void sendHtmlMailWithResources(String to, String subject, String html, List<MailResource> inlines, List<MailResource> attachments) throws MessagingException, FileNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(NO_REPLY_EMAIL);
        helper.setTo(to);
        helper.setSubject(subject);

        if (Objects.nonNull(attachments)) {
            for (MailResource attachment : attachments) {
                helper.addAttachment(attachment.name, attachment.file);
            }
        }

        helper.setText(html, true);

        if (Objects.nonNull(inlines)) {
            for (MailResource inline : inlines) {
                helper.addInline(inline.name, inline.file);
            }
        }
        mailSender.send(message);
    }

    public void sendHtmlMail(String to, String subject, String html) throws MessagingException, FileNotFoundException {
        sendHtmlMailWithResources(to, subject, html, Collections.emptyList(), Collections.emptyList());
    }
}
