package br.com.grupocesw.easyong.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {	
	
	Role findByName(String name);
}
