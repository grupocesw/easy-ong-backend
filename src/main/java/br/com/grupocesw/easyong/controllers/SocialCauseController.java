package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.SocialCauseMapper;
import br.com.grupocesw.easyong.request.dtos.SocialCauseRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiStandardResponseDto;
import br.com.grupocesw.easyong.response.dtos.SocialCauseResponseDto;
import br.com.grupocesw.easyong.services.SocialCauseService;
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
@RequestMapping(value = "/api/social-causes")
@RestController
public class SocialCauseController {

	private final SocialCauseService service;

	@GetMapping
	public Page<SocialCauseResponseDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
		if (filter == null)
			return SocialCauseMapper.INSTANCE.listToResponseDto(service.findAll(pageable));
		else
			return SocialCauseMapper.INSTANCE.listToResponseDto(service.findByName(filter, pageable));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiStandardResponseDto> create(@RequestBody @Valid SocialCauseRequestDto request, HttpServletRequest httpRequest) {
		SocialCauseResponseDto dto = SocialCauseMapper.INSTANCE.entityToResponseDto(
				service.create(SocialCauseMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(ApiStandardResponseDto.builder()
				.message("Created Social Cause")
				.data(dto)
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SocialCauseResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(SocialCauseMapper.INSTANCE.entityToResponseDto(service.retrieve(id)));
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiStandardResponseDto> update(
			@PathVariable Long id,
			@RequestBody @Valid SocialCauseRequestDto request,
			HttpServletRequest httpRequest) {

		SocialCauseResponseDto dto = SocialCauseMapper.INSTANCE.entityToResponseDto(
				service.update(id, SocialCauseMapper.INSTANCE.requestDtoToEntity(request))
		);

		return ResponseEntity
			.status(HttpStatus.OK)
			.body(ApiStandardResponseDto.builder()
				.message("Updated Social Cause")
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
				.message("Deleted Social Cause")
				.path(httpRequest.getRequestURI())
				.build()
			);
	}

}
