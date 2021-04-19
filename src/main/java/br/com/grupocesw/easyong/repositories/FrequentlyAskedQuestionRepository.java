package br.com.grupocesw.easyong.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.FrequentlyAskedQuestion;

@Repository
public interface FrequentlyAskedQuestionRepository extends JpaRepository<FrequentlyAskedQuestion, Long> {

	@Query("SELECT faq FROM FrequentlyAskedQuestion faq "
			+ "WHERE faq.question LIKE CONCAT('%', LOWER(:filter), '%') "
			+ "OR faq.answer LIKE CONCAT('%', LOWER(:filter), '%')")
	Page<FrequentlyAskedQuestion> findByQuestionAndAnswer(@Param("filter") String filter, Pageable pageable);
}
