package com.akashkumar.unu.ForgotPassword;

import com.akashkumar.unu.Utilities.Urls;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService implements EmailServices {

    @Override
    public void sendResetToken(String email, String otp) {
        Properties props = new Properties();
        props.put(Urls.SMTP+"host", "smtp.gmail.com");
        props.put(Urls.SMTP+"port", "587");
        props.put(Urls.SMTP+"auth", "true");
        props.put(Urls.SMTP+"starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Urls.FromEmail, Urls.EMAIL_PASSWORD);
            }
        });

        String htmlContent = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Password Reset Email</title>"
                + "<style>"
                + "body { background-color: #ffffff; color: #000000; font-family: Arial, sans-serif; padding: 20px; }"
                + ".email-box { background-color: #f0f0f0; padding: 20px; border-radius: 10px; }"
                + ".reset-code { color: #0056b3; font-size: 24px; font-weight: bold; }"
                + "a { color: #0056b3; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class='email-box'>"
                + "<p><strong>UNU</strong></p>"
                + "<h2 class='reset-code'>Password reset code</h2>"
                + "<p>Please use this code to reset the password for the UNU <strong>" + emailExtractor(email) + "</strong>.</p>"
                + "<p>Here is your code: <span class='reset-code'>" + otp + "</span></p>"
                + "<p>If you didn’t request this, ignore this email.</p>"
                + "<p>If you don’t recognize the UNU <strong>" + emailExtractor(email) + "</strong>, you can <a href='#'>click here</a> to remove your email address from that account.</p>"
                + "<p>Thanks,<br>The UNU team</p>"
                + "<a href='#'>Privacy Statement</a><br>"
                + "<small>SkinCare Corporation, One SkinCare Way, Redmond, WA 98052</small>"
                + "</div>"
                + "</body>"
                + "</html>";


        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Urls.FromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Password reset request");
//            message.setText("Click to reset password: " + otp);
            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("Reset otp email sent to " + email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String emailExtractor(String email) {
        //akp97583757@gmail.com
        //        @gmail.com = 10
        //akp97583757
        String myMail = "";
        int len = email.length()-10;
        int temp = len;
        for(int i=0; i<=len; i++){
            if(i<=temp-3){
                myMail = myMail + email.charAt(i);
            }else{
                myMail = myMail + "*";
            }
        }
        //akp97583***
        return myMail+"@gmail.com";
    }
}
interface EmailServices {
    void sendResetToken(String email , String otp);
}

