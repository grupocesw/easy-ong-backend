package br.com.grupocesw.easyong.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.grupocesw.easyong.dto.UserDTO;
import br.com.grupocesw.easyong.entities.User;
import br.com.grupocesw.easyong.services.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping(value = "/api/users")
@RestController
public class UserResource {

	@Autowired
	private UserService service;

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna a lista de ONG"),
			@ApiResponse(code = 401, message = "Credencial inválida para acessar este recurso"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find ngos")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.") })
	public Page<UserDTO> list(@ApiIgnore final Pageable pageable) {
		final Page<User> users = service.findAll(pageable);

		return users.map(user -> new UserDTO(user));
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@RequestBody User user) {

		User userSalved = service.insert(user);

		UserDTO userDTO = new UserDTO(userSalved);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId())
				.toUri();

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
