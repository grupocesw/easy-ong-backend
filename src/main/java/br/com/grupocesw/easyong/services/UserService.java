package br.com.grupocesw.easyong.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.repositories.UserRepository;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired private UserRepository repository;
	@Autowired private NgoService ngoService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenService jwtTokenService;
    @Autowired private JavaMailSenderService javaMailSenderService;

	public Page<User> findByChecked(Pageable pageable) {
		return repository.findByChecked(pageable);
	}
	
	public User getMe() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder. getContext(). getAuthentication().getPrincipal();

		return this.getUserByUsername(userDetails.getUsername());
	}
	
	public User getUserByUsername(String username) {
		return repository.getUserByUsername(username);
	}

	public User getOneChecked(Long id) {
		Optional<User> optional = repository.getOneChecked(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User user) {		
		try {
	        if(repository.existsByUsername(user.getUsername())) {
	            log.warn("username {} already exists.", user.getUsername());

	            throw new UsernameAlreadyExistsException(
	                    String.format("username %s already exists", user.getUsername()));
	        }
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return repository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public User update(Long id, User user) {
		try {
			User entity = this.getOneChecked(id);
			this.updateData(entity, user);

			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	private void updateData(User entity, User user) {
		entity.setName(user.getName());
		entity.setBirthday(user.getBirthday());
		entity.setGender(user.getGender());
		
		entity.getCauses().clear();	
		entity.getCauses().addAll(user.getCauses());
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
    public String login(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtTokenService.generateToken(authentication);
     }

     public User register(User user) {
         log.info("registering user {}", user.getUsername());

         if(repository.existsByUsername(user.getUsername())) {
             log.warn("username {} already exists.", user.getUsername());

             throw new UsernameAlreadyExistsException(
                     String.format("username %s already exists", user.getUsername()));
         }

         user.setPassword(passwordEncoder.encode(user.getPassword()));         
         User userRegisted = repository.save(user);

        try {
			javaMailSenderService.sendUserRegister(userRegisted);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}

         return userRegisted;
     }
     
     public Optional<User> findByUsername(String username) {
         log.info("retrieving user {}", username);
         return repository.findByUsername(username);
     }
     
 	public void favorite(Long ngoId) {
 		
		User userAuth = this.getMe();
		Ngo ngo = ngoService.findById(ngoId);
		
		if (userAuth.getFavoriteNgos().contains(ngo)) {
			userAuth.getFavoriteNgos().remove(ngo);
		} else {
			userAuth.getFavoriteNgos().add(ngo);
		}
		
		repository.save(userAuth);
	}

	public void verify(String username, String code) {
		User user = repository.getUserByUsername(username);
		
		if(user == null) {
			throw new RuntimeException("User verification failed.");
		}
		
		if(!user.getPassword().replaceAll("\\D+", "").equals(code)) {
			throw new RuntimeException("User verification failed.");
		}
		
		user.setCheckedAt(LocalDateTime.now());
		repository.save(user);
	}
	
	public void reSendVerification(String username) {
		try {
			
			User user = repository.getUserByUsername(username);

			if(user == null) {
				throw new RuntimeException("User verification failed.");
			}
			
			javaMailSenderService.sendUserRegister(user);
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}
}
