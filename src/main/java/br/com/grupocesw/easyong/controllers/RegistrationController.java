package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.services.RegistrationService;
import br.com.grupocesw.easyong.exceptions.UserNotExistException;
import br.com.grupocesw.easyong.exceptions.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/registration")
public class RegistrationController {

	private final RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<?> register(@RequestBody @Valid UserCreateRequestDto request) {
		try {	        
	        registrationService.register(
					UserMapper.INSTANCE.requestDtoToEntity(request)
			);
	        
			return ResponseEntity.status(201).body(
					new ApiResponseDto(true, "Registered User. Please confirm your email"));
		} catch (UsernameAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@GetMapping(path = "resend-confirmation/{username}")
	public ResponseEntity<?> resendConfirmation(@PathVariable String username) {
		try {
			registrationService.resendConfirmation(username);
			return ResponseEntity.ok().body(new ApiResponseDto(true, "Confirmation sent. Please confirm your email"));
		} catch (UsernameAlreadyExistsException|UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (NonUniqueResultException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, "User already confirmed"));
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