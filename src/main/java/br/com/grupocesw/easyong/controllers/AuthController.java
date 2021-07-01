package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.NgoMapper;
import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.LoginRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.exceptions.UnauthenticatedUserException;
import br.com.grupocesw.easyong.exceptions.UserNotConfirmedException;
import br.com.grupocesw.easyong.exceptions.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
@RestController
public class AuthController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto requestDto) {
		try {
			return ResponseEntity.ok(userService.login(requestDto));
		} catch (UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (UserNotConfirmedException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@GetMapping(value = "/me")
	public ResponseEntity<?> me() {		
		try {
			return ResponseEntity.ok(
					UserMapper.INSTANCE.entityToResponseDto(userService.getMe())
			);
		} catch (UnauthenticatedUserException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@PutMapping(value = "/me/update")
	public ResponseEntity<?> updateProfile(@RequestBody @Valid UserUpdateRequestDto request) {
		try {
			return ResponseEntity.ok(UserMapper.INSTANCE.entityToResponseDto(
					userService.updateMe(UserMapper.INSTANCE.requestDtoToEntity(request))
			));
		} catch (UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@PutMapping(value = "/change-password")
	public ResponseEntity<?> changePassword(@RequestBody @Valid UserPasswordRequestDto request) {
		try {
			userService.changePassword(request);
			
			return ResponseEntity.ok().body(new ApiResponseDto(true, "Password changed."));
		} catch (UserNotExistException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@PutMapping(value = "/favorite-ngos/{ngoId}")
	public ResponseEntity<?> favorite(@PathVariable Long ngoId) {
		try {
			userService.favorite(ngoId);			
			return ResponseEntity.ok().body(new ApiResponseDto(true, String.format("Action applied Ngo. Id %d", ngoId)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@GetMapping(value = "/favorite-ngos")
	public ResponseEntity<?> favoriteNgos(
			@RequestParam("page") Optional<Integer> page, 
		      @RequestParam("size") Optional<Integer> size) {
		try {
			int currentPage = page.orElse(1);
	        int pageSize = size.orElse(5);
	        
			return ResponseEntity.ok(
					NgoMapper.INSTANCE.listToSlimResponseDto(
						userService.getFavoriteNgos(PageRequest.of(currentPage - 1, pageSize))
					)
			);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}
}
