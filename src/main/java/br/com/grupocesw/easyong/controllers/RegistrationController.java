package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUsernameRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
import br.com.grupocesw.easyong.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/registration")
public class RegistrationController {

	private final RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<ApiStandardResponseDto> register(@RequestBody @Valid UserCreateRequestDto request, HttpServletRequest httpRequest) {
		UserResponseDto dto =
			UserMapper.INSTANCE.entityToResponseDto(registrationService.register(
					UserMapper.INSTANCE.requestDtoToEntity(request)
			));

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiStandardResponseDto.builder()
				.message("Registered User. Please verify your email")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}
	
	@PostMapping(path = "resend-confirmation")
	public ResponseEntity<ApiStandardResponseDto> resendConfirmation(@RequestBody @Valid UserUsernameRequestDto request, HttpServletRequest httpRequest) {
		registrationService.resendConfirmation(UserMapper.INSTANCE.requestDtoToEntity(request));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Confirmation sent. Please verify your email")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

}