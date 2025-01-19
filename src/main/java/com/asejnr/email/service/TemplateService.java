package com.asejnr.email.service;

import jakarta.mail.MessagingException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class TemplateService {
    @Autowired
    SpringTemplateEngine templateEngine;
    @Autowired
    MailService mailService;

    @SneakyThrows
    public void sendPasswordResetMail(String lang, String to, String subject) throws MessagingException, FileNotFoundException {
        Context context = new Context();
        Map<String, Object> templateModel = new HashMap<>();
        String username = to.split("@")[0];
        templateModel.put("username", username);

        context.setVariables(templateModel);
        context.setLocale(Locale.forLanguageTag(lang));

        String html = templateEngine.process("mail/password-reset/password-reset.html", context);
        mailService.sendHtmlMail(to,subject,html);
    }
}
