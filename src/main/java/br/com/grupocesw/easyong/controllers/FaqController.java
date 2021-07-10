package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.FaqMapper;
import br.com.grupocesw.easyong.request.dtos.FaqRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;
import br.com.grupocesw.easyong.services.FaqService;
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
@RequestMapping(value = "/api/faqs")
@Api(tags = "FAQ Controller")
public class FaqController {

	private final FaqService service;

	@ApiOperation(value = "Return pageable list of FAQs by default 20 items and also you can also use question and answer filters")
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
	public Page<FaqResponseDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
		if (filter == null)
			return FaqMapper.INSTANCE.listToResponseDto(service.findAll(pageable));
		else
			return FaqMapper.INSTANCE.listToResponseDto(service.findByQuestionAndAnswer(filter, pageable));
	}

	@ApiOperation(value = "Create new FAQ")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Created successfully"),
			@ApiResponse(code = 400, message = "Validation failed for arguments or error input data"),
			@ApiResponse(code = 401, message = "Invalid credential to access this resource"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiStandardResponseDto> create(@RequestBody @Valid FaqRequestDto request, HttpServletRequest httpRequest) {
		FaqResponseDto dto = FaqMapper.INSTANCE.entityToResponseDto(
				service.create(FaqMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiStandardResponseDto.builder()
				.message("Created Faq")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Get one FAQ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get one successfully"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<FaqResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(FaqMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

	@ApiOperation(value = "Update specific FAQ")
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
			@RequestBody @Valid FaqRequestDto request,
			HttpServletRequest httpRequest) {

		FaqResponseDto dto = FaqMapper.INSTANCE.entityToResponseDto(
				service.update(id, FaqMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Updated Faq")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@ApiOperation(value = "Delete specific FAQ")
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
				.message("Deleted Faq")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

}
