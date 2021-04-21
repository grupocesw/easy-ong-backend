package br.com.grupocesw.easyong.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupocesw.easyong.dtos.UserDTO;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.payloads.ApiResponse;
import br.com.grupocesw.easyong.payloads.JwtAuthenticationResponse;
import br.com.grupocesw.easyong.payloads.LoginRequest;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.services.exceptions.UserNotCheckedException;
import br.com.grupocesw.easyong.services.exceptions.UserNotExistException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/api/auth")
@RestController
public class AuthController {

	private final UserService userService;

	@GetMapping(value = "/me")
	public ResponseEntity<?> me() {
		User user = userService.getMe();
		
		if (user == null)
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Unauthenticated user."));

		return ResponseEntity.ok(new UserDTO(user));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
			return ResponseEntity.ok(new JwtAuthenticationResponse(token));
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		} catch (UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		} catch (UserNotCheckedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, e.getMessage()));
		}
	}

	@PutMapping(value = "{ngoId}/favorite")
	public ResponseEntity<Void> favorite(@PathVariable Long ngoId) {
		userService.favorite(ngoId);
		return ResponseEntity.noContent().build();
	}
}
