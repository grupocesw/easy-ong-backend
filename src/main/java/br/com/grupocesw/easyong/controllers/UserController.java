package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.request.dtos.UserCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.UserUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.response.dtos.UserResponseDto;
import br.com.grupocesw.easyong.services.UserService;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.services.exceptions.UsernameAlreadyExistsException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/users")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

	private UserService service;

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
		return ResponseEntity.ok().body(service.findCheckedAll(pageable));
	}

	@ResponseBody
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid UserCreateRequestDto request) {
		try {
			UserResponseDto userDto = service.create(request);
	
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(userDto.getId())
					.toUri();
	
			return ResponseEntity.created(uri).body(userDto);
		} catch (UsernameAlreadyExistsException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> retrieve(@PathVariable Long id) {		
		try {
			return ResponseEntity.ok().body(service.retrieve(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto request, Errors errors) {
		try {
			return ResponseEntity.ok().body(service.update(id, request));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {		
		try {
			service.delete(id);
			return ResponseEntity.ok().body(new ApiResponseDto(true, String.format("Deleted user. Id %d", id)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}

}
