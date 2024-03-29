package br.com.grupocesw.easyong.repositories;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);	
    Optional<User> findById(Long id);
	Optional<User> findByUsernameIgnoreCase(String username);
	List<User> findAll();
	Set<User> findAllByCausesIn(Set<SocialCause> causes);
    boolean existsByUsernameIgnoreCase(String username);

}
