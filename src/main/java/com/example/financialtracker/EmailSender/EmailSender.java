package com.example.financialtracker.EmailSender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@AllArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    @Async
    public void send(String to,String email){

        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
            helper.setText(email,true);
            helper.setFrom("testing.email@mail.com");
            helper.setTo(to);
            helper.setSubject("Confirmation Email from Financial Tracker");
            mailSender.send(message);
        }catch(MessagingException e){
            log.error("Error Sending Email: ", e.getMessage());
            throw new IllegalStateException("Error Sending Email");
        }
    }

}
