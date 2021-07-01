package br.com.grupocesw.easyong.services.impl;

import br.com.grupocesw.easyong.entities.*;
import br.com.grupocesw.easyong.exceptions.BadRequestException;
import br.com.grupocesw.easyong.exceptions.ResourceNotFoundException;
import br.com.grupocesw.easyong.repositories.NgoRepository;
import br.com.grupocesw.easyong.services.CityService;
import br.com.grupocesw.easyong.services.NgoService;
import br.com.grupocesw.easyong.services.SocialCauseService;
import br.com.grupocesw.easyong.exceptions.DatabaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NgoServiceImpl implements NgoService {

	private final NgoRepository repository;
	private final CityService cityService;
	private final SocialCauseService socialCauseService;

	@Override
	@CacheEvict(value = "ngos", allEntries = true)
	public Ngo create(Ngo request) {
		try {
			cityService.existsOrThrowsException(request.getAddress().getCity().getId());
			City city = cityService.retrieve(request.getAddress().getCity().getId());

			Set<SocialCause> causes = socialCauseService.findByIdIn(
					request.getCauses()
						.stream()
						.map(c -> c.getId())
						.collect(Collectors.toSet())
			);

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
        		.city(city)
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

			return repository.save(ngo);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public Ngo retrieve(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	@CacheEvict(value = "ngos", allEntries = true)
	public Ngo update(Long id, Ngo request) throws Exception {
		try {
			Ngo ngo = retrieve(id);

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

				if (request.getAddress().getCity() != null) {
					cityService.existsOrThrowsException(request.getAddress().getCity().getId());
					City city = cityService.retrieve(request.getAddress().getCity().getId());

					ngo.getAddress().setCity(city);
				}
			}

			if (request.getPictures() != null && request.getPictures().size() > 0) {
				ngo.getPictures().clear();
				ngo.getPictures().addAll(request.getPictures().stream()
    				.map(obj ->
    					Picture.builder()
    						.url(obj.getUrl())
    						.build()
    				).collect(Collectors.toSet()));
			}

			if (request.getMoreInformations() != null && request.getMoreInformations().size() > 0) {
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

			if (request.getCauses() != null && request.getCauses().size() > 0) {
				Set<SocialCause> causes = socialCauseService.findByIdIn(
						request.getCauses().stream().map(c -> c.getId())
								.collect(Collectors.toSet())
				);

				if (causes.size() < 1)
					throw new IllegalArgumentException("At least one cause required");

				ngo.getCauses().clear();
				ngo.getCauses().addAll(causes);
			}

			return repository.save(ngo);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@CacheEvict(value = "ngos", allEntries = true)
	public void delete(Long id) {
		repository.delete(retrieve(id));
	}

	@Override
	public void existsOrThrowsException(Long id) {
		if (!repository.existsById(id))
			throw new BadRequestException("Ngo not found. Id " + id);
	}

	@Override
	@Cacheable(value = "ngos", key = "#pageable.pageSize")
	public Page<Ngo> findByActivated(Pageable pageable) {
		return repository.findByActivatedTrueOrderByName(pageable);
	}

	@Override
	public Page<Ngo> findByActivatedWithFilter(String filter, Pageable pageable) {
		return repository.findWithFilter(filter, pageable);
	}

	@Override
	public Page<Ngo> findSuggested(Pageable pageable) {

		return repository.findSuggested(pageable);
	}

}
