package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.mappers.UserMapper;
import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
import br.com.grupocesw.easyong.services.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(value = "/api/users")
@Api(tags = "User Controller")
public class UserController {

	private final UserService service;

	@ApiOperation(value = "Return pageable list of users by default 20 items - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Return list with success"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@GetMapping("v1")
	public ResponseEntity<Page<UserResponseDto>> list(@ApiIgnore final Pageable pageable) {
		return ResponseEntity.ok(
				UserMapper.INSTANCE.listToResponseDto(service.findCheckedAll(pageable))
		);
	}

	@ApiOperation(value = "Create new enabled user by default - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created user successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data | Username already exists"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PostMapping("v1")
	public ResponseEntity<ApiStandardResponseDto> create(@RequestBody @Valid UserCreateRequestDto request, HttpServletRequest httpRequest) {
		User user = UserMapper.INSTANCE.requestDtoToEntity(request);
		user.setEnabled(true);

		UserResponseDto dto = UserMapper.INSTANCE.entityToResponseDto(service.create(user));

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiStandardResponseDto.builder()
				.message("Created user")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Get one user - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get one successfully"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@GetMapping(value = "v1/{id}")
	public ResponseEntity<UserResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(UserMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

	@ApiOperation(value = "Update specific user - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Updated successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PutMapping(value = "v1/{id}")
	public ResponseEntity<ApiStandardResponseDto> update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto request, HttpServletRequest httpRequest) {
		UserResponseDto dto = UserMapper.INSTANCE.entityToResponseDto(
				service.update(id, UserMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Updated user")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Delete specific user - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Deleted successfully"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@DeleteMapping(value = "v1/{id}")
	public ResponseEntity<ApiStandardResponseDto> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
		service.delete(id);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Deleted user")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Enable specific user - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Enabled successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PutMapping(value = "v1/{id}/enable")
	public ResponseEntity<ApiStandardResponseDto> enable(@PathVariable Long id, HttpServletRequest httpRequest) {
		UserResponseDto dto = UserMapper.INSTANCE.entityToResponseDto(service.enable(service.retrieve(id)));

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Enabled user")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

}
