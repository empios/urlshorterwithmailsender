package pl.wlodarczyk.apiconsumerwithsender.controller;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wlodarczyk.apiconsumerwithsender.model.Model;


@Controller
public class ApiService {

    private JavaMailSender javaMailSender;

    public ApiService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/short")
    public String shortUrl(@RequestParam String url){

        HttpResponse<String> response = Unirest.post("https://url-shortener-service.p.rapidapi.com/shorten")
                .header("x-rapidapi-host", "url-shortener-service.p.rapidapi.com")
                .header("x-rapidapi-key", "1433096c49msh804386171bb2e36p1ccae3jsn49d9267d0fa1")
                .header("content-type", "application/x-www-form-urlencoded")
                .body("url=" + url)
                .asString();


        Model shortedUrl = new Model();
        shortedUrl.setResultUrl(response.getBody());
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("shinigamipl123@gmail.com");
        msg.setSubject("Twój skrócony link");
        msg.setText(shortedUrl.getResultUrl());
        javaMailSender.send(msg);
        return "short";
    }
}
