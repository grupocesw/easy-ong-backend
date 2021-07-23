package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.Picture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface PictureService {

	Picture retrieve(Long id);

	Picture findByUrl(String url);

	Picture update(Long id, Picture request);

	void delete(Long id);

	void existsOrThrowsException(Long id);

	void upload(MultipartFile picture);

    byte[] getPicture(String name) throws IOException;
}
