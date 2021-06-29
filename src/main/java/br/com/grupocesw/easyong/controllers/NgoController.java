package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.validation.Valid;

import br.com.grupocesw.easyong.mappers.NgoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.request.dtos.NgoRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.exceptions.BadRequestException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@AllArgsConstructor
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
	public ResponseEntity<?> create(@Valid @RequestBody NgoRequestDto request) {
		try {
			NgoSlimResponseDto dto = NgoMapper.INSTANCE.entityToSlimResponseDto(
					service.create(NgoMapper.INSTANCE.requestDtoToEntity(request))
			);

			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(dto.getId()).toUri();

			return ResponseEntity.created(uri).body(dto);
		} catch (BadRequestException|IllegalArgumentException e) {			
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(
				NgoMapper.INSTANCE.entityToResponseDto(service.retrieve(id))
		);
	}
	
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid NgoRequestDto request) {
		try {
			return ResponseEntity.ok(NgoMapper.INSTANCE.entityToResponseDto(
					service.update(id, NgoMapper.INSTANCE.requestDtoToEntity(request))
			));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			return ResponseEntity.ok().body(new ApiResponseDto(true, String.format("Deleted NGO. Id %d", id)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	 
}
