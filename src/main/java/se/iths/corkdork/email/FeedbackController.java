package se.iths.corkdork.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;
import java.util.Properties;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final EmailConfig emailConfig;


    public FeedbackController(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }


    @PostMapping
    public void sendFeedback(@RequestBody Feedback feedback, BindingResult bindingResult) throws ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException("Feedback is not valid");
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedback.getEmail());
        mailMessage.setTo("philippe.vial90@gmail.com");
        mailMessage.setSubject("New feedback from " + feedback.getName());
        mailMessage.setText(feedback.getEmailFeedback());

        mailSender.send(mailMessage);
    }

}
