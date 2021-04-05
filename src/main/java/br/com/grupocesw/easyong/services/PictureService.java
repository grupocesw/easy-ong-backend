package br.com.grupocesw.easyong.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.repositories.PictureRepository;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.utilities.PictureUtility;

@Service
public class PictureService {

	public final String storageDirectoryPath = "storage/pictures/";
	
	@Autowired
	private PictureRepository repository;

	public List<Picture> findAll() {
		return repository.findAll();
	}

	public Picture findById(Long id) {
		Optional<Picture> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Picture insert(MultipartFile file) {		
		try {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());			
			Picture picture = repository.save(new Picture(fileName));
			
			this.upload(picture, file);
			
			return picture;
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Picture update(Long id, Picture picture) {
		try {
			Picture entity = repository.getOne(id);
			this.updateData(entity, picture);

			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	private void updateData(Picture entity, Picture picture) {
		entity.setName(picture.getName());
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
    public void upload(Picture picture, MultipartFile file) {

        Path storageDirectory = Paths.get(storageDirectoryPath);

        if(!Files.exists(storageDirectory)){
            try {
                Files.createDirectories(storageDirectory);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Path destination = Paths.get(storageDirectory.toString().concat("/").concat(picture.getName()));

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getPicture(String name) throws IOException {
    	try {           	
    		Path destination = Paths.get(
    			storageDirectoryPath.concat(
    				PictureUtility.getFileNameWithExtension(storageDirectoryPath, name)
    			)
    		);
            
            return IOUtils.toByteArray(destination.toUri());
    	} catch (EntityNotFoundException|NumberFormatException|IOException e) {
    		Path destination = Paths.get(storageDirectoryPath.concat(Picture.noImage));
            return IOUtils.toByteArray(destination.toUri());    		
    	}		
    }

}
