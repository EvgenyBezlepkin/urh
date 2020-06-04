package com.example.demo.controller;

import com.example.demo.domain.Result;
import com.example.demo.domain.Rewiev;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.RewievRepository;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@PropertySource("classpath:mail-message.properties")
public class MainRestController {

    private final JavaMailSender emailSender;
    private final ResultRepository resultRepository;
    private final RewievRepository rewievRepository;


    public MainRestController(JavaMailSender emailSender, ResultRepository resultRepository, RewievRepository rewievRepository) {
        this.emailSender = emailSender;
        this.resultRepository = resultRepository;
        this.rewievRepository = rewievRepository;
    }

    @RequestMapping(value = "contacts",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveRec(@RequestBody Rewiev rewiev) {
        rewievRepository.save(rewiev);

    }

    @PostMapping("/")
    public String getIndex(@RequestBody Result result, @RequestHeader("Accept-Language") String langHeader) throws IOException, URISyntaxException {

        String lang = langHeader.substring(0, 2);
        System.out.println(lang);

        result.setLdt(LocalDateTime.now());
        resultRepository.save(result);
        sendEmail(result, lang);
        return "redirect:/";
    }


    private void sendEmail(Result result, String lang) throws URISyntaxException, IOException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


        URI uriRu = getClass().getClassLoader().getResource("sendMessageRu").toURI();
        String htmlMsgRu = new String(Files.readAllBytes(Paths.get(uriRu)));

        URI uriEn = getClass().getClassLoader().getResource("sendMessageEn").toURI();
        String htmlMsgEn = new String(Files.readAllBytes(Paths.get(uriEn)));

        String res = result.getOuts().replaceAll("%. ", "%<br>");
        String htmlEn =
                "<h2 style='text-align: center;'>" + "Hello, " + result.getName() + "!</h2>" +
                        "<p style='text-align: center;'>" + res + "</p>"
                        + htmlMsgEn;

        String htmlRu =
                "<h2 style='text-align: center;'>" + "Привет, " + result.getName() + "!</h2>" +
                        "<p style='text-align: center;'>" + res + "</p>"
                        + htmlMsgRu;

        try {
            if ("ru".equals(lang)) {
                helper.setText(htmlRu, true);
            } else {
                helper.setText(htmlEn, true);
            }
            helper.setTo(result.getEmail());
            helper.setSubject("Your test results");
            helper.setFrom("info@urhumanresource@mail.com");
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
        emailSender.send(mimeMessage);
    }


}
