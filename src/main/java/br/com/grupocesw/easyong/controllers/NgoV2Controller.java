package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.NgoV2Mapper;
import br.com.grupocesw.easyong.response.dtos.NgoSlimV2ResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoV2ResponseDto;
import br.com.grupocesw.easyong.services.NgoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/ngos")
@RestController
@Api(tags = "NGO V2 Controller")
public class NgoV2Controller {

	private final NgoService service;

	@ApiOperation(value = "Return pageable list of NGOs High Lights by default 20 items and also you can also use name, description " +
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
	@GetMapping("/high-lights")
	public Page<NgoSlimV2ResponseDto> highLightsList(
			@RequestParam(required = false) String filter,
			@ApiIgnore final Pageable pageable) {

		if (filter != null)
			return NgoV2Mapper.INSTANCE.listToSlimResponseDto(service.findByActivatedWithFilter(filter, pageable));
		else
			return NgoV2Mapper.INSTANCE.listToSlimResponseDto(service.findByActivated(pageable));
	}

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
	public Page<NgoV2ResponseDto> list(
			@RequestParam(required = false) String filter,
			@ApiIgnore final Pageable pageable) {

		if (filter != null)
			return NgoV2Mapper.INSTANCE.listToResponseDto(service.findByActivatedWithFilter(filter, pageable));
		else
			return NgoV2Mapper.INSTANCE.listToResponseDto(service.findByActivated(pageable));
	}

	@ApiOperation(value = "Get one NGO")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get one successfully"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoV2ResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(NgoV2Mapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

	@ApiOperation(value = "Get one NGO High Lights")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get one successfully"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 500, message = "An exception was generated")
	})
	@GetMapping(value = "/high-lights/{id}")
	public ResponseEntity<NgoSlimV2ResponseDto> highLightsRetrieve(@PathVariable Long id) {
		return ResponseEntity.ok(NgoV2Mapper.INSTANCE.entityToSlimResponseDto(service.retrieve(id)));
	}
	 
}
