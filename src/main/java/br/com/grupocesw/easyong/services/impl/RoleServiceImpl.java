package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.repositories.RoleRepository;
import br.com.grupocesw.easyong.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository repository;

	@Override
	public Role findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("Role not found. Id " + id);
	}

	@Override
	public Set<Role> getDefaultRoles() {
		Role role = repository.findByName("USER");

		Set<Role> roles = new HashSet<>(){
			private static final long serialVersionUID = 1L;
			{ 
				add(role);
			}
		};

		return roles;
	}

}
