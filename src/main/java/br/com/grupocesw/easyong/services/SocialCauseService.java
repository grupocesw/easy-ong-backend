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

	public SocialCauseResponseDto create(SocialCauseRequestDto request);

	public SocialCauseResponseDto retrieve(Long id);

	public SocialCauseResponseDto update(Long id, SocialCauseRequestDto request);

	public void delete(Long id);
	
	public List<SocialCauseResponseDto> findAll();
	
	public Page<SocialCauseResponseDto> findAll(Pageable pageable);
	
	public Page<SocialCauseResponseDto> findByName(String name, Pageable pageable);
	
	public Optional<SocialCause> findById(Long Id);

	public Set<SocialCause> findByIds(Set<Long> ids);
}
