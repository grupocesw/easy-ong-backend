package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.NgoMapper;
import br.com.grupocesw.easyong.request.dtos.NgoCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.NgoUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import br.com.grupocesw.easyong.services.NgoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/ngos")
@RestController
@Api(tags = "NGO Controller")
public class NgoController {

	private final NgoService service;

	@ApiOperation(value = "Return pageable list of NGOs by default 20 items and also you can also use name, description " +
			"and social cause name filters")
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
	public Page<NgoSlimResponseDto> list(
			@RequestParam(required = false) String filter,
			@ApiIgnore final Pageable pageable) {

		if (filter != null)
			return NgoMapper.INSTANCE.listToSlimResponseDto(service.findByActivatedWithFilter(filter, pageable));
		else
			return NgoMapper.INSTANCE.listToSlimResponseDto(service.findByActivated(pageable));
	}

	@ApiOperation(value = "Return suggested pageable list of NGOs by default 5 items to logged and anonymous user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Return list successfully"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	@GetMapping(value = "/suggested")
	public Page<NgoSlimResponseDto> findSuggested(@PageableDefault(page = 0, size = 5) Pageable pageable) {
		return NgoMapper.INSTANCE.listToSlimResponseDto(service.findSuggested(pageable));
	}

	@ApiOperation(value = "Create new NGO - Endpoint only available to admin users")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiStandardResponseDto> create(@Valid @RequestBody NgoCreateRequestDto request, HttpServletRequest httpRequest) {
		NgoSlimResponseDto dto = NgoMapper.INSTANCE.entityToSlimResponseDto(
				service.create(NgoMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiStandardResponseDto.builder()
				.message("Created Ngo")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Get one NGO")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get one successfully"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(NgoMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

	@ApiOperation(value = "Update specific NGO - Endpoint only available to admin users")
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
			@RequestBody @Valid NgoUpdateRequestDto request,
			HttpServletRequest httpRequest) throws Exception {

		NgoResponseDto dto = NgoMapper.INSTANCE.entityToResponseDto(
				service.update(id, NgoMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Updated Ngo")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Delete specific NGO - Endpoint only available to admin users")
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
				.message("Deleted Ngo")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}
	 
}
