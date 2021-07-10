package br.com.grupocesw.easyong.security;


import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.exceptions.UserNotExistException;
import br.com.grupocesw.easyong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UserNotExistException {
        User user = userService.findByUsernameOrThrowUserNotExistException(username);

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userService.retrieve(id);

        return UserPrincipal.create(user);
    }
}