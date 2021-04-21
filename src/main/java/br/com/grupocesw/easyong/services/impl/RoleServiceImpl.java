package br.com.grupocesw.easyong.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Role;
import br.com.grupocesw.easyong.repositories.RoleRepository;
import br.com.grupocesw.easyong.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired private RoleRepository repository;

	@Override
	public Role findByName(String name) {
		return repository.findByName(name);
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
