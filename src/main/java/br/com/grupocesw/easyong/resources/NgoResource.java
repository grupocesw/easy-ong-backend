package br.com.grupocesw.easyong.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.dto.NgoDTO;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.services.NgoService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/api/ngos")
@RestController
public class NgoResource {

	@Autowired
	private NgoService us;
	
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de ONG"),
	    @ApiResponse(code = 401, message = "Credencial inválida para acessar este recurso"),
	    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping
	public ResponseEntity<List<NgoDTO>> list() {
		List<Ngo> ngos = us.findAll();
		
		List<NgoDTO> ngosDTO = ngos.stream()
				.map(o -> new NgoDTO(o))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(ngosDTO);
	}
	
	@PostMapping
	public ResponseEntity<NgoDTO> create(@RequestBody Ngo ngo) {
		
		Ngo ngoSalvo = us.insert(ngo);
		
		NgoDTO ngoDTO = new NgoDTO(ngoSalvo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(ngoDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(ngoDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NgoDTO> retrieve(@PathVariable Long id) {
		Ngo ngo = us.findById(id);
		
		NgoDTO ngoDTO = new NgoDTO(ngo);
		
		return ResponseEntity.ok(ngoDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<NgoDTO> update(@PathVariable Long id, @RequestBody Ngo ngo) {
		ngo = us.update(id, ngo);
		
		NgoDTO ngoDTO = new NgoDTO(ngo);
		
		return ResponseEntity.ok().body(ngoDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		us.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	 
}
