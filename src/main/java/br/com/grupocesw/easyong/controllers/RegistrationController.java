package br.com.grupocesw.easyong.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupocesw.easyong.payloads.ApiResponse;
import br.com.grupocesw.easyong.payloads.UserRequest;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/registration")
@AllArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody UserRequest request, Errors errors) {
		try {
	        if(errors.hasErrors()){
	            String errorMessage = errors.getFieldErrors().get(0).getDefaultMessage();
	            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMessage));
	        }
	        
	        registrationService.register(request);
	        
			return ResponseEntity.status(201).body(new ApiResponse(true, "Registered User. Please confirm your email."));
		} catch (UsernameAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	@GetMapping(path = "confirm")
	public ResponseEntity<?> confirm(@RequestParam("token") String token) {
		try {
			registrationService.confirmToken(token);
			return ResponseEntity.ok().body(new ApiResponse(true, "Usuário confirmado com sucesso!"));
		} catch (IllegalStateException e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Usuário já confirmado!"));
		}	
	}
}