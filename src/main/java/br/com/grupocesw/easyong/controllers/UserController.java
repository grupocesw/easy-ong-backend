package br.com.grupocesw.easyong.controllers;

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
public class UserController {

	private final UserService service;

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de usuário"),
			@ApiResponse(code = 401, message = "Credencial inválida para acessar este recurso"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find users")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	public ResponseEntity<Page<UserResponseDto>> list(@ApiIgnore final Pageable pageable) {
		return ResponseEntity.ok(
				UserMapper.INSTANCE.listToResponseDto(service.findCheckedAll(pageable))
		);
	}

	@ResponseBody
	@PostMapping
	public ResponseEntity<ApiStandardResponseDto> create(@RequestBody @Valid UserCreateRequestDto request, HttpServletRequest httpRequest) {
		UserResponseDto dto =
			UserMapper.INSTANCE.entityToResponseDto(service.create(
					UserMapper.INSTANCE.requestDtoToEntity(request)
			));

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiStandardResponseDto.builder()
				.message("Created user")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(UserMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

	@PutMapping(value = "/{id}")
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

	@DeleteMapping(value = "/{id}")
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

}
