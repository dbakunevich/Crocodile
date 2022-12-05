package ru.nsu.fit.crocodile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.crocodile.exception.BadTokenException;
import ru.nsu.fit.crocodile.model.token.PasswordResetToken;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.repository.PasswordResetTokenRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordTokenRepository;

    @Transactional
    public void createPasswordResetTokenForUser(UserData user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public PasswordResetToken getPasswordResetToken(String passwordToken) throws BadTokenException {
        PasswordResetToken token = passwordTokenRepository.findByToken(passwordToken);
        if (token == null) throw new BadTokenException("No such token");
        return token;
    }

    @Transactional
    public void deletePasswordResetToken(String token) {
        passwordTokenRepository.deleteByToken(token);
    }

}
