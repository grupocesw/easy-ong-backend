package br.com.grupocesw.easyong.services.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.grupocesw.easyong.entities.Address;
import br.com.grupocesw.easyong.entities.City;
import br.com.grupocesw.easyong.entities.Contact;
import br.com.grupocesw.easyong.entities.Ngo;
import br.com.grupocesw.easyong.entities.NgoMoreInformation;
import br.com.grupocesw.easyong.entities.Picture;
import br.com.grupocesw.easyong.entities.SocialCause;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.request.dtos.NgoRequestDto;
import br.com.grupocesw.easyong.response.dtos.NgoFullResponseDto;
import br.com.grupocesw.easyong.response.dtos.NgoResponseDto;
import br.com.grupocesw.easyong.services.CityService;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.SocialCauseService;
import br.com.grupocesw.easyong.services.exceptions.BadRequestException;
import br.com.grupocesw.easyong.services.exceptions.DatabaseException;
import br.com.grupocesw.easyong.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NgoServiceImpl implements NgoService {

	private final NgoRepository repository;
	private final CityService cityService;
	private final SocialCauseService socialCauseService;

	@Override
	public NgoResponseDto create(NgoRequestDto request) {
		try {
			
			Optional<City> optionalCity = cityService.findById(request.getAddress().getCityId());
			optionalCity.orElseThrow(() -> new BadRequestException("City not exists"));
			
			Set<SocialCause> causes = socialCauseService.findByIdIn(request.getCauseIds());
			
			if (causes.size() < 1)
				throw new IllegalArgumentException("At least one cause required");
			
			if (request.getPictures() != null) {
				request.getPictures().stream()
					.forEach(obj -> {
						if (obj.getUrl().isEmpty())
							throw new IllegalArgumentException("URL can't empty");
					});
			}			
			
	    	Address address = Address.builder()
        		.number(request.getAddress().getNumber())
        		.street(request.getAddress().getStreet())
        		.complement(request.getAddress().getComplement())
        		.zipCode(request.getAddress().getZipCode())
        		.latitude(request.getAddress().getLatitude())
        		.longitude(request.getAddress().getLongitude())
        		.district(request.getAddress().getDistrict())
        		.city(optionalCity.get())
    			.build();
	    	
			Ngo ngo = Ngo.builder().name(request.getName())
	    		.cnpj(request.getCnpj())
	    		.description(request.getDescription())
	    		.activated(request.getActivated())
	    		.address(address)
	    		.contacts(request.getContacts().stream()
    				.map(obj -> 
	    				Contact.builder()
		    	    		.type(obj.getType())
		    	    		.content(obj.getContent())
		    				.build()
    				).collect(Collectors.toSet()))
	    		.moreInformations(request.getMoreInformations().stream()
    				.map(obj ->
    					NgoMoreInformation.builder()
    						.information(obj.getInformation())
    						.build()
    				).collect(Collectors.toSet()))
	    		.causes(causes)
	    		.pictures(request.getPictures().stream()
    				.map(obj ->     						
    					Picture.builder()
    						.url(obj.getUrl())
    						.build()    				
    				).collect(Collectors.toSet()))
	    		.build();
			
			return new NgoResponseDto(repository.save(ngo));
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
	public NgoResponseDto update(Long id, NgoRequestDto request) throws Exception {
		try {
			Optional<Ngo> optional = repository.findById(id);
			optional.orElseThrow(() -> new ResourceNotFoundException(id));
			
			Ngo ngo = optional.get();
			
			ngo.setName(request.getName());
			ngo.setCnpj(request.getCnpj());
			ngo.setDescription(request.getDescription());
			ngo.setActivated(request.getActivated());

			if (request.getContacts() != null) {
				ngo.getContacts().clear();
				ngo.getContacts().addAll(
					request.getContacts().stream()
						.map(obj -> 
							Contact.builder()
			    	    		.type(obj.getType())
			    	    		.content(obj.getContent())
			    				.build()
						).collect(Collectors.toSet())
				);
			}
			
			if (request.getAddress() != null) {
				ngo.getAddress().setNumber(request.getAddress().getNumber());
				ngo.getAddress().setStreet(request.getAddress().getStreet());
				ngo.getAddress().setComplement(request.getAddress().getComplement());
				ngo.getAddress().setZipCode(request.getAddress().getZipCode());
				ngo.getAddress().setLatitude(request.getAddress().getLatitude());
				ngo.getAddress().setLongitude(request.getAddress().getLongitude());
				ngo.getAddress().setDistrict(request.getAddress().getDistrict());
				
				if (request.getAddress().getCityId() != null) {
					Optional<City> optionalCity = cityService.findById(request.getAddress().getCityId());
					optionalCity.orElseThrow(() -> new BadRequestException("City not exists"));
					
					ngo.getAddress().setCity(optionalCity.get());
				}
			}
			
			if (request.getPictures() != null) {
				ngo.getPictures().clear();
				ngo.getPictures().addAll(request.getPictures().stream()
    				.map(obj ->
    					Picture.builder()
    						.url(obj.getUrl())
    						.build()
    				).collect(Collectors.toSet()));
			}
			
			if (request.getMoreInformations() != null) {
				ngo.getMoreInformations().clear();
				ngo.getMoreInformations().addAll(
						request.getMoreInformations().stream()
						.map(obj -> 
							NgoMoreInformation.builder()
	    						.information(obj.getInformation())
	    						.build()
    					).collect(Collectors.toSet())
				);
			}		
			
			if (request.getCauseIds().size() > 0) {
				Set<SocialCause> causes = socialCauseService.findByIdIn(request.getCauseIds());

				if (causes.size() < 1)
					throw new IllegalArgumentException("At least one cause required");
				
				ngo.getCauses().clear();
				ngo.getCauses().addAll(causes);
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
	public Page<NgoFullResponseDto> findByActivatedFull(String filter, Pageable pageable) {
		Page<NgoFullResponseDto> ngos;
		
		if (filter != null)
			ngos = repository.findWithFilter(filter, pageable)
				.map(ngo -> new NgoFullResponseDto(ngo));
		else 
			ngos = repository.findByActivatedTrueOrderByName(pageable)
				.map(ngo -> new NgoFullResponseDto(ngo));
		
		return ngos;
	}
	
	@Override
	public Page<NgoResponseDto> findByActivated(String filter, Pageable pageable) {
		Page<NgoResponseDto> ngos;
		
		if (filter != null)
			ngos = repository.findWithFilter(filter, pageable)
				.map(ngo -> new NgoResponseDto(ngo));
		else 
			ngos = repository.findByActivatedTrueOrderByName(pageable)
				.map(ngo -> new NgoResponseDto(ngo));
		
		return ngos;
	}

	@Override
	public Page<NgoResponseDto> findSuggested(Pageable pageable) {

		return repository.findSuggested(pageable)
			.map(ngo -> new NgoResponseDto(ngo));
	}

	@Override
	public Ngo findById(Long id) {
		return repository.getOne(id);
	}

}
