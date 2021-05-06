package br.com.grupocesw.easyong.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);	
    Optional<User> findById(Long id);
	Optional<User> findByUsername(String username);
	List<User> findAll();
    Boolean existsByUsername(String username);
}
