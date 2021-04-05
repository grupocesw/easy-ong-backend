package br.com.grupocesw.easyong.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.Ngo;

@Repository
public interface NgoRepository extends JpaRepository<Ngo, Long> {
	
	@Query("SELECT n FROM Ngo n WHERE n.activated = true")
    Page<Ngo> findByActivated(Pageable pageable);
	
	@Query("SELECT n FROM Ngo n WHERE n.activated = true ORDER BY RAND()")
	Page<Ngo> findSuggested(Pageable pageable);

}
