package br.com.grupocesw.easyong.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.request.dtos.SocialCauseRequestDto;
import br.com.grupocesw.easyong.response.dtos.SocialCauseResponseDto;

@Service
public interface SocialCauseService {

	SocialCauseResponseDto create(SocialCauseRequestDto request);

	SocialCauseResponseDto retrieve(Long id);

	SocialCauseResponseDto update(Long id, SocialCauseRequestDto request);

	void delete(Long id);
	
	List<SocialCauseResponseDto> findAll();
	
	Page<SocialCauseResponseDto> findAll(Pageable pageable);
	
	Page<SocialCauseResponseDto> findByName(String name, Pageable pageable);
	
	Optional<SocialCause> findById(Long Id);

	Set<SocialCause> findByIdIn(Set<Long> ids);
}
