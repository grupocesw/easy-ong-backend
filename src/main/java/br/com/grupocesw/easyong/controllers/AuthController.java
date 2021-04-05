package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.mail.AuthenticationFailedException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.dto.UserDTO;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.payload.ApiResponse;
import br.com.grupocesw.easyong.payload.JwtAuthenticationResponse;
import br.com.grupocesw.easyong.payload.LoginRequest;
import br.com.grupocesw.easyong.payload.RegisterRequest;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = "/api/auth")
@RestController
@Slf4j
public class AuthController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/me")
	public ResponseEntity<UserDTO> me() {

		UserDTO userDTO = new UserDTO(userService.getMe());

		return ResponseEntity.ok(userDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
			return ResponseEntity.ok(new JwtAuthenticationResponse(token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "User or Password Incorrect."));
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@Valid @RequestBody LoginRequest loginRequest) {
		String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest payload) throws AuthenticationFailedException {
		log.info("creating user {}", payload.getUsername());

		User user = User.builder().name(payload.getName()).username(payload.getUsername())
				.password(payload.getPassword()).birthday(payload.getBirthday()).gender(payload.getGender()).build();

		try {
			userService.register(user);

			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
					.buildAndExpand(user.getUsername()).toUri();

			return ResponseEntity.created(location).body(new ApiResponse(true, user.getUsername()));
		} catch (UsernameAlreadyExistsException e) {			
          return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	@GetMapping(value = "/verify/{username}/{code}")
	public ResponseEntity<?> verifyUser(@PathVariable String username, @PathVariable String code) {
		try {
			userService.verify(username, code);
			return ResponseEntity.ok().body(new ApiResponse(true, "Account verificated with success!"));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}		
	}
	
	@GetMapping(value = "/resend-verfication/{username}")
	public ResponseEntity<?> resendVerfication(@PathVariable String username) {
		try {
			userService.reSendVerification(username);
			return ResponseEntity.ok().body(new ApiResponse(true, "E-mail sent with success!"));
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}		
	}

	@PutMapping(value = "{ngoId}/favorite")
	public ResponseEntity<Void> favorite(@PathVariable Long ngoId) {
		userService.favorite(ngoId);
		return ResponseEntity.noContent().build();
	}
}