package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoSlimResponseDto;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@AllArgsConstructor
@RequestMapping(value = "/api/ngos")
@RestController
public class NgoController {

	private NgoService service;
	
	@GetMapping
	public Page<NgoSlimResponseDto> list(@ApiIgnore final Pageable pageable) {
		final Page<Ngo> ngos = service.findByActivated(pageable);

		return ngos.map(ngo -> new NgoSlimResponseDto(ngo));
	}
	
	@GetMapping(value = "/suggested")
	public Page<NgoSlimResponseDto> findSuggested(@PageableDefault(page = 0, size = 5) Pageable pageable) {
		final Page<Ngo> ngos = service.findSuggested(pageable);

		return ngos.map(ngo -> new NgoSlimResponseDto(ngo));
	}
	
	@PostMapping
	public ResponseEntity<NgoResponseDto> create(@Valid @RequestBody Ngo ngo) {
		NgoResponseDto ngoDTO = new NgoResponseDto(service.create(ngo));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(ngoDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(ngoDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoResponseDto> retrieve(@PathVariable Long id) {		
		return ResponseEntity.ok(new NgoResponseDto(service.retrieve(id)));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Ngo ngo, Errors errors) {		
		try {
			return ResponseEntity.ok()
					.body(new NgoResponseDto(service.update(id, ngo)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new br.com.grupocesw.easyong.response.dtos.ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new br.com.grupocesw.easyong.response.dtos.ApiResponseDto(false, e.getMessage()));
		}
	}
	
	@DeleteMapping(value = "/{id}")
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
