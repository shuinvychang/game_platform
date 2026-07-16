package com.shuinvy.game_platform.common;

import com.shuinvy.game_platform.model.Picture;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailTemplateHandler {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender emailSender;

    public String getNewGameEmailTemplate(
            String name,
            String title,
            String info,
            String description,
            Integer screenshotCount) {
        List<String> images = new ArrayList<>();
        if (screenshotCount > 0) {
            for (int i = 0; i < screenshotCount; i++) {
                images.add("image" + (i + 1));
            }
        }
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("title", title);
        context.setVariable("info", "“%s”".formatted(info));
        context.setVariable("description", description);
        context.setVariable("images", images);
        return templateEngine.process("new-game-notify", context);
    }

    @Async
    public void sendHtmlMail(
            String to, String subject,
            String html,
            List<Picture> pictureList)
            throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        Picture picture;
        if (pictureList != null)  {
            for (int i = 0; i < pictureList.size(); i++) {
                picture = pictureList.get(i);
                helper.addInline("image%d".formatted(i+1),
                        new File("upload/%s".formatted(picture.getPath())));
            }
        }

        emailSender.send(message);
    }

}
