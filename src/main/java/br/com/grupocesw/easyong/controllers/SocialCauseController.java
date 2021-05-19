package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.grupocesw.easyong.request.dtos.SocialCauseRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.response.dtos.SocialCauseResponseDto;
import br.com.grupocesw.easyong.services.SocialCauseService;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/api/social-causes")
@RestController
public class SocialCauseController {

	private final SocialCauseService service;
	
	@GetMapping
	public Page<SocialCauseResponseDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
		if (filter == null)
			return service.findAll(pageable);
		else
			return service.findByName(filter, pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SocialCauseResponseDto> create(@RequestBody @Valid SocialCauseRequestDto request) {
		
		SocialCauseResponseDto socialCauseDTO = service.create(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(socialCauseDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(socialCauseDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SocialCauseResponseDto> retrieve(@PathVariable Long id) {
		return ResponseEntity.ok(service.retrieve(id));
	}
	
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SocialCauseResponseDto> update(@PathVariable Long id, @RequestBody @Valid SocialCauseRequestDto request) {
		SocialCauseResponseDto socialCauseDto = service.update(id, request);
		return ResponseEntity.ok().body(socialCauseDto);
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
