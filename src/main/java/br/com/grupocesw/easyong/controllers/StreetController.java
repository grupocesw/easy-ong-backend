package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.request.dtos.StreetCreateRequestDto;
import br.com.grupocesw.easyong.response.dtos.StreetResponseDto;
import br.com.grupocesw.easyong.services.StreetService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/api/streets")
@RestController
public class StreetController {

	private StreetService service;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<StreetResponseDto> create(@RequestBody @Valid StreetCreateRequestDto request) {
		
		StreetResponseDto streetDto = service.create(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(streetDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(streetDto);
	}
	 
}
