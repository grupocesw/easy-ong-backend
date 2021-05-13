package br.com.grupocesw.easyong.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.Ngo;

@Repository
public interface NgoRepository extends JpaRepository<Ngo, Long> {
	
    Page<Ngo> findByActivatedTrueOrderByName(Pageable pageable);
    
    @Query(" SELECT DISTINCT n FROM Ngo n LEFT JOIN n.causes sc "
    		+ " WHERE CONCAT(LOWER(n.name), ' ', LOWER(n.description), ' ', LOWER(sc.name)) "
    		+ " LIKE CONCAT('%', LOWER(:filter), '%') "
    		+ " AND n.activated = TRUE "
    		+ " GROUP BY n.id, n.name, n.description, sc.name "
    		+ " ORDER BY n.name ")
    Page<Ngo> findWithFilter(String filter, Pageable pageable);
	
	@Query("SELECT n FROM Ngo n WHERE n.activated = true ORDER BY RAND()")
	Page<Ngo> findSuggested(Pageable pageable);
}
