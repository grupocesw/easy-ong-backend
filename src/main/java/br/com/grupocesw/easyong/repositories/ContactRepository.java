package br.com.grupocesw.easyong.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
