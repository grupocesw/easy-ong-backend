package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ConfirmationTokenService {

    ConfirmationToken save(ConfirmationToken token);

    ConfirmationToken findByToken(String token);

    String generateToken();

    Optional<ConfirmationToken> findByUsernameNotExpiratedAndNotConfirmed(String username);

    Integer setConfirmedAt(String token);
}
