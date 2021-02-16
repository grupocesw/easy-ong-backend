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

import br.com.grupocesw.easyong.dto.UserDTO;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/api/users")
@RestController
public class UserResource {

	@Autowired
	private UserService service;
	
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Retorna a lista de usuário"),
	    @ApiResponse(code = 401, message = "Credencial inválida para acessar este recurso"),
	    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
	    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping
	public ResponseEntity<List<UserDTO>> list() {
		List<User> users = service.findAll();
		
		List<UserDTO> usersDTO = users.stream()
				.map(o -> new UserDTO(o))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(usersDTO);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody User user) {
		
		User userSalved = service.insert(user);
		
		UserDTO userDTO = new UserDTO(userSalved);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(userDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> retrieve(@PathVariable Long id) {
		User user = service.findById(id);
		
		UserDTO userDTO = new UserDTO(user);
		
		return ResponseEntity.ok(userDTO);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User user) {
		user = service.update(id, user);
		
		UserDTO userDTO = new UserDTO(user);
		
		return ResponseEntity.ok().body(userDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	 
}
