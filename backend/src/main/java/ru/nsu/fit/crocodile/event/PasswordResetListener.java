package ru.nsu.fit.crocodile.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.service.PasswordResetTokenService;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PasswordResetListener implements
        ApplicationListener<OnPasswordResetEvent> {
    private final PasswordResetTokenService passwordResetTokenService;

    @Value("${mail.message.password_reset.success}")
    private String resetSucMessage;

    @Value("${front.page.password_reset}")
    private String frontResetPassPage;

    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnPasswordResetEvent event) {
        this.confirmReset(event);
    }

    private void confirmReset(OnPasswordResetEvent event) {
        UserData user = event.getUser();
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Reset Password";
        String confirmationUrl
                = frontResetPassPage + "?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(resetSucMessage + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
