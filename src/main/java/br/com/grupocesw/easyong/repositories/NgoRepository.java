package br.com.grupocesw.easyong.repositories;

import br.com.grupocesw.easyong.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.Ngo;

import java.util.List;
import java.util.Optional;

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

	@Query("SELECT DISTINCT n FROM Ngo n JOIN FETCH n.users u WHERE u = :user")
	List<Ngo> getFavoriteNgosByUser(@Param("user") User user);

	@Query("SELECT n FROM Ngo n JOIN FETCH n.users u WHERE u.id = :userId AND n.id = :ngoId")
	Optional<Ngo> findNgoByNgoIdAndUserId(@Param("userId") Long userId, @Param("ngoId") Long ngoId);

	@Modifying
	@Query(value = "INSERT INTO user_favorite_ngos (user_id, ngo_id) VALUES (:userId, :ngoId)", nativeQuery = true)
	void saveFavoriteNgo(@Param("userId") Long userId, @Param("ngoId") Long ngoId);

	@Modifying
	@Query(value = "DELETE FROM user_favorite_ngos WHERE user_id = :userId AND ngo_id = :ngoId", nativeQuery = true)
	void deleteFavoriteNgo(@Param("userId") Long userId, @Param("ngoId") Long ngoId);

}
