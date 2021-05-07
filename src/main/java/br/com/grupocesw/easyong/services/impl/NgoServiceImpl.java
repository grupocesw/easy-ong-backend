package br.com.grupocesw.easyong.services.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.request.dtos.NgoCreateRequestDto;
import br.com.grupocesw.easyong.request.dtos.NgoUpdateRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoFullResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.StreetService;
import br.com.grupocesw.easyong.services.exceptions.BadRequestException;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NgoServiceImpl implements NgoService {

	private NgoRepository repository;
	private StreetService streetService;
	
	@Override
	public Page<NgoFullResponseDto> findByActivatedFull(Pageable pageable) {
		Page<NgoFullResponseDto> ngos = repository.findByActivated(pageable)
				.map(ngo -> new NgoFullResponseDto(ngo));
		
		return ngos;
	}
	
	@Override
	public Page<NgoResponseDto> findByActivated(Pageable pageable) {
		Page<NgoResponseDto> ngos = repository.findByActivated(pageable)
				.map(ngo -> new NgoResponseDto(ngo));
		
		return ngos;
	}

	@Override
	public Page<NgoResponseDto> findSuggested(Pageable pageable) {
		
		Page<NgoResponseDto> ngos = repository.findSuggested(pageable)
				.map(ngo -> new NgoResponseDto(ngo));
		
		return ngos;
	}

	@Override
	public NgoResponseDto create(NgoCreateRequestDto ngoDto) {
		try {			
			if (!streetService.existsById(ngoDto.getAddress().getStreet().getId()))
				throw new BadRequestException("Street not exists.");
			
			return new NgoResponseDto(repository.save(ngoDto.build()));
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	@Override
	public NgoFullResponseDto retrieve(Long id) {
		Optional<Ngo> optional = repository.findById(id);
		optional.orElseThrow(() -> new ResourceNotFoundException(id));

		return new NgoFullResponseDto(optional.get());
	}

	@Override
	public NgoResponseDto update(Long id, NgoUpdateRequestDto ngoDto) throws Exception {
		try {
			Ngo ngo = repository.getOne(id);
			Ngo ngoNew = ngoDto.build();
			
			ngo.setName(ngoNew.getName());
			ngo.setCnpj(ngoNew.getCnpj());
			ngo.setDescription(ngoNew.getDescription());
			ngo.setActivated(ngoNew.getActivated());

			if (ngoNew.getAddress() != null) {
				ngo.getAddress().setNumber(ngoNew.getAddress().getNumber());
				ngo.getAddress().setComplement(ngoNew.getAddress().getComplement());
				ngo.getAddress().setLatitude(ngoNew.getAddress().getLatitude());
				ngo.getAddress().setLongitude(ngoNew.getAddress().getLongitude());

				if (ngoNew.getAddress().getStreet() != null) {
					ngo.getAddress().getStreet().setId(ngoNew.getAddress().getStreet().getId());
				}
			}
			
			if (ngoNew.getPictures() != null) {
				ngo.getPictures().clear();
				ngo.getPictures().addAll(ngoNew.getPictures());
			}
			
			if (ngoNew.getMoreInformations() != null) {
				ngo.getMoreInformations().clear();
				ngo.getMoreInformations().addAll(ngoNew.getMoreInformations());
			}

			if (ngoNew.getCauses() != null) {
				ngo.getCauses().clear();
				ngo.getCauses().addAll(ngoNew.getCauses());
			}

			return new NgoResponseDto(repository.save(ngo));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
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
	public Ngo findById(Long id) {
		return repository.getOne(id);
	}

}
