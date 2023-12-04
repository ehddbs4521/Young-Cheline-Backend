package YoungCheline.YoungCheline.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("이메일 인증");
        mimeMessageHelper.setText("""
                <div>
                  <a href="http://localhost:3000/sign-up?email=%s" target="_blank">링크를 누르세요</a>
                </div>
                """.formatted(email), true);

        javaMailSender.send(mimeMessage);
    }

    public void sendTempPwEmail(String email, String tempPw) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("임시 비밀번호 발급");
        mimeMessageHelper.setText("""
                <div>
                       <b>임시 비밀번호: %s</b>
                </div>
                """.formatted(tempPw), true);

        javaMailSender.send(mimeMessage);
    }

}
