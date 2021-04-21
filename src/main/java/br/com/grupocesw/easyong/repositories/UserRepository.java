package br.com.grupocesw.easyong.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.checkedAt IS NOT NULL")
    Page<User> findCheckedAll(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.checkedAt IS NOT NULL AND u.id = :id")
	User findCheckedById(@Param("id") Long id);
	
	@Query("SELECT u FROM User u WHERE u.checkedAt IS NOT NULL AND u.username = :username")
	User findCheckedByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	Optional<User> findByUsernameOptional(@Param("username") String username);

	List<User> findAll();
    Boolean existsByUsername(String username);
    User findByUsername(String username);
}
