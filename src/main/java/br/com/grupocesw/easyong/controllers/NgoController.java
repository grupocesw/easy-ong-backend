package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.NgoMapper;
import br.com.grupocesw.easyong.request.dtos.NgoCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.NgoUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import br.com.grupocesw.easyong.services.NgoService;
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
public class NgoController {

	private final NgoService service;
	
	@GetMapping
	public Page<NgoSlimResponseDto> list(
			@RequestParam(required = false) String filter,
			@ApiIgnore final Pageable pageable) {

		if (filter != null)
			return NgoMapper.INSTANCE.listToSlimResponseDto(service.findByActivatedWithFilter(filter, pageable));
		else
			return NgoMapper.INSTANCE.listToSlimResponseDto(service.findByActivated(pageable));
	}
	
	@GetMapping(value = "/suggested")
	public Page<NgoSlimResponseDto> findSuggested(@PageableDefault(page = 0, size = 5) Pageable pageable) {
		return NgoMapper.INSTANCE.listToSlimResponseDto(service.findSuggested(pageable));
	}
	
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
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(NgoMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}
	
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
