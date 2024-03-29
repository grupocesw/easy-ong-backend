package br.com.grupocesw.easyong.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupocesw.easyong.entities.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture findByUrlStartingWithIgnoreCase(String url);
}
