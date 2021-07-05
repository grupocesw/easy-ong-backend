package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.NgoMapper;
import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.LoginRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
import br.com.grupocesw.easyong.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
@RestController
public class AuthController {

	private final UserService service;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
		return ResponseEntity.ok(
				service.login(UserMapper.INSTANCE.requestDtoToEntity(request))
		);
	}

	@GetMapping(value = "/me")
	public ResponseEntity<ApiStandardResponseDto> me(HttpServletRequest httpRequest) {
		UserResponseDto dto = UserMapper.INSTANCE.entityToResponseDto(service.getMe());

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Authenticated user")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}
	
	@PutMapping(value = "/me/update")
	public ResponseEntity<ApiStandardResponseDto> updateProfile(@RequestBody @Valid UserUpdateRequestDto request, HttpServletRequest httpRequest) {
		UserResponseDto dto = UserMapper.INSTANCE.entityToResponseDto(
				service.updateMe(UserMapper.INSTANCE.requestDtoToEntity(request)));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Update user profile")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}
	
	@PutMapping(value = "/change-password")
	public ResponseEntity<ApiStandardResponseDto> changePassword(@RequestBody @Valid UserPasswordRequestDto request, HttpServletRequest httpRequest) {
		service.changePassword(UserMapper.INSTANCE.requestDtoToEntity(request));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Password changed")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@PutMapping(value = "/favorite-ngos/{ngoId}")
	public ResponseEntity<ApiStandardResponseDto> favorite(@PathVariable Long ngoId, HttpServletRequest httpRequest) {
		service.favorite(ngoId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message(String.format("Action applied Ngo. Id %d", ngoId))
				.path(httpRequest.getRequestURI())
				.build()
			);
	}
	
	@GetMapping(value = "/favorite-ngos")
	public ResponseEntity<Page<NgoSlimResponseDto>> favoriteNgos(
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		return ResponseEntity.ok(NgoMapper.INSTANCE.listToSlimResponseDto(
				service.getFavoriteNgos(PageRequest.of(currentPage - 1, pageSize))
		));
	}
}
