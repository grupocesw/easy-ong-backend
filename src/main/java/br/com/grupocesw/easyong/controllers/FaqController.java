package br.com.grupocesw.easyong.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.grupocesw.easyong.request.dtos.FaqCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.FaqUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.ApiResponseDto;
import br.com.grupocesw.easyong.response.dtos.FaqResponseDto;
import br.com.grupocesw.easyong.services.FaqService;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/api/faqs")
@RestController
public class FaqController {

	private FaqService service;
	
	@GetMapping
	public Page<FaqResponseDto> list(@RequestParam(required = false) String filter, Pageable pageable) {
		if (filter == null)
			return service.findAll(pageable);
		else
			return service.findByQuestionAndAnswer(filter, pageable);
	}
	
	@PostMapping
	public ResponseEntity<FaqResponseDto> create(@RequestBody @Valid FaqCreateRequestDto request) {
		
		FaqResponseDto faqDTO = service.create(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(faqDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(faqDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<FaqResponseDto> retrieve(@PathVariable Long id) {
		FaqResponseDto faqDto = service.retrieve(id);
		return ResponseEntity.ok(faqDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<FaqResponseDto> update(@PathVariable Long id, @RequestBody @Valid FaqUpdateRequestDto request) {
		FaqResponseDto faqDto = service.update(id, request);
		return ResponseEntity.ok().body(faqDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			service.delete(id);
			return ResponseEntity.ok().body(new ApiResponseDto(true, String.format("Deleted FAQ. Id %d", id)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiResponseDto(false, e.getMessage()));
		}
	}
	 
}
