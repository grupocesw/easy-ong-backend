package br.com.grupocesw.easyong.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.repositories.ConfirmationTokenRepository;
import br.com.grupocesw.easyong.services.ConfirmationTokenService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public Integer setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

	@Override
	public Optional<ConfirmationToken> findByUsername(String username) {
		return confirmationTokenRepository.findDistinctFirstByUsername(username);
	}
}