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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.dtos.NgoDTO;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@AllArgsConstructor
@RequestMapping(value = "/api/ngos")
@RestController
public class NgoController {

	private NgoService service;
	
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de ONG"),
	    @ApiResponse(code = 401, message = "Credencial inválida para acessar este recurso"),
	    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Find ngos")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported.")
    })
	public Page<NgoDTO> list(@ApiIgnore final Pageable pageable) {
		final Page<Ngo> ngos = service.findByActivated(pageable);

		return ngos.map(ngo -> new NgoDTO(ngo));
	}
	
	@GetMapping(value = "/suggested")
	public Page<NgoDTO> findSuggested(@PageableDefault(page = 0, size = 5) Pageable pageable) {
		Page<Ngo> ngos = service.findSuggested(pageable);

		return ngos.map(ngo -> new NgoDTO(ngo));
	}
	
	@PostMapping
	public ResponseEntity<NgoDTO> create(@Valid @RequestBody Ngo ngo) {
		NgoDTO ngoDTO = new NgoDTO(service.create(ngo));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(ngoDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(ngoDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoDTO> retrieve(@PathVariable Long id) {		
		return ResponseEntity.ok(new NgoDTO(service.retrieve(id)));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Ngo ngo, Errors errors) {		
		try {
			return ResponseEntity.ok()
					.body(new NgoDTO(service.update(id, ngo)));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.badRequest().body(new br.com.grupocesw.easyong.payloads.ApiResponse(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new br.com.grupocesw.easyong.payloads.ApiResponse(false, e.getMessage()));
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	 
}
