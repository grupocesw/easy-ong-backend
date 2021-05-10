package br.com.grupocesw.easyong.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.io.IOUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.repositories.PictureRepository;
import br.com.grupocesw.easyong.request.dtos.PictureRequestDto;
import br.com.grupocesw.easyong.response.dtos.PictureResponseDto;
import br.com.grupocesw.easyong.services.PictureService;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.utils.PictureUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PictureServiceImpl implements PictureService {

	public final String storageDirectoryPath = "storage/pictures/";
	
	private PictureRepository repository;

	@Override
	public PictureResponseDto create(MultipartFile file) {		
		try {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());			
			Picture picture = repository.save(new Picture(fileName));
			
			picture.setUrl(fileName);
			
			return new PictureResponseDto(picture);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public PictureResponseDto retrieve(Long id) {
		try {			
			Picture picture = findById(id);
			return new PictureResponseDto(repository.save(picture));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	@Override
	public PictureResponseDto update(Long id, PictureRequestDto request) {
		try {			
			Picture picture = findById(id);
			picture.setUrl(request.getUrl());

			return new PictureResponseDto(repository.save(picture));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}		
	}

	@Override
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public List<PictureResponseDto> findAll() {
		return repository.findAll().stream()
			.map(obj -> new PictureResponseDto(obj))
			.collect(Collectors.toList());
	}
	
	public Picture findById(Long id) {
		Optional<Picture> optional = repository.findById(id);

		return optional.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	@Override
    public void upload(PictureRequestDto request, MultipartFile file) {

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
