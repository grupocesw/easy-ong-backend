package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.exceptions.ExpiredTokenRequestException;
import br.com.grupocesw.easyong.exceptions.InvalidTokenException;
import br.com.grupocesw.easyong.exceptions.TokenNotFoundException;
import br.com.grupocesw.easyong.repositories.ConfirmationTokenRepository;
import br.com.grupocesw.easyong.services.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void save(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public ConfirmationToken findByToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException());

        if (confirmationToken.getConfirmedAt() != null)
            throw new InvalidTokenException();

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ExpiredTokenRequestException();
        }

        return confirmationToken;
    }

    @Override
    public Integer setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

	@Override
	public Optional<ConfirmationToken> findByUsername(String username) {
		return confirmationTokenRepository.findDistinctFirstByUsername(username);
	}
}