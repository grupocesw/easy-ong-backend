package br.com.grupocesw.easyong.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.SocialCause;

@Repository
public interface SocialCauseRepository extends JpaRepository<SocialCause, Long> {
	Page<SocialCause> findByNameContainingIgnoreCase(String name, Pageable pageable);
	Optional<SocialCause> findById(Long id);
	Set<SocialCause> findByIdIn(Set<Long> ids);
}
