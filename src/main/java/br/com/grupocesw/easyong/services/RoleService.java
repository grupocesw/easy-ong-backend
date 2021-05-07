package br.com.grupocesw.easyong.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Role;

@Service
public interface RoleService {
	
	public Role findByName(String name);

	public Set<Role> getDefaultRoles();
}
