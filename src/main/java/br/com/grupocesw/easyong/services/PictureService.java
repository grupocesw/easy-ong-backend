package br.com.grupocesw.easyong.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.entities.Picture;

@Service
public interface PictureService {

	public List<Picture> findAll();

	public Picture findById(Long id);

	public Picture insert(MultipartFile file);

	public Picture update(Long id, Picture picture);

	public void delete(Long id);
	
    public void upload(Picture picture, MultipartFile file);

    public byte[] getPicture(String name) throws IOException;
}
