package br.com.grupocesw.easyong.services;


import br.com.grupocesw.easyong.entities.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FaqService {

	Faq create(Faq faq);

	Faq retrieve(Long id);

	Faq update(Long id, Faq faq);

	void delete(Long id);

	void existsOrThrowsException(Long id);

	List<Faq> findAll();

	Page<Faq> findAll(Pageable pageable);

	Page<Faq> findByQuestionAndAnswer(String filter, Pageable pageable);
}
