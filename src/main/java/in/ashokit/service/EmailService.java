package in.ashokit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(subject);
            mailMessage.setText(body);
            mailMessage.setTo(to);
            mailSender.send(mailMessage);
            return true;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
