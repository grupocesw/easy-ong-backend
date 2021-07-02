package br.com.grupocesw.easyong.controllers;

import br.com.grupocesw.easyong.mappers.FaqMapper;
import br.com.grupocesw.easyong.request.dtos.FaqRequestDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;
import br.com.grupocesw.easyong.services.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
	public ResponseEntity<FaqResponseDto> create(@RequestBody @Valid FaqRequestDto request) {

		FaqResponseDto dto = FaqMapper.INSTANCE.entityToResponseDto(
				service.create(FaqMapper.INSTANCE.requestDtoToEntity(request))
		);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<FaqResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(
				FaqMapper.INSTANCE.entityToResponseDto(service.retrieve(id))
		);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<FaqResponseDto> update(@PathVariable Long id, @RequestBody @Valid FaqRequestDto request) {
		return ResponseEntity.ok(FaqMapper.INSTANCE.entityToResponseDto(
				service.update(id, FaqMapper.INSTANCE.requestDtoToEntity(request))
		));
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
