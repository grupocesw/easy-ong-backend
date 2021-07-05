package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.FaqMapper;
import br.com.grupocesw.easyong.request.dtos.FaqRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;
import br.com.grupocesw.easyong.services.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping(value = "/api/faqs")
@RestController
public class FaqController {

	private final FaqService service;

	@GetMapping
	public Page<FaqResponseDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
		if (filter == null)
			return FaqMapper.INSTANCE.listToResponseDto(service.findAll(pageable));
		else
			return FaqMapper.INSTANCE.listToResponseDto(service.findByQuestionAndAnswer(filter, pageable));
	}

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

	@GetMapping(value = "/{id}")
	public ResponseEntity<FaqResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(FaqMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

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
