package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.PictureRepository;
import br.com.grupocesw.easyong.services.PictureService;
import br.com.grupocesw.easyong.utils.PictureUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

	public final String storageDirectoryPath = "storage/pictures/";
	
	private final PictureRepository repository;

	@Override
	public Picture create(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Picture picture = repository.save(
			Picture.builder()
					.url(fileName)
					.build()
		);

		picture.setUrl(fileName);

		return picture;
	}
	
	@Override
	public Picture retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public Picture update(Long id, Picture request) {
		Picture picture = retrieve(id);
		picture.setUrl(request.getUrl());

		return repository.save(picture);
	}

	@Override
	public void delete(Long id) {
		repository.delete(retrieve(id));
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("Picture not found. Id " + id);
	}
	
	@Override
	public List<Picture> findAll() {
		return repository.findAll();
	}
	
	public Picture findById(Long id) {
		Optional<Picture> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Override
    public void upload(Picture request, MultipartFile file) {

        Path storageDirectory = Paths.get(storageDirectoryPath);

        if(!Files.exists(storageDirectory)){
            try {
                Files.createDirectories(storageDirectory);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Path destination = Paths.get(storageDirectory.toString().concat("/").concat(request.getUrl()));

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
    public byte[] getPicture(String url) throws IOException {
    	try {           	
    		Path destination = Paths.get(
    			storageDirectoryPath.concat(
    				PictureUtil.getFileNameWithExtension(storageDirectoryPath, url)
    			)
    		);
            
            return IOUtils.toByteArray(destination.toUri());
    	} catch (EntityNotFoundException|NumberFormatException|IOException e) {
    		Path destination = Paths.get(storageDirectoryPath.concat(Picture.noImage));
            return IOUtils.toByteArray(destination.toUri());    		
    	}		
    }

}
