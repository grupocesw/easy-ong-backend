package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
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
import br.com.grupocesw.easyong.response.dtos.NgoFullResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.exceptions.BadRequestException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@AllArgsConstructor
@RequestMapping(value = "/api/ngos")
@RestController
public class NgoController {

	private NgoService service;
	
	@GetMapping
	public Page<NgoResponseDto> list(
			@RequestParam(required = false) String filter,
			@ApiIgnore final Pageable pageable) {
			return service.findByActivated(filter, pageable);
	}
	
	@GetMapping(value = "/full")
	public Page<NgoFullResponseDto> fullList(
			@RequestParam(required = false) String filter,
			@ApiIgnore final Pageable pageable) {
		return service.findByActivatedFull(filter, pageable);
	}
	
	@GetMapping(value = "/suggested")
	public Page<NgoResponseDto> findSuggested(@PageableDefault(page = 0, size = 5) Pageable pageable) {
		return service.findSuggested(pageable);
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody NgoRequestDto request) {
		try {
			NgoResponseDto ngoDto = service.create(request);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(ngoDto.getId()).toUri();
			
			return ResponseEntity.created(uri).body(ngoDto);
		} catch (BadRequestException|IllegalArgumentException e) {			
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		}		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoFullResponseDto> retrieve(@PathVariable Long id) {		
		return ResponseEntity.ok(service.retrieve(id));
	}
	
	@PutMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid NgoRequestDto request, Errors errors) {		
		try {
			return ResponseEntity.ok().body(service.update(id, request));
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
			return ResponseEntity.ok().body(new ApiResponseDto(true, String.format("Deleted ngo. Id %d", id)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	 
}
