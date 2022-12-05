package ru.nsu.fit.crocodile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.fit.crocodile.exception.BadTokenException;
import ru.nsu.fit.crocodile.model.UserData;
import ru.nsu.fit.crocodile.model.token.VerificationToken;
import ru.nsu.fit.crocodile.repository.VerificationTokenRepository;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository tokenRepository;

    public VerificationToken getVerificationToken(String VerificationToken) throws BadTokenException {
        VerificationToken token = tokenRepository.findByToken(VerificationToken);
        if (token == null) throw new BadTokenException("No such token");
        return token;
    }

    @Transactional
    public void createVerificationToken(UserData user, String token) {
        tokenRepository.save(new VerificationToken(token, user));
    }

    @Transactional
    public void deleteVerificationToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}
