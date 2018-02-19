package com.asd.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
    private MailSender mailSender;

    public void sendMail(String from, String to,String[] Cc, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(Cc);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

    }
}
