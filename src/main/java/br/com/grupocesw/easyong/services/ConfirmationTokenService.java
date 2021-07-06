package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.ConfirmationToken;
import br.com.grupocesw.easyong.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ConfirmationTokenService {

    void save(ConfirmationToken token);

    ConfirmationToken findByToken(String token);

    String generateToken();

    Optional<ConfirmationToken> findByUsername(String username);

    Integer setConfirmedAt(String token);
}
