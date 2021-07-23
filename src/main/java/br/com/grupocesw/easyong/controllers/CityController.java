package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.CityMapper;
import br.com.grupocesw.easyong.request.dtos.CityRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.CityResponseDto;
import br.com.grupocesw.easyong.services.CityService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cities")
@Api(tags = "City Controller")
public class CityController {

	private final CityService service;

	@ApiOperation(value = "Return pageable list of cities by default 20 items and also you can also use question and answer filters")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Return list successfully"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@GetMapping
	public Page<CityResponseDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
		if (filter == null)
			return CityMapper.INSTANCE.listToResponseDto(service.findAll(pageable));
		else
			return CityMapper.INSTANCE.listToResponseDto(service.findByName(filter, pageable));
	}

	@ApiOperation(value = "Create new city - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiStandardResponseDto> create(@RequestBody @Valid CityRequestDto request, HttpServletRequest httpRequest) {
		CityResponseDto dto = CityMapper.INSTANCE.entityToResponseDto(
				service.create(CityMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiStandardResponseDto.builder()
				.message("Created City")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Get one city")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get one successfully"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<CityResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(CityMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

	@ApiOperation(value = "Update specific city - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Updated successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiStandardResponseDto> update(
			@PathVariable Long id,
			@RequestBody @Valid CityRequestDto request,
			HttpServletRequest httpRequest) {

		CityResponseDto dto = CityMapper.INSTANCE.entityToResponseDto(
				service.update(id, CityMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Updated city")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Delete specific City - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Deleted successfully"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiStandardResponseDto> delete(@PathVariable Long id, HttpServletRequest httpRequest) {
		service.delete(id);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Deleted city")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

}
