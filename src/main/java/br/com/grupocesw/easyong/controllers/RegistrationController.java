package br.com.grupocesw.easyong.controllers;

import javax.persistence.NonUniqueResultException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.services.exceptions.UserNotExistException;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/registration")
@AllArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<?> register(@RequestBody @Valid UserCreateRequestDto request) {
		try {	        
	        registrationService.register(request.build());
	        
			return ResponseEntity.status(201).body(new ApiResponseDto(true, "Registered User. Please confirm your email."));
		} catch (UsernameAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@GetMapping(path = "resend-confirmation/{username}")
	public ResponseEntity<?> resendConfirmation(@PathVariable String username) {
		try {
			registrationService.resendConfirmation(username);
			return ResponseEntity.ok().body(new ApiResponseDto(true, "Confirmation sent. Please confirm your email."));
		} catch (UsernameAlreadyExistsException|UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (NonUniqueResultException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, "User already confirmed."));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@GetMapping(path = "confirm/{token}")
	public ResponseEntity<?> confirm(@PathVariable String token) {
		try {
			registrationService.confirmToken(token);
			return ResponseEntity.ok().body(new ApiResponseDto(true, "Usuário confirmado com sucesso!"));
		} catch (UsernameAlreadyExistsException|UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}	
	}
}