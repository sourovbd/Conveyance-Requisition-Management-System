package com.asd.cms.service;

public interface MailService {

	public void sendMail(String from, String to,String[] Cc, String subject, String body);
}
