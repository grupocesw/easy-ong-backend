package br.com.grupocesw.easyong.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.UserAuthDetails;

@Service
public class UserAuthDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userService
            .findByUsername(username)
            .map(UserAuthDetails::new)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
