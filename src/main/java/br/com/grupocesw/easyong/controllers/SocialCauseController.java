package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.SocialCauseMapper;
import br.com.grupocesw.easyong.request.dtos.SocialCauseRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.response.dtos.SocialCauseResponseDto;
import br.com.grupocesw.easyong.services.SocialCauseService;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
	public ResponseEntity<SocialCauseResponseDto> create(@RequestBody @Valid SocialCauseRequestDto request) {
		SocialCauseResponseDto dto = SocialCauseMapper.INSTANCE.entityToResponseDto(
				service.create(SocialCauseMapper.INSTANCE.requestDtoToEntity(request))
		);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SocialCauseResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(
				SocialCauseMapper.INSTANCE.entityToResponseDto(service.retrieve(id))
		);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SocialCauseResponseDto> update(@PathVariable Long id, @RequestBody @Valid SocialCauseRequestDto request) {

		return ResponseEntity.ok(
				SocialCauseMapper.INSTANCE.entityToResponseDto(
						service.update(id, SocialCauseMapper.INSTANCE.requestDtoToEntity(request))
				)
		);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			return ResponseEntity.ok().body(new ApiResponseDto(true, String.format("Deleted Social Cause. Id %d", id)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}

}
