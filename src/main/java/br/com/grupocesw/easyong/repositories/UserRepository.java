package br.com.grupocesw.easyong.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.checkedAt IS NOT NULL")
    Page<User> findByChecked(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.checkedAt IS NOT NULL AND u.id = ?1")
	Optional<User> getOneChecked(Long id);
	
	@Query("SELECT u FROM User u WHERE u.checkedAt IS NULL AND u.id = ?1")
	Optional<User> getOneNotChecked(Long id);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	User getUserByUsername(String username);
	
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
