package br.com.grupocesw.easyong.repositories;

import br.com.grupocesw.easyong.entities.AppContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppContactRepository extends JpaRepository<AppContact, Long> {
}
