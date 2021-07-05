package br.com.grupocesw.easyong.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.ConfirmationToken;

@Service
public interface ConfirmationTokenService {

    void save(ConfirmationToken token);

    Optional<ConfirmationToken> getToken(String token);
    
    Optional<ConfirmationToken> findByUsername(String username);

    Integer setConfirmedAt(String token);
}
