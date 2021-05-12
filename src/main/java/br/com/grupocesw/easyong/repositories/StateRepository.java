package br.com.grupocesw.easyong.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
	public List<State> findByNameContainingOrAbbreviationContainingIgnoreCase(String name, String abbreviation, Pageable pageable);
}