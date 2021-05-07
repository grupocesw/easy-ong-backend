package br.com.grupocesw.easyong.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.ConfirmationToken;

@Service
public interface ConfirmationTokenService {

    public void saveConfirmationToken(ConfirmationToken token);

    public Optional<ConfirmationToken> getToken(String token);
    
    public Optional<ConfirmationToken> findByUsername(String username);

    public Integer setConfirmedAt(String token);
}
