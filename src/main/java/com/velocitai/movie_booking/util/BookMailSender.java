package com.velocitai.movie_booking.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookMailSender {

	@Autowired
	private JavaMailSender javaMailSender;
	
	 public void SendEmail(String to,String subject ,String body) {
		 try {
			 
			 SimpleMailMessage mail=new SimpleMailMessage();
			 mail.setTo(to);
			 mail.setText(body);
			 mail.setSubject(subject);
			 javaMailSender.send(mail);
			
		} catch (Exception e) {
			log.error("Exception while sendEmail ",e);
		}
	 }
}
