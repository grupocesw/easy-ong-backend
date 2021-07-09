package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.NgoMapper;
import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.LoginRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserPasswordRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUsernameRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.JwtAuthenticationResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
import br.com.grupocesw.easyong.security.CurrentUser;
import br.com.grupocesw.easyong.security.UserPrincipal;
import br.com.grupocesw.easyong.services.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
@RestController
@Api(tags = "Auth Controller")
public class AuthController {

	private final UserService service;

	@ApiOperation(value = "Authentication user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Authentication successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data | " +
					"User not exists"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
		return ResponseEntity.ok(
				service.login(UserMapper.INSTANCE.requestDtoToEntity(request))
		);
	}

	@ApiOperation(value = "Get information logged user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get informations successfully"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@GetMapping("/me")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiStandardResponseDto> getCurrentUser(@CurrentUser UserPrincipal userPrincipal, HttpServletRequest httpRequest) {

		UserResponseDto dto = UserMapper.INSTANCE.entityToResponseDto(service.retrieve(userPrincipal.getId()));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Authenticated user")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Update logged user profile")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Updated successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PutMapping(value = "/me/update")
	public ResponseEntity<ApiStandardResponseDto> updateProfile(@CurrentUser UserPrincipal userPrincipal, @RequestBody @Valid UserUpdateRequestDto request, HttpServletRequest httpRequest) {
		UserResponseDto dto = UserMapper.INSTANCE.entityToResponseDto(
				service.update(userPrincipal.getId(), UserMapper.INSTANCE.requestDtoToEntity(request)));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Updated logged user")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Change password user in your profile")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Changed password successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data | " +
					"User not exists | " +
					"Email already sent. Please wait 15 minutes for new request | " +
					"Passwords must match | " +
					"Password must contain uppercase, lowercase, number and special character | " +
					"Password must contain between 6 and 100 characters"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PutMapping(value = "/change-password")
	public ResponseEntity<ApiStandardResponseDto> changePassword(@CurrentUser UserPrincipal userPrincipal, @RequestBody @Valid UserPasswordRequestDto request, HttpServletRequest httpRequest) {
		service.changePassword(userPrincipal.getId(), UserMapper.INSTANCE.requestDtoToEntity(request));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Success. Password changed")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Resend email for change password")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Email sent successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data | " +
					"User not exists | " +
					"Email already sent. Please wait 15 minutes for new request"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PostMapping(value = "/recover-password")
	public ResponseEntity<ApiStandardResponseDto> recoverPassword(@RequestBody @Valid UserUsernameRequestDto request, HttpServletRequest httpRequest) {
		service.recoverPassword(UserMapper.INSTANCE.requestDtoToEntity(request));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Success. Sent email with instructions to recover password")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Favorite or remove favorite NGO to logged user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "User logged into the system executes the action successfully"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 404, message = "Ngo not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PutMapping(value = "/favorite-ngos/{ngoId}")
    @PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiStandardResponseDto> favorite(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long ngoId, HttpServletRequest httpRequest) {
		service.favorite(userPrincipal.getId(), ngoId);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message(String.format("Success. Action applied Ngo. Id %d", ngoId))
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Return pageable list of NGOs by default 20 items")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Return list successfully"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@GetMapping(value = "/favorite-ngos")
    @PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Page<NgoSlimResponseDto>> favoriteNgos(
			@CurrentUser UserPrincipal userPrincipal,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);

		return ResponseEntity.ok(NgoMapper.INSTANCE.listToSlimResponseDto(
				service.getFavoriteNgos(userPrincipal.getId(), PageRequest.of(currentPage - 1, pageSize))
		));
	}
}
