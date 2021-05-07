package br.com.grupocesw.easyong.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.Faq;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

	@Query("SELECT faq FROM Faq faq"
			+ " WHERE"
			+ " CONCAT(LOWER(faq.question), ' ', LOWER(faq.answer))"
			+ " LIKE CONCAT('%', LOWER(:filter), '%')")
	Page<Faq> findByQuestionAndAnswer(@Param("filter") String filter, Pageable pageable);
}
