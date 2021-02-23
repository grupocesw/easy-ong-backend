package br.com.grupocesw.easyong.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;

@Repository
public interface FrequentlyAskedQuestionRepository extends JpaRepository<FrequentlyAskedQuestion, Long> {

	@Query("SELECT faq FROM FrequentlyAskedQuestion faq "
			+ "WHERE faq.question LIKE CONCAT('%', LOWER(?1), '%') "
			+ "OR faq.answer LIKE CONCAT('%', LOWER(?1), '%')")
	Page<FrequentlyAskedQuestion> findByQuestionAndAnswer(String filter, Pageable pageable);
}
