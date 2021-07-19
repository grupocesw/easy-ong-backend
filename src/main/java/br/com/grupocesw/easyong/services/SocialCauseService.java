package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.SocialCause;

@Service
public interface SocialCauseService {

	SocialCause create(SocialCause request);

	SocialCause retrieve(Long id);

	SocialCause update(Long id, SocialCause request);

	void delete(Long id);

	Set<SocialCause> retrieveIn(Set<SocialCause> causes);

	void existsOrThrowsException(Long id);

	List<SocialCause> findAll();

	Page<SocialCause> findAll(Pageable pageable);

	Page<SocialCause> findByName(String name, Pageable pageable);

}
