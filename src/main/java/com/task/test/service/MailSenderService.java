package com.task.test.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    void sendEmail(String toAccount, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        helper.setSubject(subject);
        helper.setText(text);
        helper.setTo(toAccount);
        helper.setFrom("mrazizbekkomilov@gmail.com");
        helper.setReplyTo("mrazizbekkomilov@gmail.com");

        javaMailSender.send(message);
    }
}
