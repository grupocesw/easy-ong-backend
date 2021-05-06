package br.com.grupocesw.easyong.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.payloads.JwtAuthenticationResponse;
import br.com.grupocesw.easyong.payloads.LoginRequest;
import br.com.grupocesw.easyong.request.dtos.AuthRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.services.exceptions.UnauthenticatedUserException;
import br.com.grupocesw.easyong.services.exceptions.UserNotConfirmedException;
import br.com.grupocesw.easyong.services.exceptions.UserNotExistException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/api/auth")
@RestController
public class AuthController {

	private final UserService userService;

	@GetMapping(value = "/me")
	public ResponseEntity<?> me() {		
		try {
			return ResponseEntity.ok(new UserResponseDto(userService.getMe()));
		} catch (UnauthenticatedUserException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@PutMapping(value = "/update-me")
	public ResponseEntity<?> updateProfile(@RequestBody @Valid UserUpdateRequestDto request, Errors errors) {
		try {
			User user = userService.updateMe(request.build());
			return ResponseEntity.ok().body(new UserResponseDto(user));
		} catch (UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@PutMapping(value = "/change-password")
	public ResponseEntity<?> changePassword(@RequestBody @Valid AuthRequestDto request, Errors errors) {
		try {
			User user = userService.changePassword(request.build());
			return ResponseEntity.ok().body(
				new ApiResponseDto(true, "Password changed. Username: " + user.getUsername())
			);
		} catch (UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
			return ResponseEntity.ok(new JwtAuthenticationResponse(token));
		} catch (UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (UserNotConfirmedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@PutMapping(value = "{ngoId}/favorite")
	public ResponseEntity<?> favorite(@PathVariable Long ngoId) {
		try {
			userService.favorite(ngoId);
			return ResponseEntity.ok().body(new ApiResponseDto(true, String.format("Ngo favorited. Id %d", ngoId)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}
}
