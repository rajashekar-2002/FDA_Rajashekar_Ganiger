package com.EventManagement.eventManagement.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.EventManagement.eventManagement.model.Events;
import com.EventManagement.eventManagement.model.MyUser;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailSchedulerService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TaskScheduler taskScheduler;

    public void scheduleMail(LocalDateTime dateTime, MyUser myUser, String username, Events event) {
        Date scheduledTime = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        taskScheduler.schedule(() -> {
            try {
                sendMail(myUser, username,event);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }, scheduledTime);
    }

    private void sendMail(MyUser myUser,String username, Events event) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        LocalDateTime currentDateTime = LocalDateTime.now();
        helper.setSubject("event" + event.getUsername() + "triggered"  + "notfication"+ " from Event Management app");
        String html = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>Hello dear " + username + " following are the user details" + "</div>\n" +
                "event" + event.getUsername() +"is live now" +
                "<div> <b>" + "Thank you for choosing our app." + "</b></div>\n" +
                "</body>\n" +
                "</html>\n";

        helper.setText(html, true);
        helper.setTo(myUser.getEmail());
        helper.setFrom("rajashekariganiger@gmail.com");
        mailSender.send(mimeMessage);
    }
}
