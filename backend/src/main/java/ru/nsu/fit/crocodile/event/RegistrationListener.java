package ru.nsu.fit.crocodile.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.service.VerificationTokenService;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private final VerificationTokenService service;

    @Value("${mail.message.registration.success}")
    private String regSucMessage;

    @Value("${front.page.confirmation}")
    private String frontConfirmRegPage;

    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        UserData user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = frontConfirmRegPage + "?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(regSucMessage + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
