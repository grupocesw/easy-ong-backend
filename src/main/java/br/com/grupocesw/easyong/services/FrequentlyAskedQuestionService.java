package br.com.grupocesw.easyong.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;

@Service
public interface FrequentlyAskedQuestionService {

	public List<FrequentlyAskedQuestion> findAll();

	public Page<FrequentlyAskedQuestion> findAll(Pageable pageable);

	public Page<FrequentlyAskedQuestion> findByQuestionAndAnswer(String filter, Pageable pageable);

	public FrequentlyAskedQuestion create(FrequentlyAskedQuestion faq);

	public FrequentlyAskedQuestion retrieve(Long id);

	public FrequentlyAskedQuestion update(Long id, FrequentlyAskedQuestion faq);

	public void delete(Long id);
}
