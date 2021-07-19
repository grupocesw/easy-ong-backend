package br.com.grupocesw.easyong.repositories;

import br.com.grupocesw.easyong.entities.SocialCause;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SocialCauseRepository extends JpaRepository<SocialCause, Long> {
	Page<SocialCause> findByNameContainingIgnoreCase(String name, Pageable pageable);
	Optional<SocialCause> findById(Long id);
	Optional<Set<SocialCause>> findByIdIn(Set<Long> ids);
}
